package com.itheima.estore.utils;

import java.util.UUID;


/**
 * 生成唯一字符串工具类
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 19:40:55
 */
public class UUIDUtils {

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
}
