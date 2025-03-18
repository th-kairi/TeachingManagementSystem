<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/base.jsp">
    <c:param name="title">得点取り込み画面</c:param>

    <c:param name="body">
        <!-- エラーメッセージ表示 -->
        <c:if test="${not empty message}">
            <div class="alert alert-info" role="alert">
                ${message}
            </div>
        </c:if>

        <div class="container mt-4">
            <h2>得点取り込み</h2>
            <div class="form-row">
                <!-- 科目選択 -->
                <div class="form-group col-md-4">
                    <label for="subjectCd">科目</label>
                    <select name="subjectCd" id="subjectCd" class="form-control" required>
                        <option value="">選択してください</option> <!-- 最初は何も選択されていない状態にする -->
                        <c:forEach var="subject" items="${subjectList}">
                            <option value="${subject.subjectCD}">${subject.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 登録月 -->
                <div class="form-group col-md-4">
                    <label for="regMonth">登録月</label>
                    <input type="month" id="regMonth" name="regMonth" class="form-control" required>
                </div>

                <!-- CSVファイル選択 -->
                <div class="form-group col-md-4">
                    <label for="fileInput">CSVファイル</label>
                    <input type="file" class="form-control-file" id="fileInput" name="fileInput" accept=".csv" onchange="handleFileSelect(event)" required>
                </div>
            </div>

            <!-- CSVデータを確認するためのボタン -->
            <button type="button" class="btn btn-primary btn-lg mt-4" onclick="showCsvData()">CSVデータ確認</button>

            <div id="csvPreview"></div>
        </div>

        <script>
            let csvData = [];

            function handleFileSelect(event) {
                console.log("handleFileSelectが実行されました ===========================")
                const file = event.target.files[0];
                const reader = new FileReader();

                reader.onload = function(e) {
                    const content = e.target.result.trim();
                    const lines = content.split("\n");

                    // CSVの各行を配列に格納 (mapの代わりにforループを使用)
                    csvData = [];
                    for (let i = 0; i < lines.length; i++) {
                        const line = lines[i];
                        const [studentId, score] = line.split(",");
                        if (studentId && score) {
                            // 配列にデータを手動で追加
                            csvData.push({ studentId: studentId.trim(), score: score.trim() });
                        }
                    }
                };

                reader.readAsText(file);
            }

            function showCsvData() {
                console.log("showCsvDataが実行されました ===========================")

                // 必要な入力がすべて揃っているかチェック
                const subjectCd = document.getElementById("subjectCd").value;
                const regMonth = document.getElementById("regMonth").value;
                const fileInput = document.getElementById("fileInput").files[0];

                if (!subjectCd || !regMonth || !fileInput) {
                    alert("すべての項目（科目、登録月、CSVファイル）を選択してください。");
                    return;
                }

                if (csvData.length === 0) {
                    alert("CSVデータがありません。ファイルを選択してください。");
                    return;
                }

                // コンソールにCSVデータを表示
                console.log("CSV Data:", csvData);

            }
        </script>

    </c:param>

</c:import>
