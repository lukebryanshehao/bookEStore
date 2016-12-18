package com.itheima.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Book;
import com.itheima.estore.service.BookService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 书籍管理servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-3 20:54:26
 */
public class BookServlet extends BaseServlet {

	/**
	 * 显示所有书籍
	 * 
	 * @param request
	 * @param response
	 * @return 所有书籍的list集合
	 */
	public String showAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了显示所有书籍的servlet层!!!!");
		BookService service = new BookService();
		List<Book> list = service.showAll();
		//页面跳转
		request.setAttribute("list", list);
		return "/jsps/book/list.jsp";
	}
	
	/**
	 * 显示某个分类的图书
	 * 
	 * @param request
	 * @param response
	 * @return 书籍list集合
	 */
	public String findByCid(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了显示某个分类的图书");
		String cid = request.getParameter("cid");
		BookService service = new BookService();
		List<Book> list = service.findByCid(cid);
		request.setAttribute("list", list);
		return "/jsps/book/list.jsp";
		
	}
	
	/**
	 * 查询某个图书的详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByBid(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了查询某个图书的详情servlet");
		//获取参数
		String bid = request.getParameter("bid");
		//传递到service层
		BookService service = new BookService();
		Book book = service.findByBid(bid);
		//页面跳转
		request.setAttribute("book", book);
		return "/jsps/book/desc.jsp";
		
	}
}
