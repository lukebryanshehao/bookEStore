<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	* {
		font-size: 11pt;
	}
	div {
		border: solid 2px rgb(78,78,78);
		width: 75px;
		height: 75px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
</style>
<script type="text/javascript">
	function showDetial(oid) {
		//获得表格
		var tab = document.getElementById("tab"+oid);
		// 获得按钮:
		var but = document.getElementById("but"+oid);
		if(but.value == "订单详情"){
			//创建异步对象
			var xhr = createXMLHttpRequest();
			//设置监听
			xhr.onreadystatechange = function showDetial(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						var data = xhr.responseText;
						var json = eval("("+data+")");
						//alert(json);
						for (var i = 0; i < json.length; i++) {
							tab.innerHTML += "<tr bordercolor='rgb(78,78,78)' align='center'><td width='15%'></td><td>书名：</td><td>单价：</td><td>作者：</td><td>数量：</td><td>小计：</td></tr>"+"<tr><td><img src='${pageContext.request.contextPath}/"
							+json[i].book.image+"' width='65' height='70'></td><td>"
							+json[i].book.bname+"</td><td>"+json[i].book.price+"</td><td>"
							+json[i].book.author+"</td><td>"+json[i].count+"</td><td>"
							+json[i].subtotal+"</td></tr>";
						}
					}
				}
			}
			//打开链接
			xhr.open("GET", "${pageContext.request.contextPath}/AdminOrderServlet?method=showDetial&oid="+oid, true);
			//发送数据
			xhr.send(null);
			but.value = "收起";
		}else{
			tab.innerHTML = "";
			but.value = "订单详情";
		}
	};
	
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
  
  <body style="background: rgb(254,238,189);">
<h1>我的订单</h1>

<table border="1" width="100%" cellspacing="0" background="black">
	<c:forEach items="${list }" var="order">
	<tr bgcolor="rgb(78,78,78)" bordercolor="rgb(78,78,78)" style="color: white;">
		<td colspan="6">
			订单：${order.oid }　成交时间：${order.ordertime }　金额：<font color="red"><b>${order.total }</b></font>
			<c:if test="${order.state == 1 }">未付款</c:if>
			<c:if test="${order.state == 2 }"><a href="${pageContext.request.contextPath }/AdminOrderServlet?method=updateState&oid=${order.oid}">发货</a></c:if>
			<c:if test="${order.state == 3 }">未收货</c:if>
			<c:if test="${order.state == 4 }">订单结束</c:if>&nbsp;&nbsp;
			<input type="button" value="订单详情" id="but${ order.oid }" onclick="showDetial('${order.oid}')" />
		</td>
	</tr>
	<tr>
		<td colspan="6">
			<table id="tab${order.oid }" width="100%" border="1"></table>
		</td>
	</tr>
 	</c:forEach>
</table>
  </body>
</html>
