package com.hl.word.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hl.word.config.FileReader;
import com.hl.word.domain.postman.Item;
import com.hl.word.domain.postman.Param;
import com.hl.word.domain.postman.PostMan;
import com.hl.word.util.UpAndDownUtil;
import com.hl.word.util.Xml2Word;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 *
 * @author ws
 * @date 2018/12/29 10:17
 */
@RestController
@RequestMapping("word")
public class WordController {

	/**
	 * 是否去掉没有注释的字段
	 */
	private boolean skipNoDesParam = false;

	/**
	 * 模板位置
	 */
	private String templetFilePath = "word/src/main/resources/config/template/template.ftl";

	/**
	 * 文件存放位置
	 */
	private String targetFilePath = "word/src/main/resources/config/file/test.doc";

	@Resource
	private FileReader reader;

	@Resource
	private WordHandler word;

	@Resource
	private ResourceLoader resourceLoader;

	Map entitys;
	List ignoreApis;

	List<Item> apis;


	/**
	 * 入口
	 */
	@RequestMapping("postman")
	public String getDomain(HttpServletResponse response) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		String json = reader.getFileContent("postman.json");
		PostMan postMan = mapper.readValue(json, PostMan.class);

		apis = new ArrayList<>();
		ignoreApis = new ArrayList<>();
		entitys = new HashMap();
		for(Item item:postMan.getItem()){
			handlerParam(item);
		}

		postMan.setDocString(word.responseBodyString);
		postMan.setItem(apis);
		Xml2Word.xml2XmlDoc(postMan,templetFilePath,targetFilePath);

		File file = ResourceUtils.getFile("classpath:config/file/test.doc");
		UpAndDownUtil.down(response,file);

		return null;
	}

	/**
	 * 参数设置（请求和响应）
	 * 	query中没有参数时从body中获取，type为空值设置为text
	 */
	public void handlerParam(Item item) throws IOException {
		//没有子集，即api层
		if(item.getItem()==null&&item.getRequest()!=null){
			item.setHasSons(false);
			//参数合并
			List<Param> query = item.getRequest().getUrl().getQuery();
			List<Param> urlencoded = item.getRequest().getBody().getUrlencoded();
			List<Param> formdata = item.getRequest().getBody().getFormdata();
			if(query == null){
				query = new ArrayList<>();
			}
			if(urlencoded!=null){
				query.addAll(urlencoded);
			}
			if(formdata!=null){
				query.addAll(formdata);
			}
			this.addCommonRequireParams(item,query);
			item.getRequest().getUrl().setQuery(query);
			apis.add(item);
			if(item.getName().contains("ignore")){
				ignoreApis.add(item.getName());
			}
			//response格式化
			boolean get = item.getRequest().getMethod().equalsIgnoreCase("get");
			if(get){
				String body = item.getResponse().get(0).getBody();
				if(StringUtils.isEmpty(body)){
					return;
				}
				Map map = new ObjectMapper().readValue(body, Map.class);
				Object data = map.get("data");
				Map params = null;
				if(data instanceof Map){
					if(((Map) data).get("total")!=null){
						params = (Map) ((List)((Map) data).get("pageData")).get(0);
					}else{
						params = (Map) data;
					}
				}else if(data instanceof List){
					params = (Map) ((List) data).get(0);
				}
				Map entitys = this.getEntitys(item,params);
				item.setResponseTableList(entitys);
			}
			word.replaceAllValue(item);
			word.getResponseBody(item);
		}else{
			item.setHasSons(true);
			String id = item.getId();
			if(id==null){
				item.setId(item.getName());
			}
			if(item.getLevel()==null){
				item.setLevel(1);
			}
			word.getResponseBody(item);
			for(Item i:item.getItem()){
				i.setParentId(item.getId());
				i.setLevel(item.getLevel()+1);
				i.setId(i.getParentId()+"-"+i.getName());
				this.handlerParam(i);
			}
		}
	}

	/**
	 * 获取响应实体集合(可能多个)
	 */
	public Map getEntitys(Item item,Map params){
		Map<String,List<Param>> responseTableList = new HashMap<>();
		if(params!=null){
			Set<String> set = params.keySet();
			List<Param> response = new ArrayList<>();
			for(String key:set){
				if(skipParam(key)){
					continue;
				}
				Object value = params.get(key);
				Param param = new Param();

				this.handlerResponseKey(key,param);

				if(value instanceof Map){
					param.setType("Object");
					List<Param> entity = new ArrayList<>();
					Set<String> set1 = ((Map) value).keySet();
					for(String key1:set1){
						if(key1.equals("oid")){
							continue;
						}
						Param param1 = new Param();
						Object value1 = ((Map) value).get(key1);
						this.handlerResponseKey(key1,param1);
						param1.setType("String");
						entity.add(param1);
					}
					responseTableList.put(key+"说明",entity);
					entitys.put(key,entity);
				}else{
					param.setType("String");
				}
				response.add(param);
			}
			responseTableList.put("main",response);
			entitys.put("main",response);
		}

		return responseTableList;
	}

	/**
	 * 响应参数key值处理
	 */
	public void handlerResponseKey(String key,Param param){
		String[] keys = key.split("-");
		param.setKey(keys[0]);
		if(keys.length==2){
			param.setDescription(keys[1]);
		}else{
			param.setDescription(word.getDefaultDes(keys[0]));
		}
	}

	/**
	 * 是否跳过当前响应参数
	 */
	public boolean skipParam(String key){
		if(key.equals("oid")){
			return true;
		}
		if(key.contains("-")&&skipNoDesParam){
			return true;
		}
		return false;
	}

	/**
	 * 设置公共请求参数，pageSize和currentPage
	 */
	public void addCommonRequireParams(Item item,List<Param> query){
		if(word.getUrl(item).contains("pageSearch")){
			Param param = new Param();
			param.setKey("currentPage");
			param.setType("int");
			param.setDescription("当前页");
			param.setRequired("否");

			Param param2 = new Param();
			param2.setKey("pageSize");
			param2.setType("int");
			param2.setDescription("每页数量");
			param2.setRequired("否");

			query.add(param);
			query.add(param2);
		}
	}

}
