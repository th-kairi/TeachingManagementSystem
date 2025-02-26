package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;

/**
 * AttendanceのDAO
 *
 * @author admin
 *
 */
public class AttendanceDAO extends DAO {

	// 全件検索
	public List<Attendance> all() throws Exception {
		List<Attendance> attendanceList = new ArrayList<>();
		String sql = "SELECT * FROM attendance";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Attendance attendance = new Attendance();
				attendance.setAttendance(
						rs.getString("studentid"),
						rs.getString("attendance"),
						rs.getString("atreason"),
						rs.getDate("atdate"),
						rs.getBoolean("point"));
				attendanceList.add(attendance);
			}
			return attendanceList;
		}
	}

	// データ登録
	public void insert(Attendance attendance) throws Exception {
		String sql = "INSERT INTO attendance (studentid, attendance, atreason, atdate, point) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
		String sql = "UPDATE attendance SET attendance = ?, atreason = ?, point = ? WHERE studentid = ? AND atdate = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
		String sql = "DELETE FROM attendance WHERE studentid = ? AND atdate = ?";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, studentId);
			pstmt.setDate(2, new java.sql.Date(atDate.getTime()));
			pstmt.executeUpdate();
		}
	}

	public boolean getByID(String studentid, Date atdate) throws Exception {
		String sql = "SELECT * FROM attendance WHERE studentid = ? AND atdate = ?";

		try (Connection conn = getConnection();
		     PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, studentid);
			stmt.setDate(2, atdate);
			ResultSet rs = stmt.executeQuery();

			return rs.next();
		}
	}

	/**
	 * **月ごとの出席確定処理**
	 * @param baseMonth 確定する年月 (YearMonth)
	 * @param classCodes 確定するクラス一覧
	 * @return 成功したら true, 失敗したら false
	 */
	public boolean finalizeAttendance(YearMonth baseMonth, String[] classCodes) {
		String startDate = baseMonth.atDay(1).toString();
		String endDate = baseMonth.atEndOfMonth().toString();

		try (Connection conn = getConnection()) {
			for (String classCode : classCodes) {
				processFinalization(conn, classCode, baseMonth, startDate, endDate);
			}
//			conn.commit();
			return true;
		} catch (Exception e) {
//			e.printStackTrace();
			return false;
		}
	}

	private void processFinalization(Connection conn, String classCode, YearMonth baseMonth, String startDate, String endDate) throws SQLException {
	    // startDateとendDateをjava.sql.Dateに変換
	    java.sql.Date startSqlDate = java.sql.Date.valueOf(startDate);
	    java.sql.Date endSqlDate = java.sql.Date.valueOf(endDate);

		// 学生ごとの出席率を計算
		String sql = ""
	            +"SELECT"
	            +"    a.STUDENTID,"
	            +"    SUM(CASE WHEN a.ATTENDANCE = '01' THEN 1 ELSE 0 END) AS attendanceDays,"
	            +"    SUM(CASE WHEN a.ATTENDANCE = '02' THEN 1 ELSE 0 END) AS absences,"
	            +"    SUM(CASE WHEN a.ATTENDANCE = '03' THEN 1 ELSE 0 END) AS tardies,"
	            +"    SUM(CASE WHEN a.ATTENDANCE = '04' THEN 1 ELSE 0 END) AS earlyLeaves"
	            +" FROM ATTENDANCE a"
	            +" JOIN CLASSROSTER c ON a.STUDENTID = c.STUDENTID"
	            +" WHERE c.CLASSCD = ? AND a.ATDATE BETWEEN ? AND ? "
	            +" GROUP BY a.STUDENTID"
	        +"";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, classCode);
	        ps.setDate(2, startSqlDate); // startSqlDateに変更
	        ps.setDate(3, endSqlDate);   // endSqlDateに変更
			System.out.println("Executing SQL:" + ps);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String studentID = rs.getString("STUDENTID");
				int attendanceDays = rs.getInt("attendanceDays");
				int absences = rs.getInt("absences");
				int tardies = rs.getInt("tardies");
				int earlyLeaves = rs.getInt("earlyLeaves");

				// 3回で1欠席として計算
				int totalAbsences = absences + (tardies + earlyLeaves) / 3;

				// 出席率と欠席率を計算
				int totalDays = attendanceDays + totalAbsences;
				double attendanceRate = totalDays > 0 ? (double) attendanceDays / totalDays : 0;
				double absenceRate = 1 - attendanceRate;

				// データを確定テーブルに保存
				String insertSql = ""
                    +"INSERT INTO ATTENDANCERATEFINALIZED (STUDENTID, FINALIZEDMONTH, ATTENDANCERATE, ABSENCERATE, TARDINESS, EARLYLEAVE, CUMULATIVEABSENCE)"
                    +" VALUES (?, ?, ?, ?, ?, ?, ?)"
                    +" ON CONFLICT (STUDENTID, FINALIZEDMONTH) DO UPDATE SET"
                    +"    ATTENDANCERATE = EXCLUDED.ATTENDANCERATE,"
                    +"    ABSENCERATE = EXCLUDED.ABSENCERATE,"
                    +"    TARDINESS = EXCLUDED.TARDINESS,"
                    +"    EARLYLEAVE = EXCLUDED.EARLYLEAVE,"
                    +"    CUMULATIVEABSENCE = EXCLUDED.CUMULATIVEABSENCE"
                +"";


				try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
					psInsert.setString(1, studentID);
					psInsert.setString(2, baseMonth.toString());
					psInsert.setDouble(3, attendanceRate);
					psInsert.setDouble(4, absenceRate);
					psInsert.setInt(5, tardies);
					psInsert.setInt(6, earlyLeaves);
					psInsert.setInt(7, totalAbsences);

					System.out.println("Executing SQL:" + psInsert);
					psInsert.executeUpdate();
				}catch (SQLException e) {
	                System.out.println("SQL Error: " + e.getMessage());
				}
			}
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    }
	}
}
