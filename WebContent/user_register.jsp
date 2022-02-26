<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版</title>
</head>
<body>

	<div class="waku">

	<div class = "formTitle">
		<h2>ユーザー登録</h2>
	</div>

	<div class = "formList">

	<c:if test="${msgUserErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgUserErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/user_register" method="post">

		ユーザーID<br>
		<small>※半角英数字6文字以上16文字以下</small><br>
		<input type="text" name="userIdR" pattern="^([a-zA-Z0-9]{6,16})$" required><br>
		パスワード<br>
		<small>※6文字以上10文字以下で1文字以上の数字、小文字の大文字アルファベットがそれぞれ含まれていること</small><br>
		<input type="password" name="passR" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}" required><br>

		<br>
		<input type="submit" value="登録">

	</form>

	<!-- formList -->
	</div>

	<br>

	<div class="menuList">
		<a href="index.jsp">トップページへ</a>
	</div>

	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

</body>
</html>