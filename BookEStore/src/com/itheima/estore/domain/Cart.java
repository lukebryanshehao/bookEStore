package com.itheima.estore.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车实体类
 * 
 * @author shehao1
 * @version 1.0 2016-11-5 00:57:52
 */
public class Cart {

	/**封装购物项集合*/
	private Map<String, CartItem> map = new LinkedHashMap<>();
	
	/**总计*/
	private double total;

	/*public Map<String, CartItem> getMap() {
		return map;
	}*/
	
	/**
	 * 提供返回所有map的value的单列集合
	 * 
	 * @return
	 */
	public Collection<CartItem> getCartItems(){
		return map.values();
	}

	public double getTotal() {
		return total;
	}

	/**
	 * 添加到购物车
	 */
	public void addCart(CartItem cartItem){
		//如果购物车中含有该购物项,则数量加,否则置为改数量
		//然后总计发送变化
		String bid = cartItem.getBook().getBid();
		if(map.containsKey(bid)){
			CartItem cartItem1 = map.get(bid);
			cartItem1.setCount(cartItem1.getCount()+cartItem.getCount());
		}else{
			//不包含
			map.put(bid, cartItem);
		}
		total += cartItem.getSubtotal();
	}
	
	/**
	 * 移除该购物项
	 * 
	 * @param bid bookid
	 */
	public void removeCart(String bid){
		CartItem cartItem = map.get(bid);
		//将该购物项移除,并将总计减去该购物项小计
		map.remove(bid);
		//将小计减去
		total -= cartItem.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 */
	public void cleanCart(){
		//清空,即将map置为0,总计置为0
		map.clear();
		total = 0;
		
	}

}
