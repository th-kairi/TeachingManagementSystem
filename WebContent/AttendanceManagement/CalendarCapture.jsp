<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">カレンダー取り込み画面</c:param>

	<c:param name="body">
	    <!-- Latest compiled and minified JavaScript -->
	    <script src="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.js"></script>
	    <!-- Latest compiled and minified CSS -->
	    <link rel="stylesheet" href="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.css">

	    <p class="fs-4 text">カレンダー取り込み用のExcel ファイルを選択してください</p>

		<form action="" method="post">
		<!-- Multiple Select -->
		    <p>登録するクラスを選択してください：
		    <select multiple="multiple">
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
		      <option value="232">232</option>
		      <option value="233">233</option>
		    </select></p>
			<p><input type="file"></p>

			<p><input type="checkbox" value="isClosedOnSaturdays">土曜日を休講にする</p>
			<p><input type="checkbox" value="isClosedOnSunday">日曜日を休講にする</p>
			<p class="text-danger">※全ての曜日が一括変更になりますので注意ください</p>

			<p><input type="submit" value="登録する"></p>
		</form>

	    <script>
	    $(function () {
	        $('select').multipleSelect({
	            width: 200,
	            formatSelectAll: function() {
	                return 'すべて';
	            },
	            formatAllSelected: function() {
	                return '全て選択されています';
	            }
	        });
	    });
	    </script>

	</c:param>

</c:import>