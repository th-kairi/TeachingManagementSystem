package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;

/**
 *
 * AttendanceのDAO
 *
 * @author admin
 *
 */
public class AttendanceDAO extends DAO {

	// 全件検索
	public List<Attendance> all() throws Exception {
		List<Attendance> attendanceList = new ArrayList<>();
		String sql = "SELECT * FROM public.attendance";

		// リソースの解放:
		// try-with-resources構文を使用して、StatementやPreparedStatement、ResultSetなどのリソースを自動的に閉じる
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				Attendance attendance = new Attendance(rs.getString("studentid"), rs.getString("attendance"),
						rs.getString("atreason"), rs.getDate("atdate"), rs.getBoolean("point"));
				attendanceList.add(attendance);
			}
		}

		return attendanceList;
	}

	// データ登録
	public void insert(Attendance attendance) throws Exception {
		String sql = "INSERT INTO public.attendance (studentid, attendance, atreason, atdate, point) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, attendance.getStudentID());
			pstmt.setString(2, attendance.getAttendance());
			pstmt.setString(3, attendance.getAtReason());
			pstmt.setDate(4, new java.sql.Date(attendance.getAtDate().getTime()));
			pstmt.setBoolean(5, attendance.isPoint());
			pstmt.executeUpdate();
		}
	}

	// データ更新
	public void update(Attendance attendance) throws Exception {
		String sql = "UPDATE public.attendance SET attendance = ?, atreason = ?, point = ? WHERE studentid = ? AND atdate = ?";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, attendance.getAttendance());
			pstmt.setString(2, attendance.getAtReason());
			pstmt.setBoolean(3, attendance.isPoint());
			pstmt.setString(4, attendance.getStudentID());
			pstmt.setDate(5, new java.sql.Date(attendance.getAtDate().getTime()));
			pstmt.executeUpdate();
		}
	}

	// データ削除
	public void delete(String studentId, Date atDate) throws Exception {
		String sql = "DELETE FROM public.attendance WHERE studentid = ? AND atdate = ?";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			pstmt.setDate(2, new java.sql.Date(atDate.getTime()));
			pstmt.executeUpdate();
		}
	}
}
