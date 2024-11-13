<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">
<style>

table {
  table-layout: fixed;
  border: 1.1px solid #000;
  border-spacing: 0px;
  width: 500px;
  th,
  td {
    border: 1px solid #ddd;
    padding: 1px;
  }
}

</style>
<h2 class="navbar-brand"><a class="nav-link active" href="/TeachingManagementSystem/dataManagement">データチェック用のページです</a></h2>

    <table border="1">
        <tr>
            <th>学籍番号</th>
			<th>学生名</th>
			<th>性別</th>
			<th>本人連絡先</th>
			<th>保護者連絡先</th>
			<th>退学フラグ</th>
        </tr>
        <c:if test="${not empty studentList}">
            <c:forEach var="staff" items="${studentList}">
                <tr>
                    <td>${student.staffID}</td>
                    <td>${student.name}</td>
                    <td>${student.password}</td>
                    <td>${student.KFlag}</td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty studentList}">
            <tr>
                <td colspan="3">学生がいません。</td>
            </tr>
        </c:if>
    </table>

</c:param>

</c:import>