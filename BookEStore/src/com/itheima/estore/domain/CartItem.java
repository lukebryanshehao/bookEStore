package com.itheima.estore.domain;

/**
 * 购物项实体
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 00:52:47
 */
public class CartItem {

	/**书信息*/
	private Book book;
	
	/**书对应数量*/
	private int count;
	
	/**价格*/
	private double subtotal;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public double getSubtotal(){
		return (book.getPrice())*count;
	}
	
}
