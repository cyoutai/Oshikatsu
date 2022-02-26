<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "formTitle">
	<h2>2次元用推し活手帳電子版（仮）</h2>
	</div>

	<div class = "formList">

	<c:if test="${msg != null }">
		<div class = "msgErr">
			<p><c:out value="${msg}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/login" method="post">

		ユーザーID<br>
		<input type="text" name="userId" required><br>
		パスワード<br>
		<input type="password" name="pass" required><br>

		<br>
		<input type="submit" value="ログイン">

	</form>

	</div>

	<br>
	<div class="menuList">
		<a href="user_register.jsp">ユーザー登録</a>
	</div>

	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

	<% session.invalidate(); %>
</body>
</html>