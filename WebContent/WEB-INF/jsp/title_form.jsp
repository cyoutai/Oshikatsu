<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.entity.TitleBean" %>
<%@ page import="model.entity.TitleSite" %>
<%@ page import="model.entity.TitleTwitter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>

	<script>

	var i = 1 ;

	function addForm(branch, form_area, idAdd, type) {

  		var input_data = document.createElement('input');
  		input_data.type = type;
  		input_data.id = branch + idAdd + i;
  		input_data.name = branch + i;
  		var parent = document.getElementById(form_area);
  		parent.appendChild(input_data);

  		var button_data = document.createElement('button');
 		button_data.id =idAdd + i;
  		button_data.onclick = function(){deleteBtn(this, form_area, branch);}
  		button_data.innerHTML = '削除';
  		var input_area = document.getElementById(input_data.id);
  		parent.appendChild(button_data);

  		var br = document.createElement("br");
  		parent.appendChild(br)

  		i++ ;

	}

	function deleteBtn(target, form_area, branch) {

		var target_id = target.id;
		var parent = document.getElementById(form_area);
		var ipt_id = document.getElementById(branch + target_id);
		var tgt_id = document.getElementById(target_id);
		parent.removeChild(ipt_id);
		parent.removeChild(tgt_id);

	}

	function deleteBtnEx(target, form_area, branch1, branch2) {

		var target_id = target.id;
		var parent = document.getElementById(form_area);
		var ipt_id1 = document.getElementById(branch1 + target_id);
		var ipt_id2 = document.getElementById(branch2 + target_id);
		var tgt_id = document.getElementById(target_id);
		parent.removeChild(ipt_id1);
		parent.removeChild(ipt_id2);
		parent.removeChild(tgt_id);

	}

	</script>

</head>
<body>

	<div class="waku">

	<div class = "formList">

	<c:if test="${msgTtileErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgTtileErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/title_update" method="post">

		<input type="hidden" name="titleId" value="${titleInfo.titleId}">
		<input type="hidden" name="userId" value="${titleInfo.userId}">
		作品名：<br>
		<input type="text" name="titleName" value="${titleInfo.titleName}" required><br>
		作品名（かな）：<br>
		<input type="text" name="titleKn" value="${titleInfo.titleKn}" required><br>
		公式サイト：<br>
			<%
				// siteListを取得
				TitleBean title = (TitleBean) session.getAttribute("titleInfo");
				List<TitleSite> siteList = title.getSite();

				if(siteList.size() != 0){
			%>

					<div id="siteUrl">
			<%
					for(int i = 0; i < siteList.size() ; i++){
			%>
						<!-- ここJSPの世界 -->
						<input type="hidden" name="siteId<%= i %>" id="siteIdse<%= i %>" value="<%= siteList.get(i).getSiteId() %>">
						<input type="url" name="siteUrl<%= i %>" id="siteUrlse<%= i %>" value="<%= siteList.get(i).getSiteUrl() %>">
						<button id="se<%= i %>" onclick="deleteBtnEx(this,'siteUrl','siteUrl','siteId')">削除</button><br>
			<%
					}
			%>
					</div>
			<%
				}
			%>
			<div id="addSiteUrl">

				<input type="url" id="addSiteUrls0" name="addSiteUrl0">
				<button id="s0" onclick="deleteBtn(this,'addSiteUrl','addSiteUrl')">削除</button>
				<br>

			</div>
			<input type="button" value="フォーム追加" onclick="addForm('addSiteUrl','addSiteUrl', 's', 'url')"><br>

		公式Twitter：<br>
		<small>※TwitterIDを@から記入</small><br>
			<%
				// twitterListを取得
				List<TitleTwitter> twitterList = title.getTwitter();

				if(twitterList.size() != 0){
			%>
					<div id="twitterId">
			<%
					for(int j = 0; j < twitterList.size() ; j++){
			%>
						<!-- ここJSPの世界 -->
						<input type="hidden" name="tTwitterId<%= j %>" id="tTwitterIdte<%= j %>" value="<%= twitterList.get(j).gettTwitterId() %>">
						<input type="text" name="twitterId<%= j %>" id="twitterIdte<%= j %>" value="<%= twitterList.get(j).getTwitterId() %>">
						<button id="te<%= j %>" onclick="deleteBtnEx(this,'twitterId','twitterId','tTwitterId')">削除</button><br>
			<%
					}
			%>
					</div>
			<%
				}
			%>
			<div id="addTwitterId">

				<input type="text" id="addTwitterIdt0" name="addTwitterId0">
				<button id="t0" onclick="deleteBtn(this,'addTwitterId','addTwitterId')">削除</button>
				<br>

			</div>
			<input type="button" value="フォーム追加" onclick="addForm('addTwitterId','addTwitterId', 't', 'text')"><br>

		表示・非表示設定：<br>
		<c:choose>

			<c:when test="${titleInfo.tOnOff == 1 }">
				<input type="radio" name="tOnOff" value="1" checked> 表示
    			<input type="radio" name="tOnOff" value="0"> 非表示<br>
			</c:when>
			<c:otherwise>
				<input type="radio" name="tOnOff" value="1"> 表示
    			<input type="radio" name="tOnOff" value="0" checked> 非表示<br>
			</c:otherwise>

		</c:choose>

		<br>
		<input type="submit" value="変更">

	</form>

	<br>
	<form action="/Oshikatsu/title_delete" method="POST">
		<input type="hidden" name="titleId" value="${titleInfo.titleId}"/>
		<input type="submit" value="削除" onclick="return confirm('本当に削除しますか？')">
	</form>

	<!-- formList -->
	</div>

	<br>
	<div class="menuList">
		<a href="/Oshikatsu/title_list">作品一覧</a>
	</div>
	<div class='menuList'>
		<a href="<c:url value="/title_detail">
			<c:param name="titleId" value="${titleInfo.titleId}" />
			</c:url>">詳細</a><br>
	</div>
	<div class="menuList">
		<a href="/Oshikatsu/home">ホーム</a>
	</div>

	<!-- wakuのdiv -->
	</div>

	<br>
	<br>


</body>
</html>