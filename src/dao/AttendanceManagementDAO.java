package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;
import bean.Student;

public class AttendanceManagementDAO extends DAO {

	// クラスコードで学生を取得
	public List<Student> getStudentsByClassCd(String classCd) throws Exception {
		List<Student> studentList = new ArrayList<>();
		String sql = "SELECT student.*\r\n"
				+ "FROM classroster\r\n"
				+ "INNER JOIN student\r\n"
				+ "ON classroster.studentid = student.studentid\r\n"
				+ "WHERE classroster.classcd = ?;";

		try (Connection conn = getConnection()) {

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classCd);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Student student = new Student(
						rs.getString("studentid"),
						rs.getString("name"),
						rs.getString("sex"),
						rs.getString("studenttel"),
						rs.getString("parenttel"),
						rs.getBoolean("dropflag")
						);
				studentList.add(student);
			}
		}

//		studentList.forEach(
//				student -> System.out.println(student.getStudentID() + ":" + student.getName())
//				);
		return studentList;
	}

	// クラスコードで学生を取得を取得し、該当月の出欠状況を返す
	public List<Attendance> getAttendancesByClassCd(String classCd, int year, int month) throws Exception {
		List<Attendance> attendanceList = new ArrayList<>();

		// yとmを元にLocalDateを作成
		String y = "2024";
		String m = "10";

		// 月の最初の日を取得
		LocalDate firstDay = LocalDate.of(year, month, 1);
		LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

		Date sqlFirstDate = Date.valueOf(firstDay);
		Date sqlLastDate = Date.valueOf(lastDay);
		System.out.println(sqlFirstDate);

		String sql = "SELECT"
				+ "    tbl1.studentid, tbl1.date, attendance.attendance, attendance.atreason, attendance.point "
				+ "FROM "
				+ "    ("
				+ "        WITH datelist AS (SELECT generate_series(?, ?, INTERVAL '1 day')::date AS date)"
				+ "        SELECT "
				+ "            student.*,"
				+ "            datelist.date"
				+ "        FROM datelist"
				+ "        CROSS JOIN student"
				+ "        WHERE student.studentid IN (SELECT studentid FROM classroster WHERE classcd = ?)"
				+ "        ORDER BY"
				+ "            student.studentid,"
				+ "            datelist.date"
				+ "    ) tbl1 "
				+ "LEFT OUTER JOIN attendance"
				+ "    ON tbl1.studentid = attendance.studentid AND tbl1.date = attendance.atdate "
				+ "LEFT OUTER JOIN attendancename "
				+ "    ON attendance.attendance = attendancename.attendance;";
		try {
			Connection conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, sqlFirstDate);
			pstmt.setDate(2, sqlLastDate);
			pstmt.setString(3, classCd);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Attendance attendance = new Attendance(
						rs.getString("studentid"),
						rs.getString("attendance"),
						rs.getString("atreason"),
						rs.getDate("date"),
						rs.getBoolean("point")
						);
				attendanceList.add(attendance);
			}


//			attendanceList.forEach(
//					attendance -> System.out.println(attendance.getStudentID() + ":" + attendance.getAtDate() + ":" + attendance.getAttendance())
//					);
		}
		catch (Exception e) {
			e.printStackTrace();  // エラーメッセージを表示する
		}

		return attendanceList;
	}

}
