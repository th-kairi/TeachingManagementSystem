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
  width: 600px;
}
th, td {
  border: 1px solid #ddd;
  padding: 5px;
  text-align: left;
}
</style>

<h2 class="navbar-brand">
    <a class="nav-link active" href="/TeachingManagementSystem/dataManagement">
        データチェック用のページです
    </a>
</h2>

<table border="1">
    <thead>
        <tr>
            <th>科目コード</th>
            <th>科目名</th>
            <th>単位数</th>
            <th>締切日</th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${not empty list}">
            <c:forEach var="subject" items="${list}">
                <tr>
                    <td>${subject.subjectCD}</td>
                    <td>${subject.subjectName}</td>
                    <td>${subject.credit}</td>
                    <td>${subject.closingDate}</td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty list}">
            <tr>
                <td colspan="4">科目がありません</td>
            </tr>
        </c:if>
    </tbody>
</table>

</c:param>
</c:import>
