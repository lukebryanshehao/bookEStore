package com.itheima.estore.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单实体
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 20:00:02
 */
public class Order {

	/**订单id*/
	private String oid;

	/**总计*/
	private double total;

	/**时间*/
	private Date ordertime;

	/**订单状态*/
	private int state;	//1:未付款	2:已付款,未发货	3:已发货,未收货	4:已收货,订单结束

	/**收货地址*/
	private String address;
	
	/**所属用户*/
	private User user;
	
	/**订单项(一个订单包含多个订单项)*/
	private List<OrderItem> orderItems = new ArrayList<>();

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
}
