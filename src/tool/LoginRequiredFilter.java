package tool;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Staff;

//@WebFilter(urlPatterns = { "/*" })
@WebFilter(urlPatterns = { "/nothing" })
public class LoginRequiredFilter implements Filter {

	@Override
	public void destroy() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// ログイン不要ページならスキップ
		String[] excludesUri = new String[] {"/TeachingManagementSystem/accounts/login"};

		if ( Arrays.asList(excludesUri).contains(
				((HttpServletRequest) request).getRequestURI())
			) {
			// ログインが不要なページ

		} else {
			// ログインが必要ページ

			HttpServletRequest req = (HttpServletRequest) request;

			// セッション情報を取得
			HttpSession session = req.getSession(true);

			// セッションからログイン情報(staff)を取得
			Staff staff = (Staff) session.getAttribute("staff");

			
			// ログインしていなかったら
			if (staff == null) {
			    // メッセージを出す場合はリクエストが変わるのでリクエストでは渡せない。セッションで渡す
			    // session.setAttribute("errorMessage", "ログインしてください");

				// ログインページにリダイレクト
				((HttpServletResponse) response).sendRedirect("/TeachingManagementSystem/accounts/login");
				return;
			}
		}

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO 自動生成されたメソッド・スタブ

	}

}
