package com.hl.word.domain.postman.environment;

/**
 * @author ws
 * @date 2019/1/4 9:43
 */
public class EnvironmentValue {

	private String key;

	private String value;

	private boolean enabled;

	private Object description;

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Object getDescription() {
		return description;
	}

	public void setDescription(Object description) {
		this.description = description;
	}
}
