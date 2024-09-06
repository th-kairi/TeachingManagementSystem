<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${ param.title }</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
	crossorigin="anonymous"></script>
<%-- JQueryのインポート --%>
<script src="https://cdn.jsdelivr.net/npm/jquery/dist/jquery.min.js"></script>

</head>
<body>
	<c:import url="/navigation.jsp" />
	<div class="container-fluid">
		<div class="row">
			<!-- SideBar -->
			<aside class="col-2 py-5">
				<c:import url="/sidebar.jsp" />
			</aside>
			<!-- Section-->
			<section class="col py-2">
				<div class="container px-4 px-lg-5 mt-5">${ param.body }</div>
			</section>
		</div>
	</div>

</body>
</html>