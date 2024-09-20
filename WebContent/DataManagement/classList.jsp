<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">

<h2 class="navbar-brand"><a class="nav-link active" href="/TeachingManagementSystem/dataManagement">データチェック用のページです</a></h2>

    <table border="1">
        <tr>
            <th>クラスID</th>
            <th>名前</th>
        </tr>
        <c:if test="${not empty list}">
            <c:forEach var="list" items="${list}">
                <tr>
                    <td>${list.classCD}</td>
                    <td>${list.name}</td>
                </tr>
            </c:forEach>
            <p>${list}</p>
        </c:if>
        <c:if test="${empty staffList}">
            <tr>
                <td colspan="3">クラスリストがありません</td>
            </tr>
        </c:if>
    </table>

</c:param>

</c:import>