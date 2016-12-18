package com.itheima.estore.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮箱发送工具类
 * 
 * @author shehao1
 * @version 1.0 2016-11-2 20:50:57
 */
public class MailUtils {

	/**
	 * 发送邮件的方法
	 * 
	 * @param to 收件人
	 * @param code 激活码
	 */
	public static void sendMail(String to,String code){
		//邮件发送三步骤
		//1.创建一个session对象:连接对象
		//Properties可以设置发送邮箱的服务器地址,这里因为是本地,所以不用设置
		Properties props = new Properties();
		Session session = Session.getInstance(props, new Authenticator(){

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@estore.com", "111");
			}
			
		});
		
		//2.创建一个Message对象:邮件对象
		Message message = new MimeMessage(session);
		try {
			//设置发件人
			message.setFrom(new InternetAddress("service@estore.com"));
			//设置收件人
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			//设置邮件主题
			message.setSubject("EStore官方激活邮件");
			//设置邮件内容
			message.setContent(
			"<h1>EStore官方激活邮件:激活请点击:</h1><h3><a href='http://localhost:8080/BookEStore/UserServlet?method=active&code="+code+"'>http://localhost:8080/BookEStore/UserServlet?method=active&code="+code+"</a></h3>",
					"text/html;charset=utf-8");
			//3.使用Transport对象发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
	}
}
