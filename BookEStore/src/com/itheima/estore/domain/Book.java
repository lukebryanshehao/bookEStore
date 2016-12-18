package com.itheima.estore.domain;

/**
 * 书籍实体类
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 20:36:11
 */
public class Book {

	/**书籍ID*/
	private String bid;
	
	/**书籍名称*/
	private String bname;
	
	/**书籍价格*/
	private double price;
	
	/**书籍作者*/
	private String author;
	
	/**书籍图片*/
	private String image;
	
	/**书籍类型对应的id*/
	private String cid;
	
	/**书籍上架状态*/
	private int isdel;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}
	
}
