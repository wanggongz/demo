package com.hl.word.util;

/**
 * @author ws
 * @date 2019/1/3 10:22
 */
public class StringUtil {
	
	static String lines = "{\n" +
			"    \"data\": {\n" +
			"        \"pageData\": [\n" +
			"            {\n" +
			"                \"streamNumber-班组标识\": \"7777771111\",\n" +
			"                \"corpId-企业号\": \"777777\",\n" +
			"                \"tenantDeptStreamNum\": null,\n" +
			"                \"grpName-班组名称\": \"wangsheng\",\n" +
			"                \"tenantName-租户名称\": \"刘子航3\",\n" +
			"                \"groupMemberList-组员列表\": [],\n" +
			"                \"oid\": \"7777771111\"\n" +
			"            }\n" +
			"        ],\n" +
			"        \"total\": 1\n" +
			"    },\n" +
			"    \"code\": 200,\n" +
			"    \"message\": \"成功\",\n" +
			"    \"success\": true\n" +
			"}";
	
	public static void lines(){
		String[] split = lines.split("\\n");
		for(String line: split){
			System.out.println(line);
		}
	}
}
