<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.entity.AnniversaryBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- イベント終了日の有無判定に使うString型の今日日付の値をセット -->
<% 	LocalDate nowLD = LocalDate.now();
	String stringNow = nowLD.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); %>
<c:set var="stringNow" value = "<%=stringNow %>" />

<!-- 周年計算用の現在の年（int型）を取得 -->
<% int nowY = LocalDate.now().getYear(); %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/home_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版</title>


</head>
<body>


<div class="waku">

<!-- 現在の年月日を表示 -->
<div class = "today">
<h2><fmt:formatDate value="<%= new Date() %>" pattern="yyyy年M月d日(E)"/></h2>
</div>

	<div class = "todayList">
	<!-- 今日の記念日を表示 -->
	<c:forEach var="annivToday" items="${annivTodayList }">

		<div class="anniv">
		<!-- 記念日名、推し名を表示 -->
		<div class="annivName">
		<b><font color="${annivToday.annivClr }">${annivToday.annivName }</font></b><br>
		</div>

		<div class="annivClass">
		<!-- 周年カウントの表示ありなら、周年を計算して表示 -->
		<c:if test="${annivToday.annivCnt != null }">
			<div class="annivClass">
			<c:set var="nowY" value = "<%=nowY %>" />
			${nowY - annivToday.annivYear}${annivToday.annivCnt }&nbsp;
			</div>
		</c:if>

		<!-- 区分があれば表示 -->
		<c:if test="${annivToday.annivClass != null }">
			<div class="annivClass">
			${annivToday.annivClass }<br>
			</div>
		</c:if>

		</div>

		</div>

	</c:forEach>

	<br>

	<!-- 今日のイベントを表示 -->
	<c:forEach var="eventToday" items="${eventTodayList }">

		<div class="event">
		<!-- イベント名を表示 -->
		<b>${eventToday.eventName }</b><br>

		<!-- 終了日があれば、終了日とカウントダウンを表示 -->
		<div class="eventEnd">
		<c:if test="${eventToday.eventEnd != stringNow }">
			終了：<fmt:formatDate value="${eventToday.eventEndDateType }" pattern="M月d日(E)"/>
			あと${eventToday.eventEndCnt }日<br>
		</c:if>
		</div>

		<!-- イベントサイトがあれば表示 -->
		<c:if test="${eventToday.eventUrl != null }">
			<div class='eventUrl'>
			<a href="${eventToday.eventUrl }" target=”_blank” >イベントサイト</a>
			</div>
		</c:if>

		<!-- 通販サイトがあれば表示 -->
		<c:if test="${eventToday.eventShop != null }">
			<div class='eventShop'>
			<a href="${eventToday.eventShop }" target=”_blank” >通販サイト</a>
			</div>
		</c:if>

		<br>

		</div>

	</c:forEach>
	</div>

	<br>

	<div class="schedule">
	<h3>これからの予定</h3>
	</div>

	<div class="scheduleList">

	<!-- 今後の記念日を表示 -->



		<!-- 同一記念日はまとめる -->
		<%
			List<AnniversaryBean> annivList = (List<AnniversaryBean>) session.getAttribute("annivList");

			for(int i = 0; i < annivList.size() ; i++){
		%>
				<div class="anniv">

		<%
				if(i != 0){

					// リストの1個前と記念日の日が同じではなかったら
					if(!(annivList.get(i - 1).getAnnivDateType().isEqual(annivList.get(i).getAnnivDateType()))){
		%>
						<!-- 記念日の月日とカウントダウンを表示 -->
						<div class="scheduleDate">
						<fmt:formatDate value="<%= annivList.get(i).getAnnivDateTypeDisplay() %>" pattern="M月d日(E)"/>
						あと<%= annivList.get(i).getAnnivDateCnt() %>日<br>
						</div>

		<%
					}
				} else {
		%>

					<!-- 記念日の月日とカウントダウンを表示 -->
					<div class="scheduleDateAnniv">
					<fmt:formatDate value="<%= annivList.get(i).getAnnivDateTypeDisplay() %>" pattern="M月d日(E)"/>
					あと<%= annivList.get(i).getAnnivDateCnt() %>日<br>
					</div>

		<%
				}

				// 周年カウントの表示ありなら、周年を計算して表示
				if(annivList.get(i).getAnnivCnt() != null){

					// 周年の計算
					int annivCnt = nowY - annivList.get(i).getAnnivYear();

		%>
					<!-- ここJSPの世界（表示させるよ） -->
					<div class="annivClass">
					<%= annivCnt %><%= annivList.get(i).getAnnivCnt() %>&nbsp;
					</div>

		<%
				}
				// 区分があれば表示
				if(annivList.get(i).getAnnivClass() != null){
		%>
					<!-- ここJSPの世界（表示させるよ） -->
					<div class="annivClass">
					<%= annivList.get(i).getAnnivClass() %><br>
					</div>
		<%
				}
		%>
				<!-- 記念日名、推し名を表示 -->
				<div class="annivName">
				<b><font color="<%= annivList.get(i).getAnnivClr() %>"><%= annivList.get(i).getAnnivName() %></font></b><br>
				</div>

				</div>
		<%
			}
		 %>


	<br>

	<!-- 今後のイベントを表示 -->
	<c:forEach var="event" items="${eventList }">


		<div class="event">

		<!-- 開始日と、終了日がある場合は終了日を表示 -->
		<div class="scheduleDateEvent">
		<fmt:formatDate value="${event.eventStartDateType }" pattern="M月d日(E)"/>
		<c:if test="${event.eventEnd != event.eventStart }">
			～<fmt:formatDate value="${event.eventEndDateType }" pattern="M月d日(E)"/>
		</c:if>
		</div>

		<!-- イベント名を表示 -->
		<br><b>${event.eventName }</b><br>

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


		<!-- ここに全角スペースを置かないと、CSSが正常に動かない -->
		　</div>

	</c:forEach>

	</div>
<br>

	<div class="menuBtnRegist">
	<a href="/Oshikatsu/title_register">作品登録</a>
	</div>
	<div class="menuBtnList">
	<a href="/Oshikatsu/title_list">作品一覧</a><br>
	</div>

	<div class="clearfix">
		<div class="menuBtnRegist">
			<a href="/Oshikatsu/anniv_register">記念日・誕生日登録</a>
		</div>
		<!-- 記念日一覧へリンク -->
		<div class="menuBtnList">
			<a href="<c:url value="/anniv_list">
			<c:param name="titleId" value="0" />
			</c:url>">記念日・誕生日一覧</a><br>
		</div>
	</div>

	<div class="clearfix">
		<div class="menuBtnRegist">
			<a href="/Oshikatsu/event_register">イベント登録</a>
		</div>
	<!-- イベント一覧へリンク -->
		<div class="menuBtnList">
			<a href="<c:url value="/event_list">
				<c:param name="titleId" value="0" />
				<c:param name="sYYYYMMDD" value="なし" />
				<c:param name="eYYYYMMDD" value="なし" />
			</c:url>">イベント一覧</a><br>
		</div>
	</div>

	<br>
	<div class="clearfix">
		<div class="menuBtnRegist">
			<a href="/Oshikatsu/config_update">設定</a>
		</div>
		<div class="menuBtnList">
			<a href="/Oshikatsu/logout">ログアウト</a>
		</div>
	</div>

</div>

	<br>
	<br>

</body>
</html>