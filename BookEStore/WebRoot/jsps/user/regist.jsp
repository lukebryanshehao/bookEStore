<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	
	<script type="text/javascript">
		function checkName() {
			//获取div
			var div1 = document.getElementById("div1");
			//获取文本框username中的值
			var username = document.getElementById("username").value;
			//alert(username)
			//获取按钮
			var but1 = document.getElementById("but1");
			//创建异步XMLHttpRequest对象
			var xhr = createXMLHttpRequest();
			//设置监听
			xhr.onreadystatechange = function (){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						var data = xhr.responseText;
						if(data == 0){
							//没找到该用户,可以使用该用户名
							div1.innerHTML = "<font color='green'>可以使用该用户名</font>";
							but1.style.display="block";
						}else if(data ==1){
							//找到了该用户,可以使用该用户名
							div1.innerHTML = "<font color='red'>该用户名已被占用</font>";
							but1.style.display="none";
						}
						
					}
				}
			}
			//打开链接
			xhr.open("GET", "${pageContext.request.contextPath}/UserServlet?method=checkName&username="+username, true);
			//发送数据
			xhr.send(null);
		}
		
		function createXMLHttpRequest(){
	 		   var xmlHttp;
	 		   try{ // Firefox, Opera 8.0+, Safari
	 		        xmlHttp=new XMLHttpRequest();
	 		    } catch (e){
	 			   try{// Internet Explorer
	 			         xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
	 			      }
	 			    catch (e){
	 			      try{
	 			         xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
	 			      }
	 			      catch (e){}
	 			      }
	 		    }
	 			return xmlHttp;
	 		 }
	</script>

  </head>
  
  <body>
  <h1>注册</h1>
<p style="color: red; font-weight: 900">${msg }</p>
<form action="${pageContext.request.contextPath}/UserServlet" method="post">
	<input type="hidden" name ="method" value="regist" />
	用户名：<input type="text" id="username" name="username" onblur="checkName()" value=""/><div id="div1"></div><br/>
	密　码：<input type="password" name="password"/><br/>
	邮　箱：<input type="text" name="email" value=""/><br/>
	<input type="submit" id="but1" value="注册"/>
</form>
  </body>
</html>
