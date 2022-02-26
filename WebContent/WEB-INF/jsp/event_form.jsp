<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "formList">

	<c:if test="${msgEventErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgEventErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/event_update" method="post">

		<input type="hidden" name="eventId" value="${eventInfo.eventId}">
		<input type="hidden" name="userId" value="${eventInfo.userId}">

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
		<input type="text" name="eventName" value="${eventInfo.eventName}" required><br>

		期間：<br>
		<input type="date" name="eventStart" value="${eventInfo.eventStart }" required/>～
		<input type="date" name="eventEnd" value="${eventInfo.eventEnd }" /><br>

		場所：<br>
		<input type="text" name="eventLoc" value="${eventInfo.eventLoc }"><br>

		イベントサイト：<br>
		<input type="url" name="eventUrl" value="${eventInfo.eventUrl }"><br>

		通販サイト：<br>
		<input type="url" name="eventShop" value="${eventInfo.eventShop }"><br>

		<br>
		<input type="submit" value="更新">

	</form>

	<br>
	<form action="/Oshikatsu/event_delete" method="POST">
		<input type="hidden" name="eventId" value="${eventInfo.eventId}"/>
		<input type="submit" value="削除" onclick="return confirm('本当に削除しますか？')">
	</form>

	<!-- formList -->
	</div>

	<br>

	<!-- イベント一覧へリンク -->
	<div class="menuList">
	<a href="<c:url value="/event_list">
		<c:param name="titleId" value="${onTitleId }" />
		<c:param name="sYYYYMMDD" value="${sYYYYMMDD }" />
		<c:param name="eYYYYMMDD" value="${eYYYYMMDD }" />
		</c:url>">イベント一覧</a><br>
	</div>

	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

</body>
</html>