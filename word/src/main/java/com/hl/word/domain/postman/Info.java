package com.hl.word.domain.postman;

/**
 * 没用到
 *
 * @author ws
 * @date 2018/12/29 11:20
 */
public class Info{
	private String _postman_id;

	private String name;

	private String schema;

	public String get_postman_id() {
		return _postman_id;
	}

	public void set_postman_id(String _postman_id) {
		this._postman_id = _postman_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
