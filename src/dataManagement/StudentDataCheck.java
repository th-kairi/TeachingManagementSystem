package dataManagement;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Staff;
import dao.StaffDAO;
import tool.DataManagementCommonServlet;

@WebServlet(urlPatterns={"/dataManagement/StaffDataCheck"})
public class StudentDataCheck extends DataManagementCommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		StaffDAO dao = new StaffDAO();

		List<Staff> staffList = dao.all();
        req.setAttribute("staffList", staffList);
        req.getRequestDispatcher("/DataManagement/staff.jsp").forward(req, resp);

	}


	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
