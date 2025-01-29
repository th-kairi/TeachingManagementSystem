package attendanceManagement;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Attendance;
import bean.AttendanceDetail;
import bean.AttendanceName;
import bean.AttendanceTuti;
import bean.Student;
import dao.AttendanceDAO;
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
		// ポップアップ画面で確定ボタンを押した場合ここから ////////////////////////////////////////////////
		String syusseki = req.getParameter("syusseki");
		if (syusseki != null) {
				String reason = req.getParameter("reason");
 				String studentid = req.getParameter("studentid");
				String atdate = req.getParameter("atdate");
				Date sqlDate= Date.valueOf(atdate);

				AttendanceDAO attendanceDAO = new AttendanceDAO();
				boolean getByIdReturn = attendanceDAO.getByID(studentid,sqlDate);
				System.out.println(getByIdReturn);

				if (getByIdReturn == true) {
					//ATTENDANCEテーブルの更新
					Attendance attendanceupdate = new Attendance();
					attendanceupdate.setAttendance(studentid,syusseki,reason,sqlDate,false);
					attendanceDAO.update(attendanceupdate);
				} else {
					//ATTENDANCEテーブルの挿入
					// new Attendance("学生番号","出席状況","出欠理由",日付,出席確定フラグ);　new Attendance("2425006","1","理由渡辺",sqlDate,false);
					Attendance attendanceinsert = new Attendance();
					attendanceinsert.setAttendance(studentid,syusseki,reason,sqlDate,false);
					attendanceDAO.insert(attendanceinsert);
				}
		}
		// ポップアップ画面で確定ボタンを押した場合ここまで ////////////////////////////////////////////////

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
		List<AttendanceDetail> attendanceList = null;
		//////////////ここから通知対象取得処理////////////////////////////////////////////////
		List<AttendanceTuti> attendanceTutiList = null;
		//////////////ここまで通知対象取得処理////////////////////////////////////////////////
		List<AttendanceName> attendanceNameList = null;

		studentList = attendanceManagementDAO.getStudentsByClassCd(classCd);
		attendanceList = attendanceManagementDAO.getAttendancesByClassCd(classCd, year, month);
		//////////////ここから通知対象取得処理////////////////////////////////////////////////
		attendanceTutiList = attendanceManagementDAO.getTuti(classCd);
		//////////////ここまで通知対象取得処理////////////////////////////////////////////////
		attendanceNameList = attendanceNameDAO.all();

		// jspに渡すためにデータを加工
		// 該当月の日付を1日から月末までリストで持つ
		List<Date> uniqueDates = AttendanceManagementController.getUniqueDates(attendanceList);
		// 学生情報を表示できるようMapで持つ
		Map<String, Student> studentMap = AttendanceManagementController.getStudentMap(studentList);
		// 出欠名を表示できるようMapで持つ
		Map<String, AttendanceName> attendanceNameMap = AttendanceManagementController.getAttendanceNameMap(attendanceNameList);
		//////////////ここから通知対象取得処理////////////////////////////////////////////////
		Map<String, String> studentTutiMap = AttendanceManagementController.getTutiMap(attendanceTutiList);;
		//////////////ここまで通知対象取得処理////////////////////////////////////////////////

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
		//////////////ここから通知対象取得処理////////////////////////////////////////////////
		// 通知状況
		// req.setAttribute("attendanceTutiList", attendanceTutiList);
		req.setAttribute("attendanceTutiList", studentTutiMap);
		//////////////ここまで通知対象取得処理////////////////////////////////////////////////

		// 画面遷移
		req.getRequestDispatcher("/AttendanceManagement/EditAttendance.jsp").forward(req, resp);
	}

    public static List<Date> getUniqueDates(List<AttendanceDetail> attendanceList) {
        Set<Date> uniqueDates = new TreeSet<>();
        for (AttendanceDetail attendance : attendanceList) {
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
    //////////////ここから通知対象取得処理////////////////////////////////////////////////
    public static Map<String, String> getTutiMap(List<AttendanceTuti> studentList) {
        Map<String, String> AttendanceTutiMap = new HashMap<>();
        studentList.forEach(AttendanceTuti -> {
        	AttendanceTutiMap.put(AttendanceTuti.getStudentID(), AttendanceTuti.getTutiStatus());

        });
        return AttendanceTutiMap;
    }
    //////////////ここまで通知対象取得処理////////////////////////////////////////////////
}
