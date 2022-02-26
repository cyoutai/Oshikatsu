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

	<c:if test="${msgAnnivErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgAnnivErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/anniv_update" method="post">

		<input type="hidden" name="annivId" value="${annivInfo.annivId}">
		<input type="hidden" name="userId" value="${annivInfo.userId}">

		作品名：<br>
		<select name="titleId">

			<c:forEach var="title" items="${titleListAll }">

				<c:choose>

					<c:when test="${title.titleId == annivInfo.titleId }">
						<option value="${title.titleId }" selected>${title.titleName }</option>
					</c:when>
					<c:otherwise>
						<option value="${title.titleId }">${title.titleName }</option>
					</c:otherwise>

				</c:choose>


			</c:forEach>

		</select>

		<br>

		推しの名前（又は名称）：<br>
		<input type="text" name="annivName" value="${annivInfo.annivName}" required><br>

		記念日・誕生日：<br>
		<small>※年は半角数字で入力してください。</small><br>
		<c:choose>
			<c:when test="${annivInfo.annivYear == 0}">
				<input type="text" pattern="[1-9][0-9]*" name="annivYear">
			</c:when>
			<c:otherwise>
				<input type="text" pattern="[1-9][0-9]*" name="annivYear" value="${annivInfo.annivYear}">
			</c:otherwise>
		</c:choose>
		年
		<input type="number" max="12" min="1" name="annivMonth" value="${annivInfo.annivMonth}" required>月
		<input type="number" max="31" min="1" name="annivDay" value="${annivInfo.annivDay}" required>日<br>

		カウント：<br>
		<input type="text" name="annivCnt" value="${annivInfo.annivCnt}"><br>

		区分：<br>
		<input type="text" name="annivClass" value="${annivInfo.annivClass}"><br>

		カラー：<br>
		<input type="color" name="annivClr" value="${annivInfo.annivClr}"><br>

		<br>
		<input type="submit" value="更新">

	</form>

	<br>
	<form action="/Oshikatsu/anniv_delete" method="POST">
		<input type="hidden" name="annivId" value="${annivInfo.annivId}"/>
		<input type="submit" value="削除" onclick="return confirm('本当に削除しますか？')">
	</form>

	<!-- formList -->
	</div>

	<br>

	<!-- 記念日一覧へリンク -->
	<div class="menuList">
		<a href="<c:url value="/anniv_list">
			<c:param name="titleId" value="${onTitleId }" />
			</c:url>">記念日・誕生日一覧</a><br>
	</div>

	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

</body>
</html>