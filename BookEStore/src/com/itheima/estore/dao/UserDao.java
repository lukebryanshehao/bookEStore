package com.itheima.estore.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.estore.domain.User;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 用户管理的dao(数据访问)层
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 19:48:07
 */
public class UserDao {

	/**
	 * 注册用户
	 * 
	 * @param user 封装用户信息
	 */
	public void regist(User user) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "insert into user values (?,?,?,?,?,?)";
		
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),
				user.getState(),user.getCode()};
		
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 校验用户名是否存在
	 * 
	 * @param username 输入框中的值
	 * @return user对象
	 */
	public User checkName(String username) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from user where username = ?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), username);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return user;
	}

	/**
	 * 用户激活
	 * 
	 * @param code 邮件链接中传入的激活码
	 * @return 该激活码对应的用户
	 */
	public User findByCode(String code) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from user where code = ?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return user;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user 用户信息
	 */
	public void update(User user) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "update user set username = ?,password = ?, email = ?, state = ?,code = ? where uid = ?";
		
		Object[] params = {user.getUsername(),user.getPassword(),user.getEmail(),
				user.getState(),user.getCode(),user.getUid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	/**
	 * 登录
	 * 
	 * @param user 页面输入的内容对象
	 * @return 查询的user对象
	 */
	public User login(User user) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql	注意激活状态,未激活不能登录
		String sql = "select * from user where username = ? and password = ? and state = ?";
		User existuser = null;
		try {
			//注意激活状态,未激活不能登录
			existuser = qr.query(sql, new BeanHandler<User>(User.class),
					user.getUsername(),user.getPassword(),1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existuser;
	}

}
