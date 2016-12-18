package com.itheima.estore.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.estore.dao.OrderDao;
import com.itheima.estore.domain.Order;
import com.itheima.estore.domain.OrderItem;
import com.itheima.estore.domain.User;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 订单模块的service层
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 21:25:13
 */
public class OrderService {

	/**
	 * 保存订单
	 * 
	 * @param order 订单
	 */
	public void saveOrder(Order order) {
		//开启事务,保存两个(订单项,订单)
		Connection connection = JDBCUtilsForC3P0.getConnection();
		try {
			//设置事务不自动提交
			connection.setAutoCommit(false);
			//保存订单
			OrderDao dao = new OrderDao();
			dao.saveOrder(connection,order);
			//保存订单项(一个订单中有多个订单项)
			for (OrderItem orderItem : order.getOrderItems()) {
				dao.saveOrder(connection,orderItem);
			}
			//事务提交(安静的提交)
			DbUtils.commitAndCloseQuietly(connection);
		} catch (SQLException e) {
			e.printStackTrace();
			//回滚
			DbUtils.rollbackAndCloseQuietly(connection);
			
		}
		
		
	}

	/**
	 * 查询我的订单:根据Uid(用户id)查询该用户的订单
	 * 
	 * @param existuser 登录的用户
	 * @return 订单
	 */
	public List<Order> findByUid(String uid) {
		OrderDao dao = new OrderDao();
		return dao.findByUid(uid);
	}

	/**
	 * 根据订单id查询订单
	 * 
	 * @param oid 订单id
	 * @return 订单
	 */
	public Order findByOid(String oid) {
		OrderDao dao = new OrderDao();
		return dao.findByOid(oid);
	}

	/**
	 * 修改订单地址
	 * 
	 * @param order 修改后的order对象
	 */
	public void update(Order order) {
		OrderDao dao = new OrderDao();
		dao.update(order);
	}

	/**
	 * 查询所有订单
	 * 
	 * @return 所有订单集合
	 */
	public List<Order> findAll() {
		OrderDao dao = new OrderDao();
		return dao.findAll();
	}

	/**
	 * 根据订单状态查询
	 * 
	 * @param state 订单状态
	 * @return 改状态下的订单集合
	 */
	public List<Order> findByState(int state) {
		OrderDao dao = new OrderDao();
		return dao.findByState(state);
	}

	/**
	 * 根据oid查询订单详情
	 * 
	 * @param oid 订单id
	 * @return 订单项(多个订单)
	 */
	public List<OrderItem> showDetial(String oid) {
		OrderDao dao = new OrderDao();
		return dao.showDetial(oid);
	}

}
