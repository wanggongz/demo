package com.hl.word.domain.postman.environment;

import com.hl.word.domain.postman.Param;

import java.util.List;

/**
 * @author ws
 * @date 2019/1/4 9:39
 */
public class Environment {

	private String id;

	private String name;

	private List<EnvironmentValue> values;

	private String _postman_variable_scope;

	private String _postman_exported_at;

	private String _postman_exported_using;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EnvironmentValue> getValues() {
		return values;
	}

	public void setValues(List<EnvironmentValue> values) {
		this.values = values;
	}

	public String get_postman_variable_scope() {
		return _postman_variable_scope;
	}

	public void set_postman_variable_scope(String _postman_variable_scope) {
		this._postman_variable_scope = _postman_variable_scope;
	}

	public String get_postman_exported_at() {
		return _postman_exported_at;
	}

	public void set_postman_exported_at(String _postman_exported_at) {
		this._postman_exported_at = _postman_exported_at;
	}

	public String get_postman_exported_using() {
		return _postman_exported_using;
	}

	public void set_postman_exported_using(String _postman_exported_using) {
		this._postman_exported_using = _postman_exported_using;
	}
}
