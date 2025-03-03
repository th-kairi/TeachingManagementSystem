package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;

/**
 * AttendanceDAO: 出席情報を管理するDAOクラス
 */
public class AttendanceDAO extends DAO {

    /**
     * 出席データの全件取得
     * @return attendanceList 出席データのリスト
     * @throws Exception 例外が発生した場合
     */
    public List<Attendance> all() throws Exception {
        List<Attendance> attendanceList = new ArrayList<>();

        // 出席データを取得するSQL文
        String sql = "SELECT studentid, attendance, atreason, atdate, point FROM attendance";

        try (Connection conn = getConnection(); // コネクションを取得
             PreparedStatement stmt = conn.prepareStatement(sql); // ステートメントを作成
             ResultSet rs = stmt.executeQuery()) { // クエリを実行し、結果セットを取得

            // 結果セットから出席データをリストに追加
            while (rs.next()) {
                Attendance attendance = new Attendance();
                // 出席情報をAttendanceオブジェクトに設定
                attendance.setAttendance(
                        rs.getString("studentid"),
                        rs.getString("attendance"),
                        rs.getString("atreason"),
                        rs.getDate("atdate"),
                        rs.getBoolean("point"));
                attendanceList.add(attendance); // リストに追加
            }
        }
        return attendanceList; // 出席データのリストを返す
    }

    /**
     * 出席データの新規登録
     * @param attendance 登録する出席情報
     * @return 成功した場合はtrue、失敗した場合はfalse
     * @throws Exception
     */
    public boolean registerAttendance(Attendance attendance) throws Exception {
        // 新規登録用のSQL文
        String sql = "INSERT INTO attendance (studentid, attendance, atreason, atdate, point) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); // コネクションを取得
             PreparedStatement ps = conn.prepareStatement(sql)) { // ステートメントを作成
            // SQLパラメータに値をセット
            ps.setString(1, attendance.getStudentID());
            ps.setString(2, attendance.getAttendance());
            ps.setString(3, attendance.getAtReason());
            ps.setDate(4, attendance.getAtDate());
            ps.setBoolean(5, attendance.isPoint());

            // SQLを実行し、更新件数を取得
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // 成功した場合はtrue、失敗した場合はfalse
        }
    }

    /**
     * 出席データの変更
     * @param attendance 更新する出席情報
     * @return 成功した場合はtrue、失敗した場合はfalse
     * @throws Exception
     */
    public boolean updateAttendance(Attendance attendance) throws Exception {
        // 出席データ更新用のSQL文
        String sql = "UPDATE attendance SET attendance = ?, atreason = ?, atdate = ?, point = ? "
                   + "WHERE studentid = ?";

        try (Connection conn = getConnection(); // コネクションを取得
             PreparedStatement ps = conn.prepareStatement(sql)) { // ステートメントを作成
            // SQLパラメータに値をセット
            ps.setString(1, attendance.getAttendance());
            ps.setString(2, attendance.getAtReason());
            ps.setDate(3, attendance.getAtDate());
            ps.setBoolean(4, attendance.isPoint());
            ps.setString(5, attendance.getStudentID());

            // SQLを実行し、更新件数を取得
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // 成功した場合はtrue、失敗した場合はfalse
        }
    }

    /**
     * 出席データの削除
     * @param studentId 削除する出席情報の学生ID
     * @return 成功した場合はtrue、失敗した場合はfalse
     * @throws Exception
     */
    public boolean deleteAttendance(String studentId) throws Exception {
        // 出席データ削除用のSQL文
        String sql = "DELETE FROM attendance WHERE studentid = ?";

        try (Connection conn = getConnection(); // コネクションを取得
             PreparedStatement ps = conn.prepareStatement(sql)) { // ステートメントを作成
            // SQLパラメータに学生IDをセット
            ps.setString(1, studentId);

            // SQLを実行し、削除件数を取得
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // 成功した場合はtrue、失敗した場合はfalse
        }
    }
    public boolean getByID(String studentid, Date sqlDate) throws Exception {
        String sql = "SELECT COUNT(*) FROM attendance WHERE studentid = ? AND atdate = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentid);
            ps.setDate(2, sqlDate);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // 該当レコードがあればtrue
                }
            }
        }
        return false; // 存在しなければfalse
    }

    public boolean update(Attendance attendance) throws Exception {
        String sql = "UPDATE attendance SET attendance = ?, atreason = ?, point = ? WHERE studentid = ? AND atdate = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attendance.getAttendance());
            ps.setString(2, attendance.getAtReason());
            ps.setBoolean(3, attendance.isPoint());
            ps.setString(4, attendance.getStudentID());
            ps.setDate(5, attendance.getAtDate());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean insert(Attendance attendance) throws Exception {
        String sql = "INSERT INTO attendance (studentid, attendance, atreason, atdate, point) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, attendance.getStudentID());
            ps.setString(2, attendance.getAttendance());
            ps.setString(3, attendance.getAtReason());
            ps.setDate(4, attendance.getAtDate());
            ps.setBoolean(5, attendance.isPoint());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }


}
