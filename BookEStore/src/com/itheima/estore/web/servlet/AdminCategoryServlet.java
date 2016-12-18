package com.itheima.estore.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Category;
import com.itheima.estore.service.CategoryService;
import com.itheima.estore.utils.BaseServlet;
import com.itheima.estore.utils.UUIDUtils;

/**
 * 后天管理的servlet
 * 
 * @author shehao1
 * @version 1.0 2016-11-6 15:08:12
 */
public class AdminCategoryServlet extends BaseServlet {

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台管理查询所有");
		//调用service层
		CategoryService categoryService = new CategoryService();
		List<Category> list = categoryService.showAll();
		//页面跳转
		request.setAttribute("list", list);
		return "adminjsps/admin/category/list.jsp";
		
	}
	
	/**
	 * 跳转到添加分类的方法
	 * 
	 * @return
	 */
	public String saveUI(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台管理跳转到添加分类的方法");
		return "adminjsps/admin/category/add.jsp";
	}
	
	/**
	 * 添加分类
	 * 
	 * @return
	 */
	public String save(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台管理添加分类");
		//接受参数
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getUUID());
		category.setCname(cname);
		//调用业务层
		CategoryService categoryService = new CategoryService();
		categoryService.save(category);
		//页面跳转
		return findAll(request,response);
		
	}
	
	/**
	 * 编辑分类
	 * 
	 * @return
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台编辑分类的方法");
		//接受参数
		String cid = request.getParameter("cid");
		//调用service层
		CategoryService categoryService = new CategoryService();
		Category category = categoryService.findByCid(cid);
		//页面跳转
		request.setAttribute("category", category);
		return "/adminjsps/admin/category/mod.jsp";
		
	}
	
	/**
	 * 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String update(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台修改分类的方法");
		//接受参数
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		//封装数据
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		//调用service层
		CategoryService categoryService = new CategoryService();
		categoryService.update(category);
		//页面跳转
		request.setAttribute("category", category);
		return findAll(request,response);
		
	}
	
	/**
	 * 删除分类
	 * 
	 * @return
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response){
		System.out.println("后台删除分类的方法");
		//接受参数
		String cid = request.getParameter("cid");
		//传输到service层
		//调用service层
		CategoryService categoryService = new CategoryService();
		categoryService.delete(cid);
		//页面跳转
		return findAll(request,response);
		
	}
}
