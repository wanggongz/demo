package com.hl.word.domain;

/**
 * @author ws
 * @date 2019/1/7 13:55
 */
public class Result {

	private String code;

	private String mesage;

	public static Result failResult(String mesage){
		Result result = new Result();
		result.setMesage(mesage);
		return result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMesage() {
		return mesage;
	}

	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
}
