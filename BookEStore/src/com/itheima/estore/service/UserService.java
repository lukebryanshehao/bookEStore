package com.itheima.estore.service;

import com.itheima.estore.dao.UserDao;
import com.itheima.estore.domain.User;
import com.itheima.estore.utils.MailUtils;
import com.itheima.estore.utils.UUIDUtils;

/**
 * 用户管理的service层
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 19:38:42
 */
public class UserService {

	/**
	 * 注册用户
	 * 
	 * @param user 传入页面封装的user
	 */
	public void regist(User user) {
		
		UserDao dao = new UserDao();
		//补全信息 uid 激活状态state 激活码code
		String uid = UUIDUtils.getUUID();
		user.setUid(uid);
		//设置激活状态:1激活,0未激活
		user.setState(0);
		//设置激活码
		String code = uid+UUIDUtils.getUUID();
		user.setCode(code);
		dao.regist(user);
		
		//发送一封激活邮件
		MailUtils.sendMail(user.getEmail(), code);
	}

	/**
	 * 校验用户名是否存在
	 * 
	 * @param username 输入框中的值
	 * @return user对象
	 */
	public User checkName(String username) {
		UserDao dao = new UserDao();
		return dao.checkName(username);
	}

	/**
	 * 用户激活
	 * 
	 * @param code 邮件链接中传入的激活码
	 * @return user 该激活码对应的用户
	 */
	public User findByCode(String code) {
		UserDao dao = new UserDao();
		return dao.findByCode(code);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param user 要修改的用户
	 */
	public void update(User user) {
		UserDao dao = new UserDao();
		dao.update(user);
		
	}

	/**
	 * 登录
	 * 
	 * @param user 页面输入的内容对象
	 * @return 查询的user对象
	 */
	public User login(User user) {
		UserDao dao = new UserDao();
		return dao.login(user);
	}
	
}
