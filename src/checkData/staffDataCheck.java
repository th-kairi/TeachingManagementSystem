package checkData;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Staff;
import dao.StaffDAO;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/checkData/staffDataCheck"})
public class staffDataCheck extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		StaffDAO dao = new StaffDAO();

		List<Staff> staffList = dao.all();
        req.setAttribute("staffList", staffList);
        req.getRequestDispatcher("staff.jsp").forward(req, resp);

	}


	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
