package scoreManagement;


import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ScoreDetail;
import dao.ScoreManagementDAO;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/ScoreManagement/show" })
public class ScoreManagementController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		this.post(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String classCd = req.getParameter("classCd");
		String subjectCd = req.getParameter("subjectCd");


		// データの取得
		ScoreManagementDAO scoreManagementDAO = new ScoreManagementDAO();

		List<ScoreDetail> scoreDetailList = null;

		scoreDetailList = scoreManagementDAO.getScoreByClassCdSubjectCd(classCd, subjectCd);

	    // 取得したデータをリクエストスコープに格納
		req.setAttribute("scoreDetailList", scoreDetailList);
	    req.setAttribute("classCd", classCd);  // classCd もJSPで使えるようにする
	    req.setAttribute("subjectCd", subjectCd);  // subjectCd もJSPで使えるようにする


		// 画面遷移
		req.getRequestDispatcher("/ScoreManagement/ShowScore.jsp").forward(req, resp);
	}

}
