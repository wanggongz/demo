package com.hl.word.domain.postman;

/**
 * 参数
 */
public class Param{
	private String key;

	private String value;

	private String type = "String";

	private String description = "说明";

	private boolean disabled;

	private String src;

	/**
	 * 是否必须，扩展字段
	 */
	private String required = "否";

	public String getRequired() {
		if(this.description!=null&&this.description.contains("*")){
			return "是";
		}
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}


	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		if(type.equalsIgnoreCase("text")){
			return "String";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description.replace("*","");
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
