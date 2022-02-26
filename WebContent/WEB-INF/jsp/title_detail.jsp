<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/title_detale_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>
</head>
<body>

	<div class="waku">

	<div class = "titleName">
		<h2>${titleInfo.titleName }</h2>
	</div>

	<div class = "titleDetale">

	<div class="titleInfoTitle">
		<h3>公式サイト：</h3>
	</div>

		<c:forEach var="site" items="${titleInfo.site }">

			<div class="urlTwitterId">
				<a href="${site.siteUrl }" target="_blank">${site.siteUrl }</a><br>
			</div>

		</c:forEach>

	<br>

	<div class="titleInfoTitle">
		<h3>公式Twitter：</h3>
	</div>

		<c:forEach var="twitter" items="${titleInfo.twitter }">

			<div class="urlTwitterId">
				<a href="https://twitter.com/${twitter.twitterId }" target="_blank">${twitter.twitterId }</a><br>
			</div>

		</c:forEach>

	<br>

	<!-- 記念日一覧へリンク -->
	<div class="selectList">
	<a href="<c:url value="/anniv_list">
		<c:param name="titleId" value="${titleInfo.titleId }" />
		</c:url>"><!-- ${titleInfo.titleName }の -->記念日・誕生日一覧</a><br>
	</div>

	<!-- イベント一覧へリンク -->
	<div class="selectList">
	<a href="<c:url value="/event_list">
		<c:param name="titleId" value="${titleInfo.titleId }" />
		<c:param name="sYYYYMMDD" value="なし" />
		<c:param name="eYYYYMMDD" value="なし" />
		</c:url>"><!-- ${titleInfo.titleName }の -->イベント一覧</a><br>
	</div>

	<!-- titleDetale -->
	</div>

	<br>
	<br>


	<div class="menuList">
		<a href="/Oshikatsu/title_list">作品一覧</a>
	</div>
	<div class="menuList">
		<a href="/Oshikatsu/home">ホーム</a>
	</div>

	<div class='toFormTitle'>
		<a href="/Oshikatsu/title_update">修正</a>
	</div>


	<!-- wakuのdiv -->
	</div>

	<br>
	<br>

</body>
</html>