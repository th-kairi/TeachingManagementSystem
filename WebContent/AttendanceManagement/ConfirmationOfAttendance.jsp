<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">

	<c:param name="title">出席状況確認画面</c:param>

	<c:param name="body">
	<% String classCode = request.getParameter("classCode"); %>
		<div class="d-flex justify-content-start">
			<form class="py5" action="" method="get">
				基準日 <input type="date" name="baseDate" value=${baseDate} >
				クラス <select id="slc" name="classCode">
		        <c:if test="${not empty list}">
		        <%-- classListに入っているクラスをプルダウンリストに展開 --%>
		            <c:forEach var="classList" items="${list}">
						<option value="${classList.classCD}">${classList.classCD}</option>

		            </c:forEach>
		        </c:if>
		        <c:if test="${empty list}">
						<option value="">クラスがありません</option>
		        </c:if>
				</select> <input type="submit" value="検索">
			</form>
		</div>
		<div class="d-flex justify-content-end">
			<a class="btn btn-outline-dark" href="#" role="button">月</a>
		</div>


		<div class="scrolltable">
			<table>
				<c:import
					url="/AttendanceManagement/ConfirmationOfAttendanceData.jsp" />
			</table>
		</div>

	</c:param>

</c:import>