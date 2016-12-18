package com.itheima.estore.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.estore.domain.Book;
import com.itheima.estore.domain.Cart;
import com.itheima.estore.domain.CartItem;
import com.itheima.estore.service.BookService;
import com.itheima.estore.utils.BaseServlet;

/**
 * 购物车servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 13:57:01
 */
public class CartServlet extends BaseServlet {

	/**
	 * 从session中获取购物车(创建购物车)
	 * 
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request){
		//从session中获取该购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//如果session中没有该购物车?
		if(cart == null){
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
		
	}
	
	/**
	 * 添加到购物车
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String addCart(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了添加到购物车项");
		//获取参数
		int count = Integer.parseInt(request.getParameter("count"));
		String bid = request.getParameter("bid");
		System.out.println(count+"---"+bid);
		//(根据bid查询到该书籍)传输到service层
		BookService service = new BookService();
		Book book = service.findByBid(bid);
		//将数值封装到购物项中
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		//添加到购物车
		//没有购物车(创建一个)
		//Cart cart = new Cart();	//(错误,设置成全局,从session中获取)每次添加一个购物项都要生成一个购物车?
		Cart cart = getCart(request);
		//Cart中的addCart方法
		cart.addCart(cartItem);
		return "/jsps/cart/list.jsp";
		
	}
	
	/**
	 * 清空购物车
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String clearCart(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了清空购物车servlet");
		//获取购物车
		Cart cart = getCart(request);
		//调用Cart中的清空购物车方法
		cart.cleanCart();
		//跳转到该页面
		return "/jsps/cart/list.jsp";
		
	}
	
	/**
	 * 移除该购物项
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public String removeCart(HttpServletRequest request, HttpServletResponse response){
		System.out.println("进入了移除该购物项servlet");
		//获取参数
		String bid = request.getParameter("bid");
		//获取购物车
		Cart cart = getCart(request);
		//调用购物车Cart中的removeCart(移除该购物项)方法
		cart.removeCart(bid);
		return "/jsps/cart/list.jsp";
	}
}
