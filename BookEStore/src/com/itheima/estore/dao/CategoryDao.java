package com.itheima.estore.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.estore.domain.Category;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 分类管理的dao层
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 19:39:43
 */
public class CategoryDao {

	/**
	 * 显示所有书
	 * 
	 * @return 所有书的list集合
	 */
	public List<Category> showAll() {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from category";
		List<Category> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
		
	}

	/**
	 * 添加分类
	 * 
	 * @param category 封装的分类信息
	 */
	public void save(Category category) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "insert into category values (?,?)";
		try {
			qr.update(sql,category.getCid(),category.getCname());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 编辑分类
	 * 
	 * @param cid 分类id
	 * @return 返回一个分类对象
	 */
	public Category findByCid(String cid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from category where cid = ?";
		Category category = null;
		try {
			category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return category;
	}

	/**
	 * 根据cid修改分类信息
	 * 
	 * @param cid 该分类的cid
	 */
	public void update(Category category) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "update category set cname = ? where cid = ?";
		try {
			qr.update(sql, category.getCname(),category.getCid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 删除分类
	 * 
	 * @param cid 该分类id
	 */
	public void delete(Connection connection,String cid) {
		QueryRunner qr = new QueryRunner();
		//编写sql
		//先将cid置为null,因为有外键,分类下如果有数据则删除不了
		String sql = "delete from category where cid = ?";
		
		try {
			//再删除该分类
			qr.update(connection, sql, cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 将cid置为空
	 * 
	 * @param connection
	 * @param cid
	 */
	public void setNull(Connection connection,String cid) {
		QueryRunner qr = new QueryRunner();
		//编写sql
		//先将cid置为null,因为有外键,分类下如果有数据则删除不了
		String sql = "update book set cid = null where cid = ?";
		try {
			qr.update(connection, sql, cid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	
}
