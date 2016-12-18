package com.itheima.estore.domain;

/**
 * 订单项实体
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 20:21:55
 */
public class OrderItem {

	/**订单项id*/
	private String itemid;
	
	/**订单项数量*/
	private int count;

	/**订单项小计*/
	private double subtotal;
	
	//Hibernate:ORM框架--Object Relational Mapping

	/**订单项对应的书籍信息*/
	private Book book;

	/**订单项对应订单信息*/
	private Order order;

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
}
