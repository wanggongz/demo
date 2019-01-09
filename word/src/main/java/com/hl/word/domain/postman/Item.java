package com.hl.word.domain.postman;

import java.util.List;
import java.util.Map;

/**
 * @author ws
 * @date 2018/12/29 11:20
 */
public class Item{

	String name;
	private List<Item> item;
	private Request request;
	private List<Response> response;
	private Object event;
	private boolean _postman_isSubFolder;
	private Object protocolProfileBehavior;
	private String description;

	/****************以下为扩展字段，非postman的字段****************/

	/**
	 * response有多个,为string类型转换为Param对象存储在map中，
	 * 主信息键值为main，其他map键值为字段名称
	 */
	private Map<String,List<Param>> responseTableList;

	/**
	 * response有多个,但一般只有一个，该值为第一个response
	 */
	private String responseString;

	/**
	 * 等级，根据等级输出对应的title
	 */
	private Integer level;

	/**
	 * 唯一标识，为层级名称相加‘-’隔开
	 */
	private String id;

	/**
	 * 父id
	 */
	private String parentId;

	/**
	 * 是否还有子节点
	 */
	private boolean hasSons;


	public String getResponseString() {
		List<Response> response = this.response;
		if(response!=null&&response.size()!=0&&response.get(0)!=null&&!response.get(0).equals("")){
			return response.get(0).getBody();
		}else{
			return "";
		}
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public boolean isHasSons() {
		return hasSons;
	}

	public void setHasSons(boolean hasSons) {
		this.hasSons = hasSons;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	public Map<String, List<Param>> getResponseTableList() {
		return responseTableList;
	}

	public void setResponseTableList(Map<String, List<Param>> responseTableList) {
		this.responseTableList = responseTableList;
	}

	public Object getProtocolProfileBehavior() {
		return protocolProfileBehavior;
	}

	public void setProtocolProfileBehavior(Object protocolProfileBehavior) {
		this.protocolProfileBehavior = protocolProfileBehavior;
	}

	public boolean is_postman_isSubFolder() {
		return _postman_isSubFolder;
	}

	public void set_postman_isSubFolder(boolean _postman_isSubFolder) {
		this._postman_isSubFolder = _postman_isSubFolder;
	}

	public Object getEvent() {
		return event;
	}

	public void setEvent(Object event) {
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getItem() {
		return item;
	}

	public void setItem(List<Item> item) {
		this.item = item;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public List<Response> getResponse() {
		return response;
	}

	public void setResponse(List<Response> response) {
		this.response = response;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
