package com.itheima.estore.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Cart;
import com.itheima.estore.domain.CartItem;
import com.itheima.estore.domain.Order;
import com.itheima.estore.domain.OrderItem;
import com.itheima.estore.domain.User;
import com.itheima.estore.service.OrderService;
import com.itheima.estore.utils.BaseServlet;
import com.itheima.estore.utils.PaymentUtil;
import com.itheima.estore.utils.UUIDUtils;

/**
 * 订单模块的servlet
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 20:41:34
 */
public class OrderServlet extends BaseServlet {

	/**
	 * 生成订单的方法
	 * 
	 * @return
	 */
	public String saveOrder(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了生成订单");
		//封装Order对象
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1); 	//1:未付款
		
		//订单总金额从购物车中获取
		//从购物车中获得总计
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//判断购物车
		if(cart == null){
			request.setAttribute("msg", "您的购物车为空,无法生成订单");
			return "/jsps/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		
		//设置订单所属用户
		User user = (User) request.getSession().getAttribute("existuser");
		//判断用户
		if(user == null){
			request.setAttribute("msg", "您还没有登录,请先登录");
			return "/jsps/user/login.jsp";
		}
		order.setUser(user);
		
		//设置订单项集合
		//订单项数据从购物项中获得
		//遍历购物车，获得每个购物项
		for (CartItem cartItem : cart.getCartItems()) {
			//封装成订单项
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		//调用业务层保存订单
		OrderService service = new OrderService();
		service.saveOrder(order);
		
		//清空购物车
		cart.cleanCart();
		//页面跳转
		request.setAttribute("order", order);
		return "/jsps/order/desc.jsp";
		
	}
	
	/**
	 * 根据Uid(用户id)查询该用户的订单
	 * 
	 * @return
	 */
	public String findByUid(HttpServletRequest request, HttpServletResponse response){
		System.out.println("查询我的订单");
		//从session中获得用户信息
		User existuser = (User) request.getSession().getAttribute("existuser");
		//调用service层该用户的订单
		OrderService service = new OrderService();
		//一个用户有多个订单
		List<Order> list = service.findByUid(existuser.getUid());
		//页面跳转
		request.setAttribute("list", list);
		return "/jsps/order/list.jsp";
		
	}
	
	/**
	 * 根据订单id查询订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByOid(HttpServletRequest request, HttpServletResponse response){
		System.out.println("根据订单id查询订单");
		//接收参数
		String oid = request.getParameter("oid");
		//传递到Service层
		OrderService service = new OrderService();
		Order order = service.findByOid(oid);
		//页面跳转
		request.setAttribute("order", order);
		return "/jsps/order/desc.jsp";
		
	}
	
	/**
	 * 订单支付
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String payOrder(HttpServletRequest request, HttpServletResponse response){
		System.out.println("订单支付");
		//接收参数
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		//修改订单地址
		OrderService service = new OrderService();
		Order order = service.findByOid(oid);
		order.setAddress(address);
		service.update(order);
		String pd_FrpId = request.getParameter("pd_FrpId");
		
		//调用易宝支付接口
		// 组装参数:
		String p0_Cmd = "Buy"; // 业务类型
		String p1_MerId = "10001126856"; // 商户编号
		String p2_Order = oid; // 订单编号
		String p3_Amt = "0.01"; // 支付金额
		String p4_Cur = "CNY"; // 交易币种
		String p5_Pid = ""; // 商品名称
		String p6_Pcat = ""; // 商品种类
		String p7_Pdesc = ""; // 商品描述
		String p8_Url = "http://localhost/BookEStore/OrderServlet?method=callBack"; // URL
		String p9_SAF = ""; // 送货地址
		String pa_MP = ""; // 商户扩展信息
		String pr_NeedResponse = "1"; // 应答机制
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue); // 签名数据
		
		//重定向向易宝发送请求
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		//页面跳转
		try {
			response.sendRedirect(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 付款成功后回执
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String callBack(HttpServletRequest request, HttpServletResponse response){
		System.out.println("付款成功后回执");
		//接收参数
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String hmac = request.getParameter("hmac");
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		boolean flag = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(flag==true){
			//修改订单状态
			OrderService service = new OrderService();
			Order order = service.findByOid(r6_Order);
			order.setState(2);
			service.update(order);
			request.setAttribute("msg", "付款成功!订单编号:"+r6_Order+",支付金额:"+r3_Amt);
			return "/jsps/msg.jsp";
		}
		return null;
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
		Order order = service.findByOid(oid);
		//设置状态
		order.setState(4);
		//修改状态
		service.update(order);
		//页面跳转
		return findByUid(request, response);
	}
}
