package dataManagement;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDAO;
import tool.DataManagementCommonServlet;

@WebServlet(urlPatterns={"/dataManagement/StudentDataCheck"})
public class StaffDataCheck extends DataManagementCommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		StudentDAO dao = new StudentDAO();

		List<Student> studentList = dao.all();
        req.setAttribute("studentList", studentList);
        req.getRequestDispatcher("/DataManagement/student.jsp").forward(req, resp);

	}


	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
