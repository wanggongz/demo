package com.hl.word.domain.postman;

import java.util.List;

/**
 * 请求
 */
public class Request{

	private String method;

	private List<Header> header;

	private Body body;

	private Url url;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Header> getHeader() {
		return header;
	}

	public void setHeader(List<Header> header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Url getUrl() {
		return url;
	}

	public void setUrl(Url url) {
		this.url = url;
	}
}
