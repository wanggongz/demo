package com.hl.word.domain.postman;

import java.util.List;

/**
 * 参数体
 */
public class Body{
	private String mode;
	private List<Param> urlencoded;
	private String raw;
	private List<Param> formdata;

	public List<Param> getFormdata() {
		return formdata;
	}

	public void setFormdata(List<Param> formdata) {
		this.formdata = formdata;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Param> getUrlencoded() {
		return urlencoded;
	}

	public void setUrlencoded(List<Param> urlencoded) {
		this.urlencoded = urlencoded;
	}
}
