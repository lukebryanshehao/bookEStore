package com.itheima.estore.service;

import java.util.List;

import com.itheima.estore.dao.BookDao;
import com.itheima.estore.domain.Book;

/**
 * 书籍管理的service层
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 20:54:16
 */
public class BookService {

	/**
	 * 显示所有书籍
	 * 
	 * @return 所有书籍的list集合
	 */
	public List<Book> showAll() {
		BookDao dao = new BookDao();
		return dao.showAll();
	}

	/**
	 * 根据cid显示某个分类的图书
	 * 
	 * @param cid 书籍类型对应的id
	 * @return 书籍list集合
	 */
	public List<Book> findByCid(String cid) {
		BookDao dao = new BookDao();
		return dao.findByCid(cid);
	}

	/**
	 * 根据bid查询某个图书的详情
	 * 
	 * @param bid
	 * @return
	 */
	public Book findByBid(String bid) {
		BookDao dao = new BookDao();
		return dao.findByBid(bid);
	}

	/**
	 * 保存图书
	 * 
	 * @param book 封装的book
	 */
	public void save(Book book) {
		BookDao dao = new BookDao();
		dao.save(book);
		
	}

	/**
	 * 修改图书
	 * 
	 * @param book 封装的book
	 */
	public void update(Book book) {
		BookDao dao = new BookDao();
		dao.update(book);
		
	}

	/**
	 * 查询所有下架的图书
	 * 
	 * @return 所有下架的图书
	 */
	public List<Book> showAllPushDowm() {
		BookDao dao = new BookDao();
		return dao.showAllPushDowm();
	}

}
