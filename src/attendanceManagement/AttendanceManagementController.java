package attendanceManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Attendance;
import bean.AttendanceName;
import bean.Student;
import dao.AttendanceManagementDAO;
import dao.AttendanceNameDAO;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/AttendanceManagement/show" })
public class AttendanceManagementController extends CommonServlet {

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#get(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		this.post(req, resp);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see tool.CommonServlet#post(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String stringYear = req.getParameter("year");
		String stringMonth = req.getParameter("month");
		String classCd = req.getParameter("classCd");

		System.out.println(stringYear);
		System.out.println(stringMonth);
		System.out.println(classCd);

		int year = 2024;
		int month = 10;

		if ( stringYear != null && !stringYear.isEmpty() ) {
			year = Integer.parseInt(stringYear);
		}
		if ( stringMonth != null && !stringMonth.isEmpty() ) {
			month = Integer.parseInt(stringMonth);
		}
		if ( classCd == null || classCd.isEmpty() ) {
			classCd = "";
		}

		
		// 入力チェック
//		if (login.isEmpty() || password.isEmpty()) {
//			// メッセージの設定
//			req.setAttribute("errorMessage", "ユーザIDとパスワードを入力してください");
//
//			// リクエスト属性の設定
//			req.setAttribute("login", login);
//
//			// 画面遷移
//			req.getRequestDispatcher("login.jsp").forward(req, resp);
//		}
		
		// データの取得
		AttendanceManagementDAO attendanceManagementDAO = new AttendanceManagementDAO();
		AttendanceNameDAO attendanceNameDAO = new AttendanceNameDAO();

		List<Student> studentList = null;
		List<Attendance> attendanceList = null;
		List<AttendanceName> attendanceNameList = null;

		studentList = attendanceManagementDAO.getStudentsByClassCd(classCd);
		attendanceList = attendanceManagementDAO.getAttendancesByClassCd(classCd, 2024, 10);
		attendanceNameList = attendanceNameDAO.all();

		// jspに渡すためにデータを加工
		List<Date> uniqueDates = AttendanceManagementController.getUniqueDates(attendanceList);
		Map<String, Student> studentMap = AttendanceManagementController.getStudentMap(studentList);
		Map<String, AttendanceName> attendanceNameMap = AttendanceManagementController.getAttendanceNameMap(attendanceNameList);
		
		// セッション情報を取得
		// HttpSession session = req.getSession(true);

		
		// リクエストに情報を保存
		// req.setAttribute("studentList", studentList);

		req.setAttribute("year", year);
		req.setAttribute("month", month);
		req.setAttribute("classCd", classCd);

		req.setAttribute("studentMap", studentMap);
		req.setAttribute("attendanceNameMap", attendanceNameMap);
		req.setAttribute("attendanceList", attendanceList);
		req.setAttribute("uniqueDates", uniqueDates);

		// 画面遷移
		req.getRequestDispatcher("/AttendanceManagement/EditAttendance.jsp").forward(req, resp);

	}

    public static List<Date> getUniqueDates(List<Attendance> attendanceList) {
        Set<Date> uniqueDates = new TreeSet<>();
        for (Attendance attendance : attendanceList) {
            uniqueDates.add(attendance.getAtDate());
        }
        return new ArrayList<>(uniqueDates); // 日付をリストとして返す
    }
    
    public static Map<String, Student> getStudentMap(List<Student> studentList) {
        Map<String, Student> studentMap = new HashMap<>();
        studentList.forEach(student -> {
        	studentMap.put(student.getStudentID(), student); 
        	
        });
        return studentMap;
    }

    public static Map<String, AttendanceName> getAttendanceNameMap(List<AttendanceName> attendanceNameList) {
        Map<String, AttendanceName> attendanceNameMap = new HashMap<>();
        attendanceNameList.forEach(attendanceName -> {
        	attendanceNameMap.put(attendanceName.getAttendance(), attendanceName); 
        	
        });
        return attendanceNameMap;
    }
}
