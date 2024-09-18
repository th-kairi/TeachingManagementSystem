<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">ログイン</c:param>

<c:param name="body">

<p>${ errorMessage }</p>
<form action="login" method="post">
	<div class="row mb-3">
		<label for="login" class="col-sm-2 form-label">ユーザID</label>
		<div class="col-sm-10">
			<input type="text" class="form-control" id="login" name="login" value="${ login }"  required>
		</div>
	</div>
	<div class="row mb-3">
		<label for="password" class="col-sm-2 form-label">パスワード</label>
		<div class="col-sm-10">
			<input type="password" class="form-control" id="password" name="password" required>
		</div>
	</div>
	<button class="btn btn-primary" type="submit">ログイン</button>
</form>

</c:param>

</c:import>