<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid px-4 px-lg-5">
        <div class="col-4">
			<h2 class="navbar-brand"><a class="nav-link active" href="/TeachingManagementSystem/">出欠席・成績管理システム</a></h2>
		</div>

            <p style="font-size: 5px; mix-blend-mode:overlay;"><a  href="/TeachingManagementSystem/DataManagement/">データ確認画面</a></p>
		<div class="col-5 collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
            <ul class="navbar-nav">
				<c:choose>
					<c:when test="${not empty staff}">
						<li>${staff.name} <c:if test="${staff.KFlag == '1'}">(管理者)</c:if></li>
						<li><a class="nav-link active" href="/TeachingManagementSystem/accounts/logout">ログアウト</a></li>
					</c:when>
					<c:otherwise>
						<li><a class="nav-link active" href="/TeachingManagementSystem/accounts/login">ログイン</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>

<hr>
