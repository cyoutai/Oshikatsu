<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<!-- イベント終了日の有無判定に使うString型の今日日付の値をセット -->
<% 	LocalDate nowLD = LocalDate.now();
	String stringNow = nowLD.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); %>
<c:set var="stringNow" value = "<%=stringNow %>" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/event_list_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "eventTitle">
		<h2>イベント一覧</h2>
	</div>

	<div class = "eventList">
	<form action="/Oshikatsu/event_list" method="post">

		<select name="titleId">

			<c:choose>

				<c:when test="${onTitleId == 0 }">
					<option value="0" selected>全作品を表示</option>
				</c:when>
				<c:otherwise>
					<option value="0" selected>全作品を表示</option>
				</c:otherwise>

			</c:choose>


			<c:forEach var="title" items="${titleOnList }">

				<c:choose>

					<c:when test="${onTitleId == title.titleId }">
						<option value="${title.titleId }" selected>${title.titleName }</option>
					</c:when>
					<c:otherwise>
						<option value="${title.titleId }">${title.titleName }</option>
					</c:otherwise>

				</c:choose>


			</c:forEach>

		</select>

		<br>

		<input type="date" name="sYYYYMMDD" value="${sYYYYMMDD }" />～
		<input type="date" name="eYYYYMMDD" value="${eYYYYMMDD }" />

		<input type="submit" value="選択">

	</form>

	<c:if test="${empty eventDispList }">
		イベントは、まだ登録されていません。<br>
	</c:if>

	<br>

	<!-- イベントを表示 -->
	<c:forEach var="event" items="${eventDispList }">

		<div class="event">

		<!-- イベント期間 -->
		<!-- 開始日と、終了日がある場合は終了日を表示 -->
		<fmt:formatDate value="${event.eventStartDateType }" pattern="M月d日(E)"/>
		<c:if test="${event.eventEnd != event.eventStart }">
			～<fmt:formatDate value="${event.eventEndDateType }" pattern="M月d日(E)"/>
		</c:if>

		<!-- イベント名 -->
		<br><b>${event.eventName }</b><br>

		<!-- 場所があれば表示 -->
		<c:if test="${event.eventLoc != null }">
			<div class="eventLoc">
			場所：${event.eventLoc }<br>
			</div>
		</c:if>

		<!-- イベントサイトがあれば表示 -->

		<c:if test="${event.eventUrl != null }">
			<div class='eventUrl'>
			<a href="${event.eventUrl }" target=”_blank” >イベントサイト</a>
			</div>
		</c:if>


		<!-- 通販サイトがあれば表示 -->

		<c:if test="${event.eventShop != null }">
			<div class='eventShop'>
			<a href="${event.eventShop }" target=”_blank” >通販サイト</a>
			</div>
		</c:if>


		<!-- 修正 -->
		<div class='toForm'>
		<a href="<c:url value="/event_form">
		<c:param name="eventId" value="${event.eventId }" />
		<c:param name="onTitleId" value="${onTitleId }" />
		<c:param name="sYYYYMMDD" value="${sYYYYMMDD }" />
		<c:param name="eYYYYMMDD" value="${eYYYYMMDD }" />
		</c:url>">修正</a>
		</div>

		<!-- event -->
		</div>

		<br>

	</c:forEach>

	<!-- eventList -->
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