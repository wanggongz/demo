//package com.hl.word.controller;
//
//import com.hl.word.config.FileReader;
//import com.hl.word.domain.postman.Header;
//import com.hl.word.domain.postman.Item;
//import com.hl.word.domain.postman.Param;
//import org.springframework.stereotype.Component;
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author ws
// * @date 2018/12/29 16:10
// */
//@Component
//public class Word {
//
//	private static String param_key = "param_key";
//	private static String param_type = "param_type";
//	private static String is_reqired = "is_reqired";
//	private static String param_description = "param_description";
//	private static String title = "title";
//	private static String response = "response";
//	private static String reqire_type="reqire_type";
//	private static String reqire_path="reqire_path";
//
//	String basePath = "classpath:config/template/";
//	private String formDataContentType = "multipart/form-data";
//	private String defaultContentType = "application/x-www-form-urlencoded";
//
//	@Resource
//	private FileReader reader;
//
//	private String responseBodyString = "";
//
//	public void getResponseBody(Item item) throws IOException {
//		List<Item> docs = item.getItem();
//		if(docs==null||docs.size()==0){
//			this.handlerTitle1(item);
//			this.handlerTableRequest(item);
//			this.handlerResponse(item);
//			String body = item.getBody();
//		}else{
//
//		}
//	}
//
//
//	public String getStr(String name) throws IOException {
//		return reader.getFileContent(basePath,name);
//	}
//
//	public void handlerTitle1(Item item) throws IOException {
//		String name = item.getName();
//		item.setTitle(getStr("title.ftl").replace(title,name));
//		item.setFunction(getStr("function.ftl").replace(title,name));
//	}
//
//
//	/**
//	 * 设置请求参数
//	 *
//	 * @author ws
//	 */
//	public void handlerTableRequest(Item item) throws IOException {
//		List<Param> query = item.getRequest().getUrl().getQuery();
//		String paramTable ="";
//		for(Param param: query){
//			String reqired ;
//			if(param.getDescription()!=null&&param.getDescription().contains("*")){
//				reqired = "是";
//			}else{
//				reqired = "否";
//			}
//			String replace = getStr("table_request_col.ftl").replace(param_key, param.getKey())
//					.replace(param_type, param.getType())
//					.replace(is_reqired, reqired);
//			if(param.getDescription()!=null){
//				replace = replace.replace(param_description,param.getDescription());
//			}else{
//				replace = replace.replace(param_description,"说明");
//			}
//			paramTable+=replace;
//		}
//		item.setRequestDes(getStr("request_des.ftl")
//				.replace(reqire_type,item.getRequest().getMethod())
//				.replace(reqire_path,getUrl(item))
//		);
//
//		//请求示例
//		String line1 = getLinesBody(item.getRequest().getMethod()+"  "+getUrl(item)+"  HTTP/1.1");
//		String line2 = getLinesBody("Content-Type: "+contentType(item));
//		String line3 = getLinesBody(getParamsStr(item));
//
//		item.setRequestParams(getStr("request_params.ftl")
//				.replace("col_body",paramTable)
//				.replace("request_example",line1+line2+line3)
//		);
//	}
//
//
//	public void handlerResponse(Item item) throws IOException {
//		String responseBody = getStr("response.ftl");
//		String body = item.getResponse().get(0).getBody();
//		if(body==null){
//			responseBody = "";
//		}else{
//			responseBody = responseBody.replace("response_example",getLinesExampleBody(body));
//			boolean get = item.getRequest().getMethod().equalsIgnoreCase("GET");
//			if(get){
//				Map<String, List<Param>> responseTableList = item.getResponseTableList();
//				Set<String> strings = responseTableList.keySet();
//				String tables = "";
//				for(String key: strings){
//					String table = this.responseTableStr(responseTableList.get(key));
//					if(key.equalsIgnoreCase("main")){
//						tables += table;
//					}
//				}
//				for(String key: strings){
//					String table = this.responseTableStr(responseTableList.get(key));
//					if(!key.equalsIgnoreCase("main")){
//						table = this.getLineStr(key) + table;
//						tables += table;
//					}
//				}
//				responseBody = responseBody.replace("response_tables",tables);
//			}
//		}
//		item.setResponseBody(responseBody);
//
//		item.setBody(item.getTitle()+getStr("service.ftl")
//				.replace("title",item.getTitle())
//				.replace("function",item.getFunction())
//				.replace("request_des",item.getRequestDes())
//				.replace("request_params",item.getRequestParams())
//				.replace("response",responseBody));
//	}
//
//	public String responseTableStr(List<Param> params) throws IOException {
//		String tableTrBody = "";
//		if(params!=null){
//			for(Param param: params){
//				String replace = getStr("table_response_col.ftl").replace(
//						"response_key", param.getKey())
//						.replace("response_type", param.getType())
//						.replace("response_description",param.getDescription())
//						;
//				tableTrBody += replace;
//			}
//			return getStr("table_response.ftl").replace("body",tableTrBody);
//		}
//		return "";
//	}
//
//
//	public String getParamsStr(Item item){
//		if(contentType(item).contains(formDataContentType)){
//			return getFormDataParams(item);
//		}
//		String res = "";
//		List<Param> query = item.getRequest().getUrl().getQuery();
//		for(Param param: query){
//			res += param.getKey()+"="+param.getValue()+"&";
//		}
//		return res.substring(0,res.length()-1).
//				replaceAll("\\{\\{","").
//				replaceAll("}}","").
//				replaceAll("&","&amp;");
//	}
//
//	public String getFormDataParams(Item item){
//		String res = "";
//		List<Param> query = item.getRequest().getUrl().getQuery();
//		for(Param param: query){
//			res += "Content-Disposition: form-data; name=\""+param.getKey()+"\"\n";
//			res += param.getValue()+"\n";
//		}
//		res += "------WebKitFormBoundary7MA4YWxkTrZu0gW--";
//		return res;
//	}
//
//
//	public String getLinesExampleBody(String body) throws IOException {
//		String[] split = body.split("\\n");
//		String res = "";
//		for(String line: split){
//			//去掉-和“之间的说明
//			if(line.contains("\"")&&line.split("\"").length>1){
//				String[] texts = line.split("\"")[1].split("-");
//				if(texts.length==2){
//					line = line.replace("-"+texts[1],"");
//				}
//			}
//			res += getLineStr(line);
//		}
//		return res;
//	}
//
//	public String getLinesBody(String body) throws IOException {
//		String[] split = body.split("\\n");
//		String res = "";
//		for(String line: split){
//			res += getLineStr(line);
//		}
//		return res;
//	}
//
//	public String getLineStr(String line) throws IOException {
//		return getStr("line.ftl").replace("content",line);
//	}
//
//	public String contentType(Item item){
//		List<Header> headers = item.getRequest().getHeader();
//		for(Header header:headers){
//			if(header.getKey().equalsIgnoreCase("Content-Type")){
//				if(header.getValue().equalsIgnoreCase(formDataContentType)){
//					return header.getValue()+"; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW";
//				}
//				return header.getValue();
//			}
//		}
//		return defaultContentType;
//	}
//
//
//	public String getUrl(Item item){
//		String raw = item.getRequest().getUrl().getRaw();
//		raw = raw.replace("{{baseUrl}}","/");
//		if(raw.contains("?")){
//			raw = raw.substring(0,raw.indexOf("?"));
//		}
//		return raw;
//	}
//}
