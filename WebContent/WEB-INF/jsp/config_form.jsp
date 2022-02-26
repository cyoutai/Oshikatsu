<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "formList">

	<c:out value="${msgUserErr}" />

	<form action="/Oshikatsu/config_update" method="post">

		<input type="hidden" name="userId" value="${user.userId}">
		ユーザーID：${user.userId}<br>

		パスワード：<br>
		<small>※6文字以上10文字以下で1文字以上の数字、小文字の大文字アルファベットがそれぞれ含まれていること</small><br>
		<input type="password" name="pass" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,10}" value="${user.pass}" required><br>

		カウントダウン開始：<br>
		<input type="number" name="countDwn" value="${user.countDwn}" required>日前からカウントダウン<br>

		イベント表示デフォルト日数：<br>
		<input type="number" name="eventGet" value="${user.eventGet}" required>日分を表示<br>

		<br>
		<input type="submit" value="更新">

	</form>

	<br>
	<form action="/Oshikatsu/user_delete" method="POST">
		<input type="hidden" name="userId" value="${user.userId}"/>
		<input type="submit" value="ユーザー削除" onclick="return confirm('本当に削除しますか？')">
	</form>

	<!-- formList -->
	</div>

	<br>
	<div class="menuList">
		<a href="/Oshikatsu/home">ホーム</a>
	</div>

	<!-- waku -->
	</div>

	<br>
	<br>

</body>
</html>