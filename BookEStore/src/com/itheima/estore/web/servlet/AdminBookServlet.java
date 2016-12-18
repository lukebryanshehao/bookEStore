package com.itheima.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Book;
import com.itheima.estore.domain.Category;
import com.itheima.estore.service.BookService;
import com.itheima.estore.service.CategoryService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 后台图书管理的servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-7 16:33:38
 */
public class AdminBookServlet extends BaseServlet {

	/**
	 * 后台查询所有图书
	 * 
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台查询所有图书");
		//代用service层
		BookService service = new BookService();
		List<Book> list = service.showAll();
		//System.out.println(list.get(0));
		//页面跳转
		request.setAttribute("list", list);
		return "/adminjsps/admin/book/list.jsp";
	}
	
	/**
	 * 跳转到添加图书页面的方法
	 * 
	 * @return
	 */
	public String saveUI(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台管理中跳转到添加图书的方法");
		//查询所有分类
		CategoryService categoryService = new CategoryService();
		List<Category> Categorylist = categoryService.showAll();
		//跳转页面
		request.setAttribute("Categorylist", Categorylist);
		return "adminjsps/admin/book/add.jsp";
	}
	
	/**
	 * 编辑图书
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response){
		System.out.println("编辑图书");
		//接收参数
		String bid = request.getParameter("bid");
		//调用service层查询图书
		BookService service = new BookService();
		Book book = service.findByBid(bid);
		//查询所有分类
		CategoryService categoryService = new CategoryService();
		List<Category> list = categoryService.showAll();
		//页面跳转
		request.setAttribute("book", book);
		request.setAttribute("list", list);
		return "/adminjsps/admin/book/desc.jsp";
	}
	
	/**
	 * 下架图书
	 * 
	 * @return
	 */
	public String pushDown(HttpServletRequest request, HttpServletResponse response){
		System.out.println("下架图书");
		//接收参数
		String bid = request.getParameter("bid");
		//调用service层查询该book信息
		BookService service = new BookService();
		Book book = service.findByBid(bid);
		//设置下架
		book.setIsdel(1);
		//修改
		service.update(book);
		//页面跳转
		return findAll(request, response);
	}
	
	/**
	 * 跳转到上架图书页面的方法
	 * 
	 * @return
	 */
	public String pushDownUI(HttpServletRequest request, HttpServletResponse response){
		System.out.println("跳转到图书上架页面");
		//调用service层查询所有下架的图书
		BookService service = new BookService();
		List<Book> list = service.showAllPushDowm();
		//页面跳转
		request.setAttribute("list", list);
		return "/adminjsps/admin/book/pushUp.jsp";
	}
	
	public String pushUp(HttpServletRequest request, HttpServletResponse response){
		System.out.println("图书上架");
		//接收参数
		String bid = request.getParameter("bid");
		//调用service层查询该book信息
		BookService service = new BookService();
		Book book = service.findByBid(bid);
		//设置上架
		book.setIsdel(0);
		//修改
		service.update(book);
		//页面跳转
		return findAll(request, response);
	}
}
