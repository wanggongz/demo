package com.hl.word.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hl.word.config.FileReader;
import com.hl.word.domain.postman.Header;
import com.hl.word.domain.postman.Item;
import com.hl.word.domain.postman.Param;
import com.hl.word.domain.postman.environment.Environment;
import com.hl.word.domain.postman.environment.EnvironmentValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author ws
 * @date 2018/12/29 16:10
 */
@Component
public class WordHandler {

	private static String reqire_type="reqire_type";
	private static String reqire_path="reqire_path";
	String basePath = "classpath:config/template/";
	private String formDataContentType = "multipart/form-data";
	private String defaultContentType = "application/x-www-form-urlencoded";

	@Resource
	private FileReader reader;


	@Value("${field.default.description}")
	private String defaultFieldDes;

	/**
	 * 环境变量
	 */
	private List<EnvironmentValue> environment;

	public String responseBodyString = "";

	@PostConstruct
	public void init(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			String environmentStr = reader.getFileContent("postman_environment.json");
			environment = mapper.readValue(environmentStr, Environment.class).getValues();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void replaceAllValue(Item item){
		//请求参数《全局变量》替换
		List<Param> query = item.getRequest().getUrl().getQuery();
		this.paramsHandler(query);
		//响应参数变量--暂时不需要替换
//		Map<String, List<Param>> responseTableList = item.getResponseTableList();
//		if(responseTableList==null){
//			return;
//		}
//		Set<String> strings = responseTableList.keySet();
//		for(String string: strings){
//			List<Param> params = responseTableList.get(string);
//			this.replaceParams(params);
//		}
	}

	private void paramsHandler(List<Param> query){
		for(Param param: query){
			//设置参数类型
			if(param.getDescription().contains("array")){
				param.setType("Array");
			}
			//设置参数示例值
			String value = param.getValue();
			for(EnvironmentValue environmentValue: environment){
				if(value==null){
					value="";
				}
				if(value.contains("{{"+environmentValue.getKey()+"}}")){
					value = value.replace("{{"+environmentValue.getKey()+"}}",environmentValue.getValue());
				}
			}
			param.setValue(value);
		}
	}


	public String getResponseBody(Item item) throws IOException {
		List<Item> docs = item.getItem();
		if(docs==null||docs.size()==0&&item.getRequest()!=null){
			item.getResponse().get(0).getBody();
			String description = item.getDescription();
			if(StringUtils.isEmpty(description)){
				description = item.getName();
			}
			String apiStr =
					getTitle(item)
					+ getFunction(item,"功能说明")
					+ getContext(description)
					+ getFunction(item,"请求说明")
					+ getRequestDes(item)
					+ getFunction(item,"请求参数")
					+ getRequestTable(item)
					+ nextLine()
					+ getContext("请求示例:")
					+ nextLine()
					+ getExample(getRequestString(item))
					+ getFunction(item,"响应说明")
					+ getResponseTable(item)
					+ nextLine()
					+ getContext("响应示例:")
					+ nextLine()
					+ getExample(item.getResponseString())
					;
			responseBodyString += apiStr;
			return apiStr;
		}else{
			responseBodyString += getTitle(item);
			return "";
		}
	}
	public String nextLine() throws IOException {
		return getStr("nextLine.ftl");
	}
	public String getTitle(Item item) throws IOException {
		String name = item.getName();
		if(name.contains("-")){
			name = name.substring(0,name.indexOf("-"));
		}
		return getStr("title"+item.getLevel()+".ftl").replace("content",name);
	}
	public String getFunction(Item item,String function) throws IOException {
		return getStr("title"+(item.getLevel()+1)+".ftl").replace("content",function);
	}
	public String getContext(String context) throws IOException {
		return getStr("line.ftl").replace("content",context);
	}
	public String getRequestTable(Item item) throws IOException {
		List<Param> query = item.getRequest().getUrl().getQuery();
		if(query==null||query.isEmpty()){
			return "";
		}
		String rows = "";
		for(Param param: query){
			rows += getStr("table_row.ftl")
					.replace("row_key",param.getKey())
					.replace("row_type",param.getType())
					.replace("row_require",param.getRequired())
					.replace("row_des",param.getDescription())
			;
		}
		return getStr("table.ftl").replace("table_rows",rows);
	}
	public String getExample(String body) throws IOException {
		String[] split = body.split("\\n");
		String res = "";
		for(String line: split){
			//去掉-和“之间的说明
			if(line.contains("\"")&&line.split("\"").length>1){
				String[] texts = line.split("\"")[1].split("-");
				if(texts.length==2){
					line = line.replace("-"+texts[1],"");
				}
			}
			res += getContext(line);
		}
		return getStr("example.ftl").replace("example",res);
	}
	public String getRequestString(Item item){
		String line1 = item.getRequest().getMethod()+"  "+getUrl(item)+"  HTTP/1.1 \n";
		String line2 = "Content-Type: "+contentType(item)+" \n";
		String line3;
		if(contentType(item).contains(formDataContentType)){
			line3 = getFormDataParams(item);
		}else{
			String res = "";
			List<Param> query = item.getRequest().getUrl().getQuery();
			for(Param param: query){
				res += param.getKey()+"="+param.getValue()+"&";
			}
			line3 = res.substring(0,res.length()-1).
					replaceAll("\\{\\{","").
					replaceAll("}}","").
					replaceAll("&","&amp;");
		}
		return line1+line2+line3;
	}
	public String getRequestDes(Item item) throws IOException {
		return getStr("table_request_des.ftl")
				.replace(reqire_type,item.getRequest().getMethod())
				.replace(reqire_path,getUrl(item));
	}

	public String getResponseTable(Item item) throws IOException {
		boolean get = item.getRequest().getMethod().equalsIgnoreCase("GET");
		if(get){
			Map<String, List<Param>> responseTableList = item.getResponseTableList();
			if(responseTableList==null){
				return "";
			}
			Set<String> strings = responseTableList.keySet();
			String tables = "";
			for(String key: strings){
				String table = this.responseTableStr(responseTableList.get(key));
				if(key.equalsIgnoreCase("main")){
					tables += table;
				}
			}
			for(String key: strings){
				String table = this.responseTableStr(responseTableList.get(key));
				if(!key.equalsIgnoreCase("main")){
					table = this.getLineStr(key) + table;
					tables += table;
				}
			}
			return tables;
		}
		return "";
	}
	public String responseTableStr(List<Param> params) throws IOException {
		String tableTrBody = "";
		if(params!=null){
			for(Param param: params){
				String replace = getStr("table_row.ftl").replace(
						"row_key", param.getKey())
						.replace("row_type", param.getType())
						.replace("row_des",param.getDescription())
						.replace("row_require","")
						;
				tableTrBody += replace;
			}
			return getStr("table.ftl").replace("table_rows",tableTrBody);
		}
		return "";
	}
	public String getStr(String name) throws IOException {
		return reader.getFileContent(basePath,name);
	}
	public String getFormDataParams(Item item){
		String res = "";
		List<Param> query = item.getRequest().getUrl().getQuery();
		for(Param param: query){
			res += "Content-Disposition: form-data; name=\""+param.getKey()+"\"\n";
			res += param.getValue()+"\n";
		}
		res += "------WebKitFormBYWxkTrZu0gW--";
		return res;
	}
	public String getLineStr(String line) throws IOException {
		return getStr("line.ftl").replace("content",line);
	}
	public String contentType(Item item){
		List<Header> headers = item.getRequest().getHeader();
		for(Header header:headers){
			if(header.getKey().equalsIgnoreCase("Content-Type")){
				if(header.getValue().equalsIgnoreCase(formDataContentType)){
					return header.getValue()+"; boundary=----WebKitFormBYWxkTrZu0gW";
				}
				return header.getValue();
			}
		}
		return defaultContentType;
	}
	public String getUrl(Item item){
		String raw = item.getRequest().getUrl().getRaw();
		raw = raw.replace("{{baseUrl}}","/");
		if(raw.contains("?")){
			raw = raw.substring(0,raw.indexOf("?"));
		}
		return raw;
	}
	public String getDefaultDes(String field){
		if(defaultFieldDes==null){
			return "";
		}
		if(defaultFieldDes.equalsIgnoreCase("field")){
			return field;
		}
		return defaultFieldDes;
	}

}
