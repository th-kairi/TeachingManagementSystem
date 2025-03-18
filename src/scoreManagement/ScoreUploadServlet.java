package scoreManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Score;
import bean.Subject;
import dao.ScoreDAO;
import dao.SubjectDAO;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/ScoreManagement/ScoreUpload" })
public class ScoreUploadServlet extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 科目リストを取得
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = subjectDAO.all();
        req.setAttribute("subjectList", subjectList);

        // メッセージがある場合に表示
        String message = req.getParameter("message");
        if (message != null) {
            req.setAttribute("message", message);
        }

        // JSPへフォワード
        req.getRequestDispatcher("/ScoreManagement/ScoreUpload.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームデータを取得
        String subjectCd = request.getParameter("subjectCd");
        String regMonth = request.getParameter("regMonth");
        String csvDataStr = request.getParameter("csvData");

        // エラーチェック
        if (subjectCd == null || regMonth == null || csvDataStr == null || csvDataStr.isEmpty()) {
            response.sendRedirect("ScoreUpload?message=" + encode("必要な情報が不足しています"));
            return;
        }

        // CSVデータを解析
        List<Score> scores = new ArrayList<>();
        try {
            // CSVデータを分解（JSON形式を仮定している？）
            String[] csvDataArray = csvDataStr.split("},\\{");

            for (String line : csvDataArray) {
                // 余計な文字を削除
                line = line.replace("{", "").replace("}", "").replace("\"", "").replace("]", "");

                String[] keyValuePairs = line.split(",");

                String studentId = null;
                int scoreValue = 0;

                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length < 2) {
                        continue;
                    }

                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();

                    if (key.equals("studentId")) {
                        studentId = value;
                    } else if (key.equals("score")) {
                        scoreValue = Integer.parseInt(value);
                    }
                }

                // データが正しく取得できた場合のみ処理
                if (studentId != null && scoreValue > 0) {
                    // Scoreオブジェクトを作成して追加
                    Score score = new Score();
                    score.setScore(studentId, subjectCd, regMonth.split("-")[0], regMonth.split("-")[1], scoreValue, 1);
                    scores.add(score);
                    System.out.println("追加されたScore: " + score);
                } else {
                    System.out.println("データが不完全なためスキップ: studentId=" + studentId + ", scoreValue=" + scoreValue);
                }
            }

            // 登録データがある場合はDBに保存
            if (!scores.isEmpty()) {
                // List<Score> -> Score[] に変換
                Score[] scoreArray = new Score[scores.size()];
                scoreArray = scores.toArray(scoreArray); // リストを配列に変換

                ScoreDAO scoreDAO = new ScoreDAO();
                String message = scoreDAO.insertScores(scoreArray);  // DB登録
                response.sendRedirect("ScoreUpload?message=" + encode(message));
            } else {
                response.sendRedirect("ScoreUpload?message=" + encode("有効なデータがありません"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ScoreUpload?message=" + encode("データ登録に失敗しました"));
        }
    }

    // URLエンコードメソッド
    private String encode(String message) throws IOException {
        return java.net.URLEncoder.encode(message, "UTF-8");
    }
}
