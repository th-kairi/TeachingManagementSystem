package accounts;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = req.getSession();

		String login = req.getParameter("login");
		String password = req.getParameter("password");

		// ログイン処理

		req.getRequestDispatcher("../index.jsp").forward(req, resp);

	}

}
