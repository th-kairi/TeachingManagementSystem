<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">
	<p>${ errorMessage }</p>
	<form action="./show" method="post">
		<label for="yearmonth">年月</label>
		<input type="month" id="yearmonth" name="yearmonth" required value="${yearmonth}" />
		<input type="text" id="classCd" name="classCd" required maxlength="3" size="10" value="${classCd}" />
		<label for="classCd">組</label>
		<button class="btn btn-primary" type="submit">検索</button>
	</form>

	<c:choose>
		<c:when test="${!empty attendanceList}">
			<table border="1">
				<thead>
					<tr>
						<th>学籍番号</th>
						<th>氏名</th>
						<!-- 日付の列を動的に生成 -->
						<c:forEach var="date" items="${uniqueDates}">
						<th><fmt:formatDate value="${date}" pattern="d" />日</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:set var="lastStudentID" value="first" />

					<!-- 学籍番号ごとに行を生成 -->
					<c:forEach var="attendance" items="${attendanceList}">
						<c:if test="${attendance.studentID != lastStudentID}">
							<!--  学籍番号が変わったので、その行を閉じてから処理する --> 
							<!--  firstの時はまだどの学籍番号も処理していない。それ以外だけ行を閉じる -->
							<c:if test="${lastStudentID != 'first'}"></tr></c:if>

							<c:set var="date" value="1" />
							<tr>
								<td>${attendance.studentID}</td><!-- 学籍番号  -->
								<td>${studentMap[attendance.studentID].name}</td><!-- 氏名  -->
						</c:if>
						<td><img class="attendance_image" src="${pageContext.request.contextPath}/static/img/attendance${attendance.attendance}.png" data-studentid="${attendance.studentID}" data-yearmonth="${yearmonth}" data-date="${date}" /></td><!-- 出欠  -->
						<c:set var="lastStudentID" value="${attendance.studentID}" />
						<c:set var="date" value="${date + 1}" />
					</c:forEach>
					</tr>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>該当データなし</p>
		</c:otherwise>
	</c:choose>

	<%-- ポップアップ画面レイアウトここから --%>
	<div id="bg">
    <div class="select">
    	<!-- 学籍番号と名前を表示 -->
    	<p>学籍番号：${attendance.studentID}　${studentMap[attendance.studentID].name}</p>
    	<form action="./show" method="post">
    		<div class="col-4">
				<label>出席状況</label>
				<select name="syusseki"id="syusseki">
					<option value="0">--------</option>
					<option value="1">欠席/退学予定者</option>
					<option value="2">遅刻</option>
					<option value="3">早退</option>
					<option value="4">その他欠席</option>
					<option value="5">休学中</option>
					<option value="6">退学者</option>
					<option value="7">休日</option>
					<option value="8">遅刻+早退</option>
				</select>
			</div>
			<p>理由</p>
			<input type="text" name="reason" value="${ reason }">
			<input type="submit" value="確定"><button type='button' id="cancel" class=close>キャンセル</button>
		</form>
    	</div>
    </div>
	<%-- ポップアップ画面レイアウトここまで --%>

	<style>
		table, td, th { border: 2px #808080 solid; }
		.attendance_image {width: 30px; height: 30px; }

		<%-- ポップアップ画面表示の際のcssここから --%>
		/* モーダル画面の背景 */
		div#bg{
		  position: absolute;        /* 親要素に対して絶対座標で表示 */
		  display: none;             /* 初期状態は非表示 */
		  top: 200; left: 200;           /* 左上隅から表示 */
		  width: 100%; height: 100%; /* 画面いっぱいに表示 */
		  background-color: rgba(0, 0, 0, 0.5); /* 背景は黒色透過 */
		}
		/* モーダル画面(選択肢一覧画面)本体 */
		div.select{
		  position: absolute;
		  top: 1rem; left: 50%;
		  transform: translateX(-50%);
		  width: 80%;
		  background-color: #fff;
		  padding: 1rem;
		}
		<%-- ポップアップ画面表示の際のcssここまで --%>

	</style>


	<script src="${pageContext.request.contextPath}/static/js/attendance.js"></script>


	<%-- ポップアップ画面を表示させるjavascriptここから --%>
	<script>
		$(function(){
			  // 選択肢表示画面を表示
			  $('input.selectId').click(function(){
			    openSelectWindow("#bg");
			  });
			});

			// 選択肢表示画面を表示：汎用的に使えるように分離
			function openSelectWindow(bgId){
			  // 本関数が呼ばれたら表示
			  $(bgId).css("display","block");

			  $(bgId+' .close').unbind().click(function(){
			    $(bgId).css("display","none");
			  });

			}
	</script>
	<%-- ポップアップ画面を表示させるjavascriptここまで --%>

</c:param>

</c:import>