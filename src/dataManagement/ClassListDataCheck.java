package dataManagement;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassList;
import dao.ClassListDAO;
import tool.DataManagementCommonServlet;

@WebServlet(urlPatterns={"/dataManagement/ClassListDataCheck"})
public class ClassListDataCheck extends DataManagementCommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		ClassListDAO dao = new ClassListDAO();

		List<ClassList> classList = dao.all();
        req.setAttribute("list", classList);
        req.getRequestDispatcher("/DataManagement/classList.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}
}
