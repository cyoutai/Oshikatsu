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

	<div class = "formTitle">
	<h2>イベント登録</h2>
	</div>

	<div class = "formList">

	<c:if test="${msgEventErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgEventErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/event_register" method="post">

		作品名：<br>
		<select name="titleId">

			<c:forEach var="title" items="${titleListAll }">

				<c:choose>

					<c:when test="${title.titleId == eventInfo.titleId }">
						<option value="${title.titleId }" selected>${title.titleName }</option>
					</c:when>
					<c:otherwise>
						<option value="${title.titleId }">${title.titleName }</option>
					</c:otherwise>

				</c:choose>


			</c:forEach>

		</select>

		<br>

		イベント名：<br>
		<input type="text" name="eventName" required><br>

		期間：<br>
		<input type="date" name="eventStart" required/>～
		<input type="date" name="eventEnd"/><br>

		場所：<br>
		<input type="text" name="eventLoc"><br>

		イベントサイト：<br>
		<input type="url" name="eventUrl"><br>

		通販サイト：<br>
		<input type="url" name="eventShop"><br>

		<br>
		<input type="submit" value="登録">

	</form>

	<!--  -->
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