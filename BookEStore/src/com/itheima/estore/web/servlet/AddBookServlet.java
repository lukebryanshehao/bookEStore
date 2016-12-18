package com.itheima.estore.web.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.estore.domain.Book;
import com.itheima.estore.service.BookService;
import com.itheima.estore.utils.UUIDUtils;

/**
 * 添加图书的servlet层
 * 
 * @author shehao1
 * @version 1.0 2016-11-7 17:39:14
 */
public class AddBookServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("添加图书");
		//创建磁盘文件项工厂
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//创建核心解析类
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		//创建一个map集合用于存储页面数据
		Map<String, String> map = new HashMap<>();
		//封装一个book对象
		Book book = new Book();
		
		try {
			//解析request
			List<FileItem> list = servletFileUpload.parseRequest(request);
			
			String filename = null;
			
			//遍历list集合
			for (FileItem fileItem : list) {
				//判断
				if(fileItem.isFormField()){
					//普通项(每次获取一个)
					//获得普通项名称
					String fieldName = fileItem.getFieldName();
					//获得普通项值
					String value = fileItem.getString("utf-8");
					//存储到map
					map.put(fieldName, value);
				}else{
					//文件项
					//获得文件名
					filename = fileItem.getName();
					//获得文件输入流
					InputStream is = fileItem.getInputStream();
					//写到文件中
					String path = this.getServletContext().getRealPath("/book_img");
					//创建输出流
					OutputStream os = new FileOutputStream(path+"\\"+filename);
					//两个流对接
					int len = 0;
					byte[] b = new byte[1024*8];
					while((len = is.read(b)) != -1){
						os.write(b, 0, len);
					}
					is.close();
					os.close();
				}
			}
			//封装book对象
			BeanUtils.populate(book, map);
			book.setBid(UUIDUtils.getUUID());
			book.setIsdel(0);
			book.setImage("book_img/"+filename);
			//调用业务层保存图书
			BookService service = new BookService();
			service.save(book);
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//页面跳转
		request.getRequestDispatcher("/AdminBookServlet?method=findAll").forward(request, response);;
	}

}
