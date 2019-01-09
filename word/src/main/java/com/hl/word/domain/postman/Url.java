package com.hl.word.domain.postman;

import java.util.List;

/**
 * @author ws
 * @date 2018/12/29 11:20
 */
public class Url{
	private String raw;
	private List<String> host;
	private List<String> path;
	private List<Param> query;

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public List<String> getHost() {
		return host;
	}

	public void setHost(List<String> host) {
		this.host = host;
	}


	public List<String> getPath() {
		return path;
	}

	public void setPath(List<String> path) {
		this.path = path;
	}

	public List<Param> getQuery() {
		return query;
	}

	public void setQuery(List<Param> query) {
		this.query = query;
	}
}
