package attendanceManagement;

import java.time.LocalDate;
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
		String stringYearMonth = req.getParameter("yearmonth");
		String classCd = req.getParameter("classCd");

		// 年月の値が不正な場合は今日の年月にしておく
		LocalDate localdate = LocalDate.now();
		int year;
		int month;

		// 入力チェック
		if ( stringYearMonth != null && !stringYearMonth.isEmpty() ) {
			// 入力あり
			year = Integer.parseInt( stringYearMonth.split("-")[0] );
			month = Integer.parseInt( stringYearMonth.split("-")[1] );
		} else {
			// 未入力
			year = 	localdate.getYear();
			month = localdate.getMonthValue();
		}

		if ( classCd == null || classCd.isEmpty() ) {
			// 未入力
			classCd = "";
		} else {
			// 入力あり
		}

		
		System.out.println("year:"+year);
		System.out.println("month:"+month);
		System.out.println("classCd:"+classCd);
		// データの取得
		AttendanceManagementDAO attendanceManagementDAO = new AttendanceManagementDAO();
		AttendanceNameDAO attendanceNameDAO = new AttendanceNameDAO();

		List<Student> studentList = null;
		List<Attendance> attendanceList = null;
		List<AttendanceName> attendanceNameList = null;

		studentList = attendanceManagementDAO.getStudentsByClassCd(classCd);
		attendanceList = attendanceManagementDAO.getAttendancesByClassCd(classCd, year, month);
		attendanceNameList = attendanceNameDAO.all();

		// jspに渡すためにデータを加工
		// 該当月の日付を1日から月末までリストで持つ
		List<Date> uniqueDates = AttendanceManagementController.getUniqueDates(attendanceList);
		// 学生情報を表示できるようMapで持つ
		Map<String, Student> studentMap = AttendanceManagementController.getStudentMap(studentList);
		// 出欠名を表示できるようMapで持つ
		Map<String, AttendanceName> attendanceNameMap = AttendanceManagementController.getAttendanceNameMap(attendanceNameList);
		
		// jsp側に渡すデータ
		// 年月
		req.setAttribute("yearmonth", stringYearMonth);
		// 組
		req.setAttribute("classCd", classCd);
		// 学生情報 … Mapで持つ
		req.setAttribute("studentMap", studentMap);
		// 出欠名 … Mapで持つ
		req.setAttribute("attendanceNameMap", attendanceNameMap);
		// 出欠情報
		req.setAttribute("attendanceList", attendanceList);
		// 該当月の日付リスト
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
