<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">

<h1>データチェック用のページです</h1>

    <table border="1">
        <tr>
            <th>スタッフID</th>
            <th>名前</th>
            <th>権限フラグ</th>
        </tr>
        <c:if test="${not empty staffList}">
            <c:forEach var="staff" items="${staffList}">
                <tr>
                    <td>${staff.staffID}</td>
                    <td>${staff.name}</td>
                    <td>${staff.kflag}</td>
                </tr>
            </c:forEach>
        </c:if>
        <c:if test="${empty staffList}">
            <tr>
                <td colspan="3">スタッフがいません。</td>
            </tr>
        </c:if>
    </table>

</c:param>

</c:import>