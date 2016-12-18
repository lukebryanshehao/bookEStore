package com.itheima.estore.utils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的Servlet工具类
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 18:49:13
 */
public class BaseServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置编码
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//获取参数
		//<a href="userServlet?method=add">添加用户</a>
		String methodName = req.getParameter("method");
		//反射执行该方法(this代表子类)
		Class clazz = this.getClass();
		try {
			//获取该方法
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			
			String result = null;
			//判断method
			if(method != null){
				
					//执行该方法
					result = (String) method.invoke(this, req,resp);
					if(result != null){
						req.getRequestDispatcher(result).forward(req, resp);
					}
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req,resp);
	}

	
}
