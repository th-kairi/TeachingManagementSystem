package dataManagement;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDAO;
import tool.DataManagementCommonServlet;

@WebServlet(urlPatterns={"/dataManagement/SubjectDataCheck"})
public class SubjectDataCheck extends DataManagementCommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // SubjectDAOを使ってデータ取得
        SubjectDAO dao = new SubjectDAO();
        List<Subject> subjectList = dao.all();

        // 取得したデータをリクエストにセット
        req.setAttribute("list", subjectList);

        // JSPにフォワード
        req.getRequestDispatcher("/DataManagement/subjectList.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // POST処理が不要な場合は何もしない
    }
}
