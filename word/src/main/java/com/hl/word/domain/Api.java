package com.hl.word.domain;

/**
 * @author ws
 * @date 2019/1/3 14:48
 */
public class Api {

	private String name;

	private String level;

	private String isTitle;

	private String requestExample;

	private String responseExample;

	private String requestTable;

	private String responseTables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(String isTitle) {
		this.isTitle = isTitle;
	}

	public String getRequestExample() {
		return requestExample;
	}

	public void setRequestExample(String requestExample) {
		this.requestExample = requestExample;
	}

	public String getResponseExample() {
		return responseExample;
	}

	public void setResponseExample(String responseExample) {
		this.responseExample = responseExample;
	}

	public String getRequestTable() {
		return requestTable;
	}

	public void setRequestTable(String requestTable) {
		this.requestTable = requestTable;
	}

	public String getResponseTables() {
		return responseTables;
	}

	public void setResponseTables(String responseTables) {
		this.responseTables = responseTables;
	}
}
