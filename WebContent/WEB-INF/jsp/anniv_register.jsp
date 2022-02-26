<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/Oshikatsu/CSS/form_css.css">
<meta charset="UTF-8">
<title>2次元用推し活手帳電子版（仮）</title>

	<script>

	function AutoCheck(str,checkname) {
	    // 入力文字数が1文字以上あるかどうかを確認
	    if( str.length > 0 ) {
	        // あればチェックを入れる
	        document.getElementById(checkname).checked = true;
	    }
	    else {
	        // なければチェックを外す
	        document.getElementById(checkname).checked = false;
	    }
	}

	</script>

</head>
<body>

	<div class="waku">

	<div class = "formTitle">
	<h2>記念日・誕生日登録</h2>
	</div>

	<div class = "formList">

	<c:if test="${msgAnnivErr != null }">
		<div class = "msgErr">
			<p><c:out value="${msgAnnivErr}" /></p>
		</div>
	</c:if>

	<form action="/Oshikatsu/anniv_register" method="post">

		作品名：<br>
		<select name="titleId">

			<c:forEach var="title" items="${titleListAll }">

				<option value="${title.titleId }">${title.titleName }</option>

			</c:forEach>

		</select>

		<br>

		推しの名前（又は名称）：<br>
		<input type="text" name="annivName" required><br>

		記念日・誕生日：<br>
		<small>※年は半角数字で入力してください。</small><br>
		<input type="text" pattern="[1-9][0-9]*" name="annivYear">年
		<input type="number" max="12" min="1" name="annivMonth" required>月
		<input type="number" max="31" min="1" name="annivDay" required>日<br>

		カウント：<br>
		<small>※年が登録される場合、〇周年、〇回目と表示されます。</small><br>
		<input type="radio" name="annivCnt" value="なし" checked> なし
		<input type="radio" name="annivCnt" value="周年"> 周年
		<input type="radio" name="annivCnt" value="回目"> 回目
		<input type="radio" name="annivCnt" value="annivCntText" id="annivCntText">
		<input type="text" name="annivCntText" oninput="AutoCheck(this.value ,'annivCntText');"><br>

		区分：<br>
		<input type="radio" name="annivClass" value="なし" checked> なし
		<input type="radio" name="annivClass" value="記念日"> 記念日
		<input type="radio" name="annivClass" value="誕生日"> 誕生日
		<input type="radio" name="annivClass" value="annivClassText" id="annivClassText">
		<input type="text" name="annivClassText" oninput="AutoCheck(this.value ,'annivClassText');"><br>

		カラー：<br>
		<input type="color" name="annivClr"><br>

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