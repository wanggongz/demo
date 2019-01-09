package com.hl.word.domain;

/**
 * @author ws
 * @date 2019/1/2 10:10
 */
public class ResponseBody {
	private String code;
	private String message;
	private String success;
	private Object data;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
