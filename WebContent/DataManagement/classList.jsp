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
  width: 400px;
  th,
  td {
    border: 1px solid #ddd;
    padding: 1px;
  }
}

</style>

<h2 class="navbar-brand"><a class="nav-link active" href="/TeachingManagementSystem/dataManagement">データチェック用のページです</a></h2>

    <table border="1">
		<thead>
	        <tr>
	            <th>スタッフID</th>
	            <th>名前</th>
	        </tr>
        </thead>
        <tbody>
	        <c:if test="${not empty list}">
	            <c:forEach var="classList" items="${list}">
	                <tr>
	                    <td>${classList.classCD}</td>
	                    <td>${classList.name}</td>
	                </tr>
	            </c:forEach>
	        </c:if>
	        <c:if test="${empty list}">
	            <tr>
	                <td colspan="3">クラスがありません</td>
	            </tr>
	        </c:if>
        </tbody>
    </table>

</c:param>
</c:import>