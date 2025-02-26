<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">出席確定処理</c:param>

<c:param name="body">
<h2>出席確定処理</h2>

<form method="post">
    <label for="baseDate">確定月:</label>
    <input type="month" id="baseDate" name="baseDate" value="${baseDate}">

    <h3>クラス選択</h3>

     <c:forEach var="classList" items="${list}">
	    <input type="checkbox" name="classCodes" value=${classList.classCD}>${classList.classCD} (${classList.name})<br>
     </c:forEach>


    <input type="submit" value="確定">
</form>

</c:param>

</c:import>
