package com.itheima.estore.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.json.JsonArray;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.itheima.estore.domain.Order;
import com.itheima.estore.domain.OrderItem;
import com.itheima.estore.service.OrderService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 后台订单查询servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-7 22:04:49
 */
public class AdminOrderServlet extends BaseServlet {

	/**
	 * 查询所有订单
	 * 
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("查询所有订单");
		//接收参数:state是否为null
		String value = request.getParameter("state");
		//定义一个list集合存储返回的订单
		List<Order> list = null;
		//创建业务层对象
		OrderService service =new OrderService();
		//判断参数
		if(value == null || "".equals(value)){
			//查询所有
			//调用service层
			list = service.findAll();
		}else{
			//按状态查询
			int state = Integer.parseInt(value);
			//调用service层
			list = service.findByState(state);
		}
		//页面跳转
		request.setAttribute("list", list);
		return "/adminjsps/admin/order/ajaxList.jsp";
	}
	
	/**
	 * 修改订单状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response){
		System.out.println("修改订单状态");
		//接收参数
		String oid = request.getParameter("oid");
		//调用service层
		OrderService service = new OrderService();
		Order order =service.findByOid(oid);
		order.setState(3);
		service.update(order);
		return findAll(request, response);
	}
	
	/**
	 * 显示订单详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String showDetial(HttpServletRequest request, HttpServletResponse response){
		System.out.println("显示订单详情");
		//接收参数
		String oid = request.getParameter("oid");
		//调用service层
		OrderService service = new OrderService();
		List<OrderItem> list = service.showDetial(oid);
		//向页面输出
		//转出json格式显示到页面
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"itemid","order"});
		JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
		System.out.println(jsonArray);
		try {
			response.getWriter().print(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
