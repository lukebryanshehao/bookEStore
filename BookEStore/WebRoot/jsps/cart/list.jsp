<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>购物车列表</title>
    
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
		margin:20px;
		border: solid 2px gray;
		width: 150px;
		height: 150px;
		text-align: center;
	}
	li {
		margin: 10px;
	}
	
	#buy {
		background: url(<c:url value='/images/all.png'/>) no-repeat;
		display: inline-block;
		
		background-position: 0 -902px;
		margin-left: 30px;
		height: 36px;
		width: 146px;
	}
	#buy:HOVER {
		background: url(<c:url value='/images/all.png'/>) no-repeat;
		display: inline-block;
		
		background-position: 0 -938px;
		margin-left: 30px;
		height: 36px;
		width: 146px;
	}
</style>
  </head>
  
  <body>
<h1>购物车</h1>
<c:if test="${fn:length(cart.cartItems) ==0 }">
	<h1 align="center" style="color: navy;">
	您的购物车空空如也!请去<a href="${pageContext.request.contextPath }/BookServlet?method=showAll">购物</a>
	</h1>
	<img src="${pageContext.request.contextPath }/images/cart.png">
</c:if>

<c:if test="${fn:length(cart.cartItems) !=0 }">
<table border="1" width="100%" cellspacing="0" background="black">
	<tr>
		<td colspan="7" align="right" style="font-size: 15pt; font-weight: 900">
			<a href="${pageContext.request.contextPath }/CartServlet?method=clearCart">清空购物车</a>
		</td>
	</tr>
	<tr>
		<th>图片</th>
		<th>书名</th>
		<th>作者</th>
		<th>单价</th>
		<th>数量</th>
		<th>小计</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${cart.cartItems }" var="cc">
	<tr>
		<td><div><img src="${pageContext.request.contextPath }/${cc.book.image}" width="130" height="140"/></div></td>
		<td>${cc.book.bname }</td>
		<td>${cc.book.author }</td>
		<td>${cc.book.price }</td>
		<td>${cc.count }</td>
		<td>${cc.subtotal }</td>
		<td><a href="${pageContext.request.contextPath }/CartServlet?method=removeCart&bid=${cc.book.bid}">删除</a></td>
	</tr>
	</c:forEach>

	<tr>
		<td colspan="7" align="right" style="font-size: 15pt; font-weight: 900">
			合计：${cart.total }元
		</td>
	</tr>
	<tr>
		<td colspan="7" align="right" style="font-size: 15pt; font-weight: 900">
			<a id="buy" href="${pageContext.request.contextPath }/OrderServlet?method=saveOrder"></a>
		</td>
	</tr>
</table>
</c:if>
  </body>
</html>
