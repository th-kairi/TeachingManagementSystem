<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">経理本科教務システム</c:param>

<c:param name="body">

<h2 class="navbar-brand"><a class="nav-link active" href="/TeachingManagementSystem/dataManagement">データチェック用のページです</a></h2>

<ul>
	<li><a class="nav-link active" href="/TeachingManagementSystem/dataManagement/StaffDataCheck">Staff</a></li>
	<li><a class="nav-link active" href="/TeachingManagementSystem/dataManagement/ClassListDataCheck">ClassList</a></li>

</ul>

</c:param>

</c:import>