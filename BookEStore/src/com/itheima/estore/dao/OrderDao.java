package com.itheima.estore.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.itheima.estore.domain.Book;
import com.itheima.estore.domain.Order;
import com.itheima.estore.domain.OrderItem;
import com.itheima.estore.utils.JDBCUtilsForC3P0;

/**
 * 订单模块的dao层
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 21:26:48
 */
public class OrderDao {

	/**
	 * 保存订单
	 * 
	 * @param connection 事务
	 * @param order 订单
	 */
	public void saveOrder(Connection connection,Order order) {
		QueryRunner qr = new QueryRunner();
		//编写sql
		String sql = "insert into orders values (?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getTotal(),order.getOrdertime(),
				order.getState(),order.getAddress(),order.getUser().getUid()};
		try {
			qr.update(connection,sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 保存订单项
	 * 
	 * @param connection 事务
	 * @param orderItem 订单项
	 */
	public void saveOrder(Connection connection, OrderItem orderItem) {
		QueryRunner qr = new QueryRunner();
		//编写sql
		String sql = "insert into orderItem values (?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
				orderItem.getBook().getBid(),orderItem.getOrder().getOid()};
		try {
			qr.update(connection,sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 查询我的订单:根据Uid(用户id)查询该用户的订单
	 * 
	 * @param existuser 登录的用户
	 * @return 订单
	 */
	public List<Order> findByUid(String uid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders where uid = ? order by ordertime desc";
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
			//遍历list集合,获取各个订单项
			for (Order order : list) {
				sql = "select * from orderitem o,book b where o.bid = b.bid and o.oid = ?";
				List<Map<String,Object>> oList = qr.query(sql, new MapListHandler(), order.getOid());
				//获得list中每个map
				for (Map<String, Object> map : oList) {
					Book book = new Book();
					try {
						BeanUtils.populate(book, map);
						
						OrderItem orderItem = new OrderItem();
						BeanUtils.populate(orderItem, map);
						orderItem.setBook(book);
						orderItem.setOrder(order);
						
						//将新封装的订单项添加到订单项集合
						order.getOrderItems().add(orderItem);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return list;
		
	}

	/**
	 * 根据订单id查询订单
	 * 
	 * @param oid 订单id
	 * @return 订单
	 */
	public Order findByOid(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders where oid = ?";
		Order order = null;
		try {
			order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
			
			sql = "select * from orderItem o,book b where o.bid = b.bid and o.oid = ?";
			List<Map<String, Object>> olist = qr.query(sql, new MapListHandler(), oid);
			for (Map<String, Object> map : olist) {
				try {
					//将属于book的属性封装到book中
					Book book = new Book();
					BeanUtils.populate(book, map);
					
					//将属于orderItem的属性封装到orderItem中
					OrderItem orderItem = new OrderItem();
					BeanUtils.populate(orderItem, map);
					orderItem.setBook(book);
					orderItem.setOrder(order);
					
					//将新封装的订单项添加到订单项集合
					order.getOrderItems().add(orderItem);
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return order;
	}

	/**
	 * 修改订单地址
	 * 
	 * @param order 修改后的order对象
	 */
	public void update(Order order) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "update orders set total = ?,ordertime = ?,state = ?,address = ? where oid = ?";
		Object[] params = {order.getTotal(),order.getOrdertime(),order.getState(),
				order.getAddress(),order.getOid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 查询所有订单(非异步)
	 * 
	 * @return 所有订单集合
	 */
	/*public List<Order> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders order by ordertime desc";
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class));
			for (Order order : list) {
				sql = "select * from orderItem o,book b where o.bid = b.bid and o.oid = ?";
				List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(), order.getOid());
				for (Map<String, Object> map : oList) {
					Book book = new Book();
					//封装数据
					BeanUtils.populate(book, map);
					
					OrderItem orderItem = new OrderItem();
					orderItem.setOrder(order);
					orderItem.setBook(book);
					
					order.getOrderItems().add(orderItem);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}  catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}*/

	/**
	 * 查询所有订单(异步)
	 * 
	 * @return 所有订单集合
	 */
	public List<Order> findAll() {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders order by ordertime desc";
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class));
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}  
		return list;
	}
	
	/**
	 * 根据订单状态查询(非异步)
	 * 
	 * @param state 订单状态
	 * @return 改状态下的订单集合
	 */
	/*public List<Order> findByState(int state) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders where state = ? order by ordertime desc";
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class),state);
			for (Order order : list) {
				sql = "select * from orderItem o,book b where o.bid = b.bid and o.oid = ?";
				List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(), order.getOid());
				for (Map<String, Object> map : oList) {
					Book book = new Book();
					//封装数据
					BeanUtils.populate(book, map);
					
					OrderItem orderItem = new OrderItem();
					orderItem.setOrder(order);
					orderItem.setBook(book);
					
					order.getOrderItems().add(orderItem);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}  catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}*/

	/**
	 * 根据订单状态查询(异步)
	 * 
	 * @param state 订单状态
	 * @return 改状态下的订单集合
	 */
	public List<Order> findByState(int state) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orders where state = ? order by ordertime desc";
		List<Order> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Order>(Order.class),state);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}  
		return list;
	}

	/**
	 * 根据oid查询订单详情
	 * 
	 * @param oid 订单id
	 * @return 订单项(多个订单)
	 */
	public List<OrderItem> showDetial(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtilsForC3P0.getDataSource());
		//编写sql
		String sql = "select * from orderitem o,book b where o.bid = b.bid and o.oid = ?";
		
		List<OrderItem> list = new ArrayList<>();
		
		List<Map<String, Object>> oList = null;
		try {
			oList = qr.query(sql, new MapListHandler(), oid);
			for (Map<String, Object> map : oList) {
				Book book = new Book();
				//封装数据
				BeanUtils.populate(book, map);
				
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				orderItem.setBook(book);
				
				list.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
