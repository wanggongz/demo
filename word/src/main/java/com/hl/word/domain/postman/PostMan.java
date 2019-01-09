package com.hl.word.domain.postman;

import java.util.List;

/**
 * @author ws
 * @date 2018/12/29 10:58
 */
public class PostMan {

	private Info info;

	private List<Item> item;

	/**
	 * 已转换的doc格式主内容
	 */
	private String docString;

	public String getDocString() {
		return docString;
	}

	public void setDocString(String docString) {
		this.docString = docString;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}
}


