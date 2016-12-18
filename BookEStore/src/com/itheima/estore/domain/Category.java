package com.itheima.estore.domain;

/**
 * 书籍分类实体类
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 19:27:46
 */
public class Category {

	/**书ID*/
	private String cid;
	
	/**书名*/
	private String cname;

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
