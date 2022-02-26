<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.AnniversaryBean" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/anniv_list_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "annivTitle">
		<h2>記念日・誕生日一覧</h2>
	</div>

	<div class = "annivList">
	<form action="/Oshikatsu/anniv_list" method="post">

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

		<input type="submit" value="選択">

	</form>

	<c:if test="${empty annivDispList }">
		記念日・誕生日は、まだ登録されていません。<br>
	</c:if>

	<%
		// リクエストスコープから値の取り出し
		List<AnniversaryBean> annivList = (List<AnniversaryBean>) request.getAttribute("annivDispList");

		// 1月～12月までカウント
		for(int i = 1; i <= 12; i++){
	%>

			<h3><%= i %>月</h3>

	<%
			for(AnniversaryBean anniv: annivList){

				// 月カウントと記念日の月が同じなら、表示
				if(i == anniv.getAnnivMonth()){
	%>
					<div class="yoko">
					<!-- ここJSPの世界 -->
					<!-- 記念日の月日を表示 -->
					<%= anniv.getAnnivMonth() %>月<%= anniv.getAnnivDay() %>日

					<!-- 区分があれば表示 -->
					<c:set var="annivClass" value="<%= anniv.getAnnivClass() %>"/>
					<c:if test="${annivClass != null }">
						${annivClass }
					</c:if>

					<!-- 記念日名、推し名を表示 -->
					<b>　<font color="<%= anniv.getAnnivClr() %>"><%= anniv.getAnnivName() %></font></b>

					<!-- 記念日の修正フォームへリンク -->
					<div class='toForm'>
					<a href="<c:url value="/anniv_form">
						<c:param name="annivId" value="<%= String.valueOf(anniv.getAnnivId()) %>" />
						<c:param name="onTitleId" value="${onTitleId }" />
						</c:url>">修正</a><br>
					</div>

					</div>
	<%
				}
			}
	%>
			<br>

	<%
		}
	%>

	<!-- annivList -->
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