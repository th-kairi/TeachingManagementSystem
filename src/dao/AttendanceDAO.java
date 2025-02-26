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
 * AttendanceのDAOクラス
 * 出席情報のCRUD操作と、月ごとの出席確定処理を行う
 *
 * @author admin
 */
public class AttendanceDAO extends DAO {

    /**
     * 出席データの全件取得
     * @return 出席データのリスト
     * @throws Exception データベース操作エラー
     */
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
        }
        return attendanceList;
    }

    /**
     * 出席データの登録
     * @param attendance 出席データ
     * @throws Exception データベース操作エラー
     */
    public void insert(Attendance attendance) throws Exception {
        String sql = "INSERT INTO attendance (studentid, attendance, atreason, atdate, point) VALUES (?, ?, ?, ?, ?)";

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

    /**
     * 出席データの更新
     * @param attendance 更新する出席データ
     * @throws Exception データベース操作エラー
     */
    public void update(Attendance attendance) throws Exception {
        String sql = "UPDATE attendance SET attendance = ?, atreason = ?, point = ? WHERE studentid = ? AND atdate = ?";

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

    /**
     * 出席データの削除
     * @param studentId 学生ID
     * @param atDate 出席日
     * @throws Exception データベース操作エラー
     */
    public void delete(String studentId, Date atDate) throws Exception {
        String sql = "DELETE FROM attendance WHERE studentid = ? AND atdate = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setDate(2, new java.sql.Date(atDate.getTime()));
            pstmt.executeUpdate();
        }
    }

    /**
     * 出席データの存在チェック
     * @param studentid 学生ID
     * @param atdate 出席日
     * @return データが存在すればtrue、存在しなければfalse
     * @throws Exception データベース操作エラー
     */
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
     * 月ごとの出席確定処理を実行するメソッド
     * @param baseMonth 確定する年月 (YearMonth)
     * @param classCodes 確定対象のクラスコード一覧
     * @return 成功したら true, 失敗したら false
     */
    public boolean finalizeAttendance(YearMonth baseMonth, String[] classCodes) {
        // 月の初日と最終日を文字列に変換
        String startDate = baseMonth.atDay(1).toString();
        String endDate = baseMonth.atEndOfMonth().toString();

        try (Connection conn = getConnection()) {
            // 指定された各クラスコードに対して処理を実行
            for (String classCode : classCodes) {
                processFinalization(conn, classCode, baseMonth, startDate, endDate);
            }
            return true; // 処理が成功した場合は true を返す
        } catch (Exception e) {
            e.printStackTrace(); // 例外が発生した場合はエラーログを出力
            return false; // 失敗した場合は false を返す
        }
    }

    /**
     * クラスごとの出席率計算と確定データの保存を行うメソッド
     * @param conn データベース接続オブジェクト
     * @param classCode クラスコード
     * @param baseMonth 確定する年月
     * @param startDate 期間開始日
     * @param endDate 期間終了日
     * @throws SQLException SQLエラー
     */
    private void processFinalization(Connection conn, String classCode, YearMonth baseMonth, String startDate, String endDate) throws SQLException {
        // 出席データを取得するSQLクエリ
        String sql = ""
            + "SELECT a.STUDENTID, "
            + "       SUM(CASE WHEN a.ATTENDANCE = '01' THEN 1 ELSE 0 END) AS attendanceDays, "
            + "       SUM(CASE WHEN a.ATTENDANCE = '02' THEN 1 ELSE 0 END) AS absences, "
            + "       SUM(CASE WHEN a.ATTENDANCE = '03' THEN 1 ELSE 0 END) AS tardies, "
            + "       SUM(CASE WHEN a.ATTENDANCE = '04' THEN 1 ELSE 0 END) AS earlyLeaves "
            + " FROM ATTENDANCE a "
            + " JOIN CLASSROSTER c ON a.STUDENTID = c.STUDENTID "
            + " WHERE c.CLASSCD = ? AND a.ATDATE BETWEEN ? AND ? "
            + " GROUP BY a.STUDENTID";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // クエリパラメータを設定
            ps.setString(1, classCode);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();

            // 出席データを処理
            while (rs.next()) {
                String studentID = rs.getString("STUDENTID");
                int admissionYear = Integer.parseInt("20" + studentID.substring(0, 2)); // 先頭2桁を学年に変換
                int attendanceDays = rs.getInt("attendanceDays");
                int absences = rs.getInt("absences");
                int tardies = rs.getInt("tardies");
                int earlyLeaves = rs.getInt("earlyLeaves");

                // 総欠席日数を計算 (遅刻・早退を欠席として扱う)
                int totalAbsences = absences + (tardies + earlyLeaves) / 3;
                int totalDays = attendanceDays + totalAbsences;
                // 出席率と欠席率を計算
                double[] rates = calculateAttendanceRates(attendanceDays, totalDays);
                double attendanceRate = rates[0];
                double absenceRate = rates[1];

                // 1. 学生ごとの月ごとの出席率確定データを更新
                updateStudentMonthlyAttendance(conn, studentID, baseMonth.toString(), attendanceRate, absenceRate, tardies, earlyLeaves, totalAbsences);

                // 2. 学生ごとの累積出席率を更新
                updateStudentCumulativeAttendance(conn, studentID, attendanceRate, absenceRate, tardies, earlyLeaves, totalAbsences, 0, 0); // ここではその他欠席と休学は 0 と仮定
            }
        }

//        // 3. クラスごとの月ごとの確定出席率を更新
//        updateClassMonthlyAttendance(conn, classCode, baseMonth.toString());
//
//        // 4. クラスごとの累積出席率を更新
//        updateClassCumulativeAttendance(conn, classCode);
    }

    /**
     * 出席率と欠席率を計算するメソッド
     * @param attendanceDays 出席日数
     * @param totalDays 授業日数
     * @return 出席率と欠席率の配列（[出席率, 欠席率]）
     */
    private double[] calculateAttendanceRates(int attendanceDays, int totalDays) {
        double attendanceRate = 0.0;
        double absenceRate = 0.0;

        if (totalDays > 0) {
            attendanceRate = (double) attendanceDays / totalDays; // 出席率計算
            absenceRate = 1.0 - attendanceRate; // 欠席率計算
        }

        return new double[]{attendanceRate, absenceRate}; // 配列で返却
    }

    /**
     * 学生の月別出席率を更新するメソッド
     * @param conn データベース接続オブジェクト
     * @param studentID 学生番号
     * @param finalizedMonth 確定月（YYYY-MM形式）
     * @param attendanceRate 出席率
     * @param absenceRate 欠席率
     * @param tardies 遅刻数
     * @param earlyLeaves 早退数
     * @param cumulativeAbsence 累積欠席数
     * @throws SQLException SQLエラー
     */
    private void updateStudentMonthlyAttendance(Connection conn, String studentID, String finalizedMonth,
                                                 double attendanceRate, double absenceRate,
                                                 int tardies, int earlyLeaves, int cumulativeAbsence) throws SQLException {

        String insertSql = ""
            + "INSERT INTO ATTENDANCERATEFINALIZED (STUDENTID, FINALIZEDMONTH, ATTENDANCERATE, ABSENCERATE, "
            + "TARDINESS, EARLYLEAVE, CUMULATIVEABSENCE) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?) "
            + "ON CONFLICT (STUDENTID, FINALIZEDMONTH) DO UPDATE SET "
            + "ATTENDANCERATE = EXCLUDED.ATTENDANCERATE, "
            + "ABSENCERATE = EXCLUDED.ABSENCERATE, "
            + "TARDINESS = EXCLUDED.TARDINESS, "
            + "EARLYLEAVE = EXCLUDED.EARLYLEAVE, "
            + "CUMULATIVEABSENCE = EXCLUDED.CUMULATIVEABSENCE";

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, studentID);
            ps.setString(2, finalizedMonth);
            ps.setDouble(3, attendanceRate);
            ps.setDouble(4, absenceRate);
            ps.setInt(5, tardies);
            ps.setInt(6, earlyLeaves);
            ps.setInt(7, cumulativeAbsence);
            ps.executeUpdate();
        }
    }

    /**
     * 学生の累積出席率を更新するメソッド
     * @param conn データベース接続オブジェクト
     * @param studentID 学生番号
     * @param attendanceRate 出席率
     * @param absenceRate 欠席率
     * @param tardies 遅刻数
     * @param earlyLeaves 早退数
     * @param cumulativeAbsence 累積欠席数
     * @param otherAbsence その他欠席数
     * @param leaveOfAbsence 休学数
     * @throws SQLException SQLエラー
     */
    private void updateStudentCumulativeAttendance(Connection conn, String studentID, double attendanceRate,
                                                   double absenceRate, int tardies, int earlyLeaves,
                                                   int cumulativeAbsence, int otherAbsence, int leaveOfAbsence) throws SQLException {

        String insertSql = ""
            + "INSERT INTO ATTENDANCERATE (STUDENTID, ATTENDANCERATE, ABSENCERATE, TARDINESS, EARLYLEAVE, "
            + "CUMULATIVEABSENCE, OTHERABSENCE, LEAVEOFABSENCE) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
            + "ON CONFLICT (STUDENTID) DO UPDATE SET "
            + "ATTENDANCERATE = EXCLUDED.ATTENDANCERATE, "
            + "ABSENCERATE = EXCLUDED.ABSENCERATE, "
            + "TARDINESS = EXCLUDED.TARDINESS, "
            + "EARLYLEAVE = EXCLUDED.EARLYLEAVE, "
            + "CUMULATIVEABSENCE = EXCLUDED.CUMULATIVEABSENCE, "
            + "OTHERABSENCE = EXCLUDED.OTHERABSENCE, "
            + "LEAVEOFABSENCE = EXCLUDED.LEAVEOFABSENCE";

        try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setString(1, studentID);
            ps.setDouble(2, attendanceRate);
            ps.setDouble(3, absenceRate);
            ps.setInt(4, tardies);
            ps.setInt(5, earlyLeaves);
            ps.setInt(6, cumulativeAbsence);
            ps.setInt(7, otherAbsence);
            ps.setInt(8, leaveOfAbsence);
            ps.executeUpdate();
        }
    }


}
