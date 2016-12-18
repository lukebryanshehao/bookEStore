package com.itheima.estore.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.itheima.estore.domain.User;

/**
 * 权限过滤器
 * 
 * @author shehao1
 * @version 1.0 2016-11-6 14:41:12
 */
public class PrivilegeFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		//获取用户信息
		User existuser = (User) req.getSession().getAttribute("existuser");
		//判断用户是否存在
		if(existuser == null){
			request.setAttribute("msg", "您还没有登录,权限不够");
			request.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
			return;
		}else{
			chain.doFilter(req, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
