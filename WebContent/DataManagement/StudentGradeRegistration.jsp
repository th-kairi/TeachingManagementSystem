<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">カレンダー取り込み画面</c:param>

	<c:param name="body">
	    <!-- Latest compiled and minified JavaScript -->
	    <script src="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.js"></script>
	    <!-- Latest compiled and minified CSS -->
	    <link rel="stylesheet" href="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.css">

        <h1>学生毎成績登録</h1>
<%
  String strError1 = "エラーメッセージ領域";
  String strError = (String)request.getAttribute("errMessage");


  String verTest1 = (String)request.getAttribute("studentID");
  String verTest2 = (String)request.getAttribute("studentName");
  String[][]  vertest3 = (String[][])request.getAttribute("ALLGrade");

  int vertest4 = 0;
  String[] strArrData1 = {"","","","",""};

  String strData1 ="";
  String strData2 ="";
  String strData3 ="";
  String strData4 ="";
  String strData5 ="";

  if (verTest1 == null){
	  verTest1 = "";
  }

  if (verTest2 == null){
	  verTest2 = "";
  }

  //オブジェクトがないときは件数は0件でカウント
  if (vertest3 == null){
	  vertest4 = 0;
  }else{
	  vertest4 = vertest3.length;
  }

%>
        <form action="" method="post">
        	学籍番号 <input type="text" value="<%=verTest1%>">
        	<input type="checkbox" value="isClosedOnSaturdays">履修単位のみ
        	<input type="submit" value="開始"><%=verTest2%>
		</form>

		<!--  <div class="scrolltable">>  -->

        <form action="StudentGradeRegistrationEnd" method="post">
		<div>
			<table border="1">
                <thead>
	                <tr>
		                <th>学年</th>
		                <th>月</th>
		                <th>科目コード</th>
		                <th>科目名</th>
		                <th>科目点数</th>
		            </tr>
		        </thead>
		        <tbody>
		            <%
		              for(int i=0;i<vertest4;i++){
		            	  strArrData1 = vertest3[i];
		            	  strData1 = strArrData1[0];
		            	  strData2 = strArrData1[1];
		            	  strData3 = strArrData1[2];
		            	  strData4 = strArrData1[3];
		            	  strData5 = strArrData1[4];
		            %>
		            <tr>
		                <th><input type="text" name="v1" value=<%=strData1%>></th>
		                <th><input type="text" name="v2" value=<%=strData2%>></th>
		                <th><input type="text" name="v3" value=<%=strData3%>></th>
		                <th><input type="text" name="v4" value=<%=strData4%>></th>
		                <th><input type="text" name="v5" value=<%=strData5%>></th>
		            </tr>
		            <%
		              }
		            %>
		        </tbody>
			</table>
		</div>

        	<%=strError%>>
        	<input type="checkbox" value="isClosedOnSaturdays">エクセル出力
        	<input type="submit" value="実行">
			<input type="button" onclick="location.href='../index.jsp'" value="終了">
		</form>

	</c:param>
</c:import>
