package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;
import dao.StaffDAO;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/accounts/login" })
public class LoginController extends CommonServlet {

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#get(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#post(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String login = req.getParameter("login");
		String password = req.getParameter("password");


		// 入力チェック
		
		// ログイン処理
		StaffDAO staffDAO = new StaffDAO();
		Staff staff = null;
		
		staff = staffDAO.getLoginUser(login, password);
		
		if (staff == null) {
			// ログイン失敗
			req.getRequestDispatcher("login.jsp").forward(req, resp);			
		} else {
			// ログイン成功
			// セッション情報を取得
			HttpSession session = req.getSession(true);

			// セッションにログイン情報(staff)を保存
			session.setAttribute("staff", staff);
			
			req.getRequestDispatcher("../index.jsp").forward(req, resp);
		}

	}

}
