<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">学生別成績処理画面</c:param>

	<c:param name="body">
	    <!-- Latest compiled and minified JavaScript -->
	    <script src="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.js"></script>
	    <!-- Latest compiled and minified CSS -->
	    <link rel="stylesheet" href="https://unpkg.com/multiple-select@1.5.2/dist/multiple-select.min.css">

        <h1>学生毎成績登録</h1>
<%

  //String strError1 = "エラーメッセージ領域";
  String strError = (String)request.getAttribute("errMessage");


  String disStID = (String)request.getAttribute("studentID");
  String disStName = (String)request.getAttribute("studentName");
  String[][]  disALLData = (String[][])request.getAttribute("ALLGrade");

  int dataCnt = 0;
  //String[] strArrData1 = {"","","","",""};

  String strData1 ="";
  String strData2 ="";
  String strData3 ="";
  String strData4 ="";
  String strData5 ="";

  //初期画面用処理nullが表示されないようにする
  if (disStID == null){
	  disStID = "";
  }

  //初期画面用処理nullが表示されないようにする
  if (disStName == null){
	  disStName = "";
  }

  //オブジェクトがないときは件数は0件でカウント
  if (disALLData == null){
	  dataCnt = 1;
	  disALLData = new String[1][5];
	  disALLData[0][0] =" ";
	  disALLData[0][1] =" ";
	  disALLData[0][2] =" ";
	  disALLData[0][3] =" ";
	  disALLData[0][4] =" ";
  }else{
	  dataCnt = disALLData.length;
  }

%>
        <form action="" method="post">
        	学籍番号 <input type="text" name="studentID" value="<%=disStID%>">
        	<input type="checkbox" value="isClosedOnSaturdays">履修単位のみ
        	<input type="submit" value="開始"><%=disStName%>
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
		              for(int i=0;i<dataCnt;i++){
		            	  //strArrData1 = disALLData[i];
		            	  strData1 = disALLData[i][0];
		            	  strData2 = disALLData[i][1];
		            	  strData3 = disALLData[i][2];
		            	  strData4 = disALLData[i][3];
		            	  strData5 = disALLData[i][4];
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

        	<%=strError%>
        	<input type="checkbox" value="isClosedOnSaturdays">エクセル出力
        	<input type="submit" value="実行">
			<input type="button" onclick="location.href='../index.jsp'" value="終了">
		</form>

	</c:param>
</c:import>
