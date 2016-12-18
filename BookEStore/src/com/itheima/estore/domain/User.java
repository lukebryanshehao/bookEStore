package com.itheima.estore.domain;

/**
 * 用户管理的实体类
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 19:13:30
 */
public class User {

	/**用户ID*/
	private String uid;
	
	/**用户名*/
	private String username;
	
	/**密码*/
	private String password;
	
	/**邮箱*/
	private String email;
	
	/**状态*/
	private int state;
	
	/**激活码*/
	private String code;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
