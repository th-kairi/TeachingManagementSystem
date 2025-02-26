package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.AttendanceDetail;
import bean.AttendanceTuti;
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
				Student student = new Student();
				student.setStudent(
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
	public List<AttendanceDetail> getAttendancesByClassCd(String classCd, int year, int month) throws Exception {
		List<AttendanceDetail> attendanceList = new ArrayList<>();

		// 月の最初の日を取得
		LocalDate firstDay = LocalDate.of(year, month, 1);
		LocalDate lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());

		Date sqlFirstDate = Date.valueOf(firstDay);
		Date sqlLastDate = Date.valueOf(lastDay);

		String sql = "SELECT"
				+ "      tbl1.studentid, tbl1.date, tbl1.studenttel, attendance.attendance, attendance.atreason, attendance.point ,"
				+ " (SELECT "
				+ "        SUM("
				+ "            CASE "
				+ "                WHEN ATTENDANCE = '1' THEN 1"
				+ "                WHEN ATTENDANCE = '2' THEN 1/3.0"
				+ "                WHEN ATTENDANCE = '3' THEN 1/3.0"
				+ "                WHEN ATTENDANCE = '23' THEN 2/3.0"
				+ "                ELSE 0"
				+ "            END"
				+ "        ) AS ATTENDANCE_ALL_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('1', '2', '3', '23')"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        SUM("
				+ "            CASE "
				+ "                WHEN ATTENDANCE = '1' THEN 1"
				+ "                WHEN ATTENDANCE = '2' THEN 1/3.0"
				+ "                WHEN ATTENDANCE = '3' THEN 1/3.0"
				+ "                WHEN ATTENDANCE = '23' THEN 2/3.0"
				+ "                ELSE 0"
				+ "            END"
				+ "        ) AS ATTENDANCE_MONTH_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('1', '2', '3', '23')"
				+ "   AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        COUNT(*) AS ATTENDANCE_ABSENCE_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('1') AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        COUNT(*) AS ATTENDANCE_LATENESS_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('2', '23') AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        COUNT(*) AS ATTENDANCE_EARLY_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('3', '23') AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        COUNT(*) AS ATTENDANCE_OTHER_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('4') AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " ),"
				+ " (SELECT "
				+ "        COUNT(*) AS ATTENDANCE_SUSPENSION_SUM"
				+ " FROM attendance"
				+ " WHERE STUDENTID = tbl1.studentid"
				+ "   AND ATTENDANCE IN ('5') AND ATDATE >= ? AND ATDATE <= ?"
				+ " GROUP BY STUDENTID"
				+ " )"
				+ "  FROM "
				+ "      ("
				+ "          WITH datelist AS (SELECT generate_series(?, ?, INTERVAL '1 day')::date AS date)"
				+ "          SELECT "
				+ "              student.*,"
				+ "              datelist.date"
				+ "          FROM datelist"
				+ "          CROSS JOIN student"
				+ "          WHERE student.studentid IN (SELECT studentid FROM classroster WHERE classcd = ?)"
				+ "          ORDER BY"
				+ "              student.studentid,"
				+ "              datelist.date"
				+ "      ) tbl1 "
				+ "  LEFT OUTER JOIN attendance"
				+ "      ON tbl1.studentid = attendance.studentid AND tbl1.date = attendance.atdate "
				+ "  LEFT OUTER JOIN attendancename "
				+ "      ON attendance.attendance = attendancename.attendance;";

//		String sql = "SELECT"
//				+ "    tbl1.studentid, tbl1.date, attendance.attendance, attendance.atreason, attendance.point "
//				+ "FROM "
//				+ "    ("
//				+ "        WITH datelist AS (SELECT generate_series(?, ?, INTERVAL '1 day')::date AS date)"
//				+ "        SELECT "
//				+ "            student.*,"
//				+ "            datelist.date"
//				+ "        FROM datelist"
//				+ "        CROSS JOIN student"
//				+ "        WHERE student.studentid IN (SELECT studentid FROM classroster WHERE classcd = ?)"
//				+ "        ORDER BY"
//				+ "            student.studentid,"
//				+ "            datelist.date"
//				+ "    ) tbl1 "
//				+ "LEFT OUTER JOIN attendance"
//				+ "    ON tbl1.studentid = attendance.studentid AND tbl1.date = attendance.atdate "
//				+ "LEFT OUTER JOIN attendancename "
//				+ "    ON attendance.attendance = attendancename.attendance;";



		try {
			Connection conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, sqlFirstDate);
			pstmt.setDate(2, sqlLastDate);
			pstmt.setDate(3, sqlFirstDate);
			pstmt.setDate(4, sqlLastDate);
			pstmt.setDate(5, sqlFirstDate);
			pstmt.setDate(6, sqlLastDate);
			pstmt.setDate(7, sqlFirstDate);
			pstmt.setDate(8, sqlLastDate);
			pstmt.setDate(9, sqlFirstDate);
			pstmt.setDate(10, sqlLastDate);
			pstmt.setDate(11, sqlFirstDate);
			pstmt.setDate(12, sqlLastDate);
			pstmt.setDate(13, sqlFirstDate);
			pstmt.setDate(14, sqlLastDate);
			pstmt.setString(15, classCd);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				AttendanceDetail attendance = new AttendanceDetail();
				attendance.setAttendance(
						rs.getString("studentid"),
						rs.getString("attendance"),
						rs.getString("atreason"),
						rs.getDate("date"),
						rs.getBoolean("point"),
						rs.getDouble("ATTENDANCE_ALL_SUM"),
						rs.getDouble("ATTENDANCE_MONTH_SUM"),
						rs.getInt("ATTENDANCE_ABSENCE_SUM"),
						rs.getInt("ATTENDANCE_LATENESS_SUM"),
						rs.getInt("ATTENDANCE_EARLY_SUM"),
						rs.getInt("ATTENDANCE_OTHER_SUM"),
						rs.getInt("ATTENDANCE_SUSPENSION_SUM"),
						rs.getString("studenttel")
						);
				attendanceList.add(attendance);
			}
			////////////////ここから接続切る//////////////////////////////////////
			pstmt.close();
			conn.close();
			////////////////ここまで接続切る//////////////////////////////////////

		}
		catch (Exception e) {
			e.printStackTrace();  // エラーメッセージを表示する
		}

		return attendanceList;
	}

	// 通知発送状況取得
	public List<AttendanceTuti> getTuti(String classCd) throws Exception {
		List<AttendanceTuti> tutiList = new ArrayList<>(); // 通知発送状況を格納（学籍番号:通知発送状況）

		// 通知発送対象者を取得
		String sql = "SELECT"
				+"			    a.studentid,"
				+"			    CASE"
				+"			        WHEN EXISTS ("
				+"			            SELECT 1"
				+"		 	            FROM notification n"
				+"			            WHERE n.studentid = a.studentid AND n.notification = 3"
				+"			        ) THEN '再戒告済'"
				+"			        WHEN SUM("
				+"			            CASE "
				+"			                WHEN a.attendance = '1' THEN 1"
				+"			                WHEN a.attendance = '2' THEN 1/3.0"
				+"			                WHEN a.attendance = '3' THEN 1/3.0"
				+"			                WHEN a.attendance = '23' THEN 2/3.0"
				+"			                ELSE 0"
				+"			            END"
				+"			        ) >= 60 THEN '再戒告対象'"
				+"			        WHEN EXISTS ("
				+"			            SELECT 1"
				+"			            FROM notification n"
				+"			            WHERE n.studentid = a.studentid AND n.notification = 2"
				+"			        ) THEN '戒告済'"
				+"			        WHEN SUM("
				+"			            CASE "
				+"			                WHEN a.attendance = '1' THEN 1"
				+"			                WHEN a.attendance = '2' THEN 1/3.0"
				+"			                WHEN a.attendance = '3' THEN 1/3.0"
				+"			                WHEN a.attendance = '23' THEN 2/3.0"
				+"			                ELSE 0"
				+"			            END"
				+"			        ) >= 40 THEN '戒告対象'"
				+"			        WHEN EXISTS ("
				+"			            SELECT 1"
				+"			            FROM notification n"
				+"			            WHERE n.studentid = a.studentid AND n.notification = 1"
				+"			        ) THEN '訓告済'"
				+"			        WHEN SUM("
				+"			            CASE "
				+"			                WHEN a.attendance = '1' THEN 1"
				+"			                WHEN a.attendance = '2' THEN 1/3.0"
				+"			                WHEN a.attendance = '3' THEN 1/3.0"
				+"			                WHEN a.attendance = '23' THEN 2/3.0"
				+"			                ELSE 0"
				+"			            END"
				+"			        ) >= 20 THEN '訓告対象'"
				+"			        ELSE ''"
				+"			    END AS tutistatus"
				+"			FROM"
				+"			    attendance a"
				+"			WHERE"
				+"			    a.studentid IN (SELECT studentid FROM classroster WHERE classcd = ?)"
				+"			    AND a.attendance IN ('1', '2', '3', '23') "
				+"			GROUP BY"
				+"			    a.studentid;";



		try {
			Connection conn = getConnection();


			// 通知発送対象者を取得
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classCd);


			ResultSet rs = pstmt.executeQuery();


			while (rs.next()) {
				AttendanceTuti attendance = new AttendanceTuti();
				attendance.setAttendanceTuti(
						rs.getString("studentid"),
						rs.getString("tutistatus")
						);
				tutiList.add(attendance);
			}
			System.out.println(tutiList);

			////////////////ここから接続切る//////////////////////////////////////
			pstmt.close();
			conn.close();
			////////////////ここまで接続切る//////////////////////////////////////

		}
		catch (Exception e) {
			e.printStackTrace();  // エラーメッセージを表示する
		}

		return tutiList;

	}
}
