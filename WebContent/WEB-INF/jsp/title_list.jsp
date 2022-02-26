<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/Oshikatsu/CSS/title_list_css.css">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "titleTitle">
		<h2>登録作品リスト</h2>
	</div>

	<div class = "titleList">

	<!-- 登録作品を表示 -->
	<!-- 表示非表示設定がON（1）なら、チェックボックスをONして表示 -->
	<form action="/Oshikatsu/title_on_off" method="post">
		<c:forEach var="title" items="${titleListAll }">

			<div class="yoko">
			<c:choose>

				<c:when test="${title.tOnOff == 1 }">
					<input type="checkbox" name="TitleOnOff" value="${title.titleId }" checked>
				</c:when>

				<c:otherwise>
					<input type="checkbox" name="TitleOnOff" value="${title.titleId }">
				</c:otherwise>

			</c:choose>

				${title.titleName }

				<div class='toDtl'>
				<a href="<c:url value="/title_detail">
						<c:param name="titleId" value="${title.titleId}" />
						</c:url>">詳細</a><br>
				</div>

			</div>

		</c:forEach>

		<br>
		<input type="submit" value="表示する作品を更新">

	</form>

	<!-- titleList -->
	</div>



	<br>
	<div class="menuHome">
		<a href="/Oshikatsu/home">ホーム</a>
	</div>


	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

</body>
</html>