<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">

	<c:param name="title">出席状況確認画面</c:param>

	<c:param name="body">
		<div class="d-grid d-md-flex">
			<form class=" py5" action="" method="get">
				基準日 <input type="date" name="baseDate" value=${baseDate} >
				クラス <select id="slc" name="classCode">
					<option value="101">101</option>
					<option value="102">102</option>
					<option value="103">103</option>
					<option value="104">104</option>
					<option value="105">105</option>
					<option value="106">106</option>
					<option value="108">108</option>
					<option value="109">109</option>
					<option value="131">131</option>
					<option value="132">132</option>
					<option value="133">133</option>
					<option value="201">201</option>
					<option value="202">202</option>
					<option value="203">203</option>
					<option value="204">204</option>
					<option value="205">205</option>
					<option value="206">206</option>
					<option value="208">208</option>
					<option value="209">209</option>
					<option value="231">231</option>
					<option selected value="232">232</option>
					<option value="233">233</option>
				</select> <input type="submit" value="検索">
			</form>
			<div class="d-grid gap-2 d-md-flex justify-content-md-end">
				<a class="btn btn-outline-dark" href="#" role="button">月</a>
			</div>
		</div>

		<div class="scrolltable">
			<table>
				<c:import
					url="/AttendanceManagement/ConfirmationOfAttendanceData.jsp" />
			</table>
		</div>

	</c:param>

</c:import>