<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">
	<p>${ errorMessage }</p>
	<form action="./show" method="post">
		<label for="classCd">クラスCD</label>
		<input type="text" id="classCd" name="classCd" required maxlength="3" size="10" value="131" />

		<label for="subjectCd">科目コード</label>
		<input type="text" id="subjectCd" name="subjectCd" required value="CS5" />
		<button class="btn btn-primary" type="submit">表示</button>
	</form>

	<c:choose>
		<c:when test="${!empty scoreDetailList}">
			<table>
				<thead>
					<tr>
						<th>学籍番号</th>
						<th>氏名</th>
						<th>科目コード</th>
						<th>科目名</th>
						<th>年</th>
						<th>月</th>
						<th>得点</th>
						<th>回数</th>
					</tr>
				</thead>

				<tbody>
					<!-- 学籍番号ごとに行を生成 -->
					<c:forEach var="scoreDetail" items="${scoreDetailList}">
						<tr>
							<td>${scoreDetail.studentId}</td>
							<td>${scoreDetail.name}</td>
							<td>${scoreDetail.subjectCd}</td>
							<td>${scoreDetail.subjectName}</td>
							<td>${scoreDetail.year}</td>
							<td>${scoreDetail.month}</td>
							<td>${scoreDetail.score}</td>
							<td>${scoreDetail.count}</td>
						</tr>			
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>該当データなし</p>
		</c:otherwise>
	</c:choose>

	</style>

</c:param>

</c:import>