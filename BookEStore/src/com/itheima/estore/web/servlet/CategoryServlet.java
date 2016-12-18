package com.itheima.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Category;
import com.itheima.estore.service.CategoryService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 分类管理的servlet
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 20:54:47
 */
public class CategoryServlet extends BaseServlet {

	/**
	 * 显示所有书
	 * 
	 * @param request
	 * @param response
	 * @return 所有书的list集合
	 */
	public String showAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了所有书籍分类管理层");
		CategoryService service = new CategoryService();
		List<Category> list = service.showAll();
		//页面跳转
		request.setAttribute("list", list);
		return "/jsps/left.jsp";
		
	}
}
