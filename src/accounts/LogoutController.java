package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/accounts/logout" })
public class LogoutController extends CommonServlet {

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#get(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		post(req, resp);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#post(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {


		// ログイン成功
		// セッション情報を取得
		HttpSession session = req.getSession(true);
		// セッションを破棄
		session.invalidate();
		req.getRequestDispatcher("./login.jsp").forward(req, resp);

	}

}
