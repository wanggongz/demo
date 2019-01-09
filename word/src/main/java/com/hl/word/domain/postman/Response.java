package com.hl.word.domain.postman;

/**
 * @author ws
 * @date 2018/12/29 11:20
 */
public class Response{
	private String name;
	private String status;
	private int code;
	private String _postman_previewlanguage;
	private String body;
	private Object originalRequest;
	private Object cookie;
	private Object header;

	public Object getOriginalRequest() {
		return originalRequest;
	}

	public void setOriginalRequest(Object originalRequest) {
		this.originalRequest = originalRequest;
	}

	public Object getCookie() {
		return cookie;
	}

	public void setCookie(Object cookie) {
		this.cookie = cookie;
	}

	public Object getHeader() {
		return header;
	}

	public void setHeader(Object header) {
		this.header = header;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String get_postman_previewlanguage() {
		return _postman_previewlanguage;
	}

	public void set_postman_previewlanguage(String _postman_previewlanguage) {
		this._postman_previewlanguage = _postman_previewlanguage;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
