package com.itheima.estore.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0改造的专用JDBC工具类
 * 
 * @author shehao1
 * @version 1.0 2016年10月22日08:54:30
 */
public class JDBCUtilsForC3P0 {

	/** 数据库连接池  */
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	/**
	 * 获得连接		
	 */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获得连接池
	 */
	public static DataSource getDataSource(){
		return cpds;
	}
}
