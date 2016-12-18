package com.itheima.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.estore.domain.Book;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 书籍管理的dao层
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 20:54:07
 */
public class BookDao {

	/**
	 * 显示所有书籍
	 * 
	 * @return 所有书籍的list集合
	 */
	public List<Book> showAll() {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql	注意书籍上架状态isdel
		String sql = "select * from book where isdel = ?";
		List<Book> list = null;
		try {
			//注意书籍上架状态isdel 0:上架  1:下架
			list = qr.query(sql, new BeanListHandler<Book>(Book.class),0);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}

	/**
	 * 根据cid分别显示所有书籍
	 * 
	 * @param cid 书籍类型对应的id
	 * @return 书籍list集合
	 */
	public List<Book> findByCid(String cid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql	
		String sql = "select * from book where cid = ?";
		List<Book> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
	}

	/**
	 * 根据bid查询某个图书的详情
	 * 
	 * @param bid 书籍id
	 * @return 书封装类
	 */
	public Book findByBid(String bid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from book where bid = ?";
		Book book = null;
		try {
			book = qr.query(sql, new BeanHandler<Book>(Book.class), bid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return book;
	}

	/**
	 * 保存图书
	 * 
	 * @param book 封装的book
	 */
	public void save(Book book) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "insert into book values (?,?,?,?,?,?,?)";
		Object[] params = {book.getBid(),book.getBname(),book.getPrice(),book.getAuthor(),
				book.getImage(),book.getCid(),book.getIsdel()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 修改图书
	 * 
	 * @param book 封装的book
	 */
	public void update(Book book) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "update book set bname=?,price=?,author=?,image=?,cid=?,isdel=? where bid=?";
		Object[] params = {book.getBname(),book.getPrice(),book.getAuthor(),
				book.getImage(),book.getCid(),book.getIsdel(),book.getBid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 查询所有下架的图书
	 * 
	 * @return 所有下架的图书
	 */
	public List<Book> showAllPushDowm() {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from book where isdel = ?";
		List<Book> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Book>(Book.class), 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
