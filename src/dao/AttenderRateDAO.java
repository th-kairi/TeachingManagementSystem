package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.AttenderRate;

/**
 * AttenderRateのデータベースアクセスを管理するDAOクラス
 */
public class AttenderRateDAO extends DAO {

    // 1. 全件検索
    public List<AttenderRate> all() throws Exception {
        List<AttenderRate> attenderRates = new ArrayList<>();
        String sql = "SELECT * FROM attender_rates";

		try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AttenderRate attenderRate = mapResultSetToAttenderRate(rs);
                attenderRates.add(attenderRate);
            }
        }

        return attenderRates;
    }

    // 2. 挿入
    public void insert(AttenderRate attenderRate) throws Exception {
        String sql = "INSERT INTO attender_rates (student_id, attendance_rate, absence_rate, tardiness, early_leave, " +
                     "cumulative_absence, fraction, other_absence, leave_of_absence) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, attenderRate.getStudentId());
            stmt.setDouble(2, attenderRate.getAttendanceRate());
            stmt.setDouble(3, attenderRate.getAbsenceRate());
            stmt.setInt(4, attenderRate.getTardiness());
            stmt.setInt(5, attenderRate.getEarlyLeave());
            stmt.setInt(6, attenderRate.getCumulativeAbsence());
            stmt.setInt(7, attenderRate.getFraction());
            stmt.setInt(8, attenderRate.getOtherAbsence());
            stmt.setInt(9, attenderRate.getLeaveOfAbsence());

            stmt.executeUpdate();
        }
    }

    // 3. 更新
    public void update(AttenderRate attenderRate) throws Exception {
        String sql = "UPDATE attender_rates SET attendance_rate = ?, absence_rate = ?, tardiness = ?, early_leave = ?, " +
                     "cumulative_absence = ?, fraction = ?, other_absence = ?, leave_of_absence = ? WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, attenderRate.getAttendanceRate());
            stmt.setDouble(2, attenderRate.getAbsenceRate());
            stmt.setInt(3, attenderRate.getTardiness());
            stmt.setInt(4, attenderRate.getEarlyLeave());
            stmt.setInt(5, attenderRate.getCumulativeAbsence());
            stmt.setInt(6, attenderRate.getFraction());
            stmt.setInt(7, attenderRate.getOtherAbsence());
            stmt.setInt(8, attenderRate.getLeaveOfAbsence());
            stmt.setString(9, attenderRate.getStudentId());

            stmt.executeUpdate();
        }
    }

    // 4. 削除
    public void delete(String studentId) throws Exception {
        String sql = "DELETE FROM attender_rates WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, studentId);
            stmt.executeUpdate();
        }
    }

    // ResultSetをAttenderRateオブジェクトにマッピング
    private AttenderRate mapResultSetToAttenderRate(ResultSet rs) throws Exception {
        AttenderRate attenderRate = new AttenderRate();
        attenderRate.setStudentId(rs.getString("student_id"));
        attenderRate.setAttendanceRate(rs.getDouble("attendance_rate"));
        attenderRate.setAbsenceRate(rs.getDouble("absence_rate"));
        attenderRate.setTardiness(rs.getInt("tardiness"));
        attenderRate.setEarlyLeave(rs.getInt("early_leave"));
        attenderRate.setCumulativeAbsence(rs.getInt("cumulative_absence"));
        attenderRate.setFraction(rs.getInt("fraction"));
        attenderRate.setOtherAbsence(rs.getInt("other_absence"));
        attenderRate.setLeaveOfAbsence(rs.getInt("leave_of_absence"));
        return attenderRate;
    }
}
