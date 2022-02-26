<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">

<script>

	var i = 1 ;

	function addForm(branch, form_area, idAdd, type) {

  		var input_data = document.createElement('input');
  		input_data.type = type;
  		input_data.id = branch + idAdd + i;
  		input_data.name = branch + i;
  		if (type == "text") {
  			input_data.pattern = '@^[0-9A-Za-z._%+-]+$';
  		  }
  		var parent = document.getElementById(form_area);
  		parent.appendChild(input_data);

  		var button_data = document.createElement('button');
 		button_data.id = idAdd + i;
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

</script>



<title>2次元用推し活手帳電子版</title>
</head>
<body>

	<div class="waku">

	<div class = "formTitle">
	<h2>作品登録</h2>
	</div>

	<div class = "formList">

	<c:if test="${msgTitle != null }">
		<div class = "msgErr">
			<p><c:out value="${msgTitle}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/title_register" method="post">

		作品名：<br>
		<input type="text" name="titleName" required><br>
		作品名（かな）：<br>
		<input type="text" name="titleKn" required><br>

		公式サイト：
		<div id="siteUrl">

			<input type="url" id="siteUrls0" name="siteUrl0">
			<button id="s0" onclick="deleteBtn(this,'siteUrl','siteUrl')">削除</button>
			<br>

		</div>
		<input type="button" value="フォーム追加" onclick="addForm('siteUrl','siteUrl', 's', 'url')"><br>

		公式Twitter：<br>
		<small>※TwitterIDを@から記入</small><br>
		<div id="twitterId">

			<input type="text" id="twitterIdt0" name="twitterIdt0" pattern="@[0-9a-zA-Z_]{1,15}">
			<button id="t0" onclick="deleteBtn(this,'twitterId','twitterId')">削除</button>
			<br>

		</div>
		<input type="button" value="フォーム追加" onclick="addForm('twitterId','twitterId', 't', 'text')"><br>

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