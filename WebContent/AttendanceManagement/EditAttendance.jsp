<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">
	<form action="./show" method="post">
		<input type="hidden" id="year" name="year" value=2024 />
		<input type="text" id="month" name="month" required maxlength="2" size="10" value="${month}" />
		<label for="month">月</label>
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
						<th><fmt:formatDate value="${date}" pattern="d" /></th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:set var="lastStudentID" value="first" />
					<!-- 学籍番号ごとに行を生成 -->
					<c:forEach var="attendance" items="${attendanceList}">
						<c:if test="${attendance.studentID != lastStudentID}">
							<c:if test="${lastStudentID != 'first'}"></tr></c:if>
							<tr>
								<td>${attendance.studentID}</td>
								<td>${studentMap[attendance.studentID].name}</td>
						</c:if>
						<td>${attendanceNameMap[attendance.attendance].attendanceName}</td>
						<c:set var="lastStudentID" value="${attendance.studentID}" />
					</c:forEach>
					</tr>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>該当データなし</p>
		</c:otherwise>
	</c:choose>
	<style>
		table, td, th { border: 2px #808080 solid; }
	</style>
</c:param>

</c:import>