package com.itheima.estore.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;

import com.itheima.estore.domain.User;
import com.itheima.estore.service.UserService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 用户管理的servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 19:27:30
 */
public class UserServlet extends BaseServlet {

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response){
		System.out.println("抵达注册");
		//接受数据
		Map<String, String[]> map = request.getParameterMap();
		
		//封装数据
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			//调用业务层
			UserService service = new UserService();
			service.regist(user);
			//页面的跳转
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		request.setAttribute("msg", "用户注册成功!请前往邮箱激活!");
		return "/jsps/msg.jsp";
	}
	
	/**
	 * 校验用户名是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String checkName(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入校验");
		//获取输入框值
		String username = request.getParameter("username");
		
		UserService service = new UserService();
		User user = service.checkName(username);
		//判断user
		if(user == null){
			//没找到该用户,可以使用该用户名
			try {
				response.getWriter().println(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//找到了该用户,不可以使用该用户名
			try {
				response.getWriter().println(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 用户激活的方法 active
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String active(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入激活码校验");
		//接收激活码
		String code = request.getParameter("code");
		//根据激活码查询是否有这个人
		//调用业务层
		UserService service = new UserService();
		User user = service.findByCode(code);
		if(user == null){
			//没找到,显示错误信息
			request.setAttribute("msg", "激活码不正确,激活失败,请返回重新激活!");
			//request.getRequestDispatcher("").forward(request, response);
		}else {
			//找到了,修改激活状态为1,并且将激活码置为空
			user.setState(1);
			user.setCode(null);
			service.update(user);
			request.setAttribute("msg", "激活成功!");
		}
		return "/jsps/msg.jsp";
	}
	
	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @return 
	 */
	public String login(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入登录servlet层");
		//接收数据
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		try {
			BeanUtils.populate(user, map);
			
			//传递到service层
			UserService service = new UserService();
			User existuser = service.login(user);
			
			//判断该用户
			if(existuser == null){
				//数据库中没有三个信息(username,password,state)都与之匹配的用户
				request.setAttribute("msg", "用户名或密码不正确,或者您没有激活");
				//return "/jsps/user/login.jsp";
				return "/jsps/msg.jsp";
			}else {
				//将信息存到session中
				request.getSession().setAttribute("existuser", existuser);
				//重定向
				response.sendRedirect(request.getContextPath()+"/jsps/main.jsp");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 退出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String exist(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入退出servlet层");
		//退出即销毁session
		//获得session
		HttpSession session = request.getSession();
		//销毁session
		session.invalidate();
		//页面跳转
		try {
			response.sendRedirect(request.getContextPath()+"/jsps/main.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
