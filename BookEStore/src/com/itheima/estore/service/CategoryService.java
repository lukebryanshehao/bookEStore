package com.itheima.estore.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.estore.dao.CategoryDao;
import com.itheima.estore.domain.Category;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 分类管理的service层
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 19:37:18
 */
public class CategoryService {

	/**
	 * 显示所有书
	 * 
	 * @return 所有书的list集合
	 */
	public List<Category> showAll() {
		CategoryDao dao = new CategoryDao();
		return dao.showAll();
	}

	/**
	 * 添加分类
	 * 
	 * @param category 封装的分类信息
	 */
	public void save(Category category) {
		CategoryDao dao = new CategoryDao();
		dao.save(category);
		
	}

	/**
	 * 编辑分类
	 * 
	 * @param cid 分类id
	 * @return 返回一个分类对象
	 */
	public Category findByCid(String cid) {
		CategoryDao dao = new CategoryDao();
		return dao.findByCid(cid);
		
		
	}

	/**
	 * 根据cid修改分类信息
	 * 
	 * @param cid 该分类的cid
	 */
	public void update(Category category) {
		CategoryDao dao = new CategoryDao();
		dao.update(category);
		
	}

	/**
	 * 删除分类
	 * 
	 * @param cid 该分类id
	 */
	public void delete(String cid) {
		//开启事务(先将cid置为null,再删除该分类)
		Connection connection = JDBCUtilsForC3P0.getConnection();
		//设置不自动提交
		try {
			connection.setAutoCommit(false);
			//将cid置为null
			CategoryDao dao = new CategoryDao();
			dao.setNull(connection,cid);
			//再删除该分类
			dao.delete(connection,cid);
			
			//事务提交(安静的提交)
			DbUtils.commitAndCloseQuietly(connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
			//回滚
			DbUtils.rollbackAndCloseQuietly(connection);
		}
	}
	
}
