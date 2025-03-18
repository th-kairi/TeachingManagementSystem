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
            <form id="scoreUploadForm" method="POST">
                <div class="form-row">
                    <!-- 科目選択 -->
                    <div class="form-group col-md-4">
                        <label for="subjectCd">科目</label>
                        <select name="subjectCd" id="subjectCd" class="form-control" required>
                            <option value="">選択してください</option>
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
                <button type="button" class="btn btn-primary btn-lg mt-4" onclick="submitCsvData()">送信</button>

                <!-- 隠しフィールドでJSONデータを送信 -->
                <input type="hidden" name="csvData" id="csvData" />
            </form>

            <div id="csvPreview"></div>
        </div>

        <script>
            let csvData = [];

            // ファイルが選択されたときに呼び出される関数
            function handleFileSelect(event) {
                const file = event.target.files[0];  // 選択されたファイル
                const reader = new FileReader();  // FileReaderを使用してファイルを読み込む

                reader.onload = function(e) {
                    const content = e.target.result.trim();  // ファイル内容を取得し前後の空白を除去
                    const lines = content.split("\n");  // 改行で分割して行ごとの配列を作成

                    // CSVの各行を配列に格納
                    csvData = [];
                    for (let i = 0; i < lines.length; i++) {
                        const line = lines[i].trim();
                        const [studentId, score] = line.split(",");  // カンマで区切り、学生IDと得点を取得
                        if (studentId && score) {
                            // 配列にデータを手動で追加
                            csvData.push({ studentId: studentId.trim(), score: score.trim() });
                        }
                    }

                };

                reader.readAsText(file);  // ファイルをテキストとして読み込む
            }

            // CSVデータをフォームで送信する前にJSONに変換して隠しフィールドにセット
            function submitCsvData() {
                const subjectCd = document.getElementById("subjectCd").value;  // 科目コード
                const regMonth = document.getElementById("regMonth").value;  // 登録月
                const fileInput = document.getElementById("fileInput").files[0];  // CSVファイル

                // 必要な入力がすべて揃っているかチェック
                if (!subjectCd || !regMonth || !fileInput) {
                    alert("すべての項目（科目、登録月、CSVファイル）を選択してください。");
                    return;
                }

                // CSVデータが空かどうか確認
                if (csvData.length === 0) {
                    alert("CSVデータがありません。ファイルを選択してください。");
                    return;
                }

                // 送信するデータをJSON形式に変換
                const jsonData = {
                    subjectCd: subjectCd,
                    regMonth: regMonth,
                    csvData: csvData
                };

                // JSONデータを隠しフィールドにセット
                document.getElementById("csvData").value = JSON.stringify(jsonData);

                // フォームを送信
                document.getElementById("scoreUploadForm").submit();
            }
        </script>
    </c:param>
</c:import>
