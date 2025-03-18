package scoreManagement;

import java.util.ArrayList;
import java.util.List;

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
        // SubjectDAOを使って科目リストを取得
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = subjectDAO.all();

        // 取得した科目リストをリクエストスコープにセット
        req.setAttribute("subjectList", subjectList);

        // 登録画面にフォワード
        req.getRequestDispatcher("/ScoreManagement/ScoreUpload.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        try {
            // フォームから取得
            String subjectCd = req.getParameter("subjectCd");
            String regMonth = req.getParameter("regMonth");

            // 年月を分割
            String[] dateParts = regMonth.split("-");
            String year = dateParts[0];
            String month = dateParts[1];

            // 複数の studentId と score を取得
            String[] studentIds = req.getParameterValues("studentId");
            String[] scores = req.getParameterValues("score");

            List<Score> scoreList = new ArrayList<>();

            // データを Score オブジェクトに変換
            for (int i = 0; i < studentIds.length; i++) {
                Score score = new Score();
                score.setScore(
                    studentIds[i],
                    subjectCd,
                    year,
                    month,
                    Integer.parseInt(scores[i]),
                    1 // count の初期値
                );
                scoreList.add(score);
            }

            // DAO で一括登録
            ScoreDAO scoreDAO = new ScoreDAO();
            scoreDAO.insertScores(scoreList);

            // 成功メッセージ
            req.setAttribute("message", "登録に成功しました！");
        } catch (Exception e) {
            req.setAttribute("message", "登録に失敗しました: " + e.getMessage());
        }

        // リダイレクトしてPOST/Redirect/GETパターンを適用
        resp.sendRedirect(req.getContextPath() + "/ScoreManagement/ScoreUpload");
    }
}
