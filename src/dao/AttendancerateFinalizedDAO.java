package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AttendancerateFinalizedDAO extends DAO {

    // クラス単位で出席状況を更新するメソッド（複数クラス対応）
    public boolean updateAttendanceRatesByClasses(String[] classcds, String targetMonthStr) {
        boolean isSuccess = true;
        System.out.println("updateAttendanceRatesByClasses");

        for (String classcd : classcds) {
        	System.out.println("classcd:" + classcd);
            if (!updateAttendanceRateByClass(classcd, targetMonthStr)) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    // クラス単位で出席状況を更新するメソッド
    public boolean updateAttendanceRateByClass(String classcd, String targetMonthStr) {
        YearMonth targetMonth = YearMonth.parse(targetMonthStr);  // 引数として受け取った年月を解析
        try (Connection connection = getConnection()) {
            // トランザクションを開始
            connection.setAutoCommit(false);

            // 学生IDリストを取得
            List<String> studentIds = getStudentIdsByClass(connection, classcd);

            // 出席状況の集計
            int totalAttendance = 0;
            int totalAbsence = 0;
            int totalLate = 0;
            int totalEarlyLeave = 0;

            // 各学生の出席状況を更新し、集計
            for (String studentId : studentIds) {
                int[] attendanceStats = updateAttendanceStatusForStudent(connection, studentId, targetMonth);
                totalAttendance += attendanceStats[0];
                totalAbsence += attendanceStats[1];
                totalLate += attendanceStats[2];
                totalEarlyLeave += attendanceStats[3];
            }

            // 遅刻と早退の合算を欠席に加算
            double totalAbsenceAdjusted = (totalLate + totalEarlyLeave) / 3.0 + totalAbsence;

            // 出席率と欠席率の計算
            double attendanceRate = totalAttendance == 0 ? 0 : (double) (totalAttendance - totalAbsenceAdjusted) / totalAttendance;
            double absenceRate = totalAttendance == 0 ? 0 : totalAbsenceAdjusted / totalAttendance;

            // ATTENDANCERATEBYCLASSFINALIZED テーブルを更新（存在しなければ作成）
            updateClassAttendanceRate(connection, classcd, attendanceRate, absenceRate, targetMonth);

            // コミット
            connection.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 学生単位での出席状況更新
    private int[] updateAttendanceStatusForStudent(Connection connection, String studentId, YearMonth targetMonth) throws SQLException {
        // 学生の入学年月日を計算
        LocalDate enrollmentDate = calculateEnrollmentDate(studentId);
        LocalDate currentDate = LocalDate.now();
        long daysSinceEnrollment = ChronoUnit.DAYS.between(enrollmentDate, currentDate);

        // 出席情報を取得
        List<int[]> attendanceRecords = getAttendanceRecords(connection, studentId, targetMonth);

        // 出席日数、遅刻数、早退数、欠席数を集計
        int attendanceDays = 0;
        int lateDays = 0;
        int earlyLeaveDays = 0;
        int absenceDays = 0;

        for (int[] record : attendanceRecords) {
            int attendanceType = record[0];
            int count = record[1];

            switch (attendanceType) {
                case 1: absenceDays += count; break;  // 欠席
                case 2: lateDays += count; break;    // 遅刻
                case 3: earlyLeaveDays += count; break;  // 早退
                case 23:
                    lateDays += count;
                    earlyLeaveDays += count;
                    break;  // 遅刻+早退
                default: attendanceDays += count;  // 出席日数
            }
        }

        // 遅刻と早退の合計を計算して3で割った整数部分を欠席日数に加算
        int tardyAndLeaveDays = lateDays + earlyLeaveDays;
        int oneThirdAbsenceDays = tardyAndLeaveDays / 3;  // 小数点以下を切り捨てた整数部分

        // 出席日数の計算
        attendanceDays = (int) (daysSinceEnrollment - absenceDays);

        // 欠席日数の調整：遅刻と早退の合算を3で割った分だけ欠席に加算
        absenceDays += oneThirdAbsenceDays;  // 遅刻・早退の日数を3で割った整数部分は欠席に含む

        // 出席率と欠席率を計算
        double attendanceRate = attendanceDays == 0 ? 0 : (attendanceDays - absenceDays) / (double) attendanceDays;
        double absenceRate = attendanceDays == 0 ? 0 : absenceDays / (double) attendanceDays;

        // ATTENDANCERATEFINALIZED テーブルを更新（存在しなければ作成）
        updateStudentAttendanceRate(connection, studentId, attendanceRate, absenceRate, lateDays, earlyLeaveDays, absenceDays, oneThirdAbsenceDays, oneThirdAbsenceDays, oneThirdAbsenceDays, targetMonth);

        return new int[]{attendanceDays, absenceDays, lateDays, earlyLeaveDays};
    }

    // 出席情報をデータベースから取得するメソッド（変更後、targetMonthに対応）
    private List<int[]> getAttendanceRecords(Connection connection, String studentId, YearMonth targetMonth) throws SQLException {
        String query = "SELECT attendance, COUNT(*) AS count "
                + "FROM attendance "
                + "WHERE studentid = ? AND atdate <= ? "
                + "GROUP BY attendance";

        List<int[]> attendanceList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setDate(2, java.sql.Date.valueOf(targetMonth.atEndOfMonth()));  // targetMonthの月末日を設定
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attendanceList.add(new int[]{rs.getInt("attendance"), rs.getInt("count")});
            }
        }
        return attendanceList;
    }

    // クラスコードから学生IDリストを取得するメソッド
    private List<String> getStudentIdsByClass(Connection connection, String classcd) throws SQLException {
        String query =
            "SELECT studentid "
            + "FROM classroster "
            + "WHERE classcd = ?";

        List<String> studentIds = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, classcd);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                studentIds.add(rs.getString("studentid"));
            }
        }
        return studentIds;
    }

    private void updateClassAttendanceRate(Connection connection, String classcd, double attendanceRate, double absenceRate, YearMonth targetMonth) throws SQLException {
		// 学籍番号から入学年度を算出する
    	List<String> studentIds = getStudentIdsByClass(connection, classcd);
        int admissionYear = calculateAdmissionYearFromStudentIds(studentIds);

    	// ATTENDANCERATEBYCLASSFINALIZED テーブルを更新（存在しなければ作成）
        String selectQuery = "SELECT COUNT(*) FROM ATTENDANCERATEBYCLASSFINALIZED WHERE CLASSCD = ? AND FINALIZEDMONTH = ?";
        try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
            selectStmt.setString(1, classcd);
            selectStmt.setString(2, targetMonth.toString());  // YearMonthを文字列に変換
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // 既にレコードがある場合は UPDATE
                String updateQuery =
                    "UPDATE ATTENDANCERATEBYCLASSFINALIZED SET ATTENDANCERATE = ?, ABSENCERATE = ? WHERE CLASSCD = ? AND FINALIZEDMONTH = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, attendanceRate);
                    updateStmt.setDouble(2, absenceRate);
                    updateStmt.setString(3, classcd);
                    updateStmt.setString(4, targetMonth.toString());
                    updateStmt.executeUpdate();
                }
            } else {
                // レコードがない場合は INSERT
                String insertQuery =
                    "INSERT INTO ATTENDANCERATEBYCLASSFINALIZED (CLASSCD, ATTENDANCERATE, ABSENCERATE, FINALIZEDMONTH, ADMISSIONYEAR) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, classcd);
                    insertStmt.setDouble(2, attendanceRate);
                    insertStmt.setDouble(3, absenceRate);
                    insertStmt.setString(4, targetMonth.toString());
                    insertStmt.setInt(5, admissionYear);
                    insertStmt.executeUpdate();

                }
            }
        }
    }

    // ATTENDANCERATE テーブルを更新（存在しなければ作成）
    private void updateStudentAttendanceRate(Connection connection, String studentId, double attendanceRate, double absenceRate, int tardiness, int earlyLeave, int cumulativeAbsence, int fraction, int otherAbsence, int leaveOfAbsence, YearMonth targetMonth) throws SQLException {
        String selectQuery =
            "SELECT COUNT(*) FROM ATTENDANCERATEFINALIZED WHERE STUDENTID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(selectQuery)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                // レコードが存在しない場合はINSERT
                String insertQuery =
                    "INSERT INTO ATTENDANCERATEFINALIZED (STUDENTID,FINALIZEDMONTH, ATTENDANCERATE, ABSENCERATE, "
                    + "TARDINESS, EARLYLEAVE, CUMULATIVEABSENCE, FRACTION, OTHERABSENCE, LEAVEOFABSENCE) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    insertStmt.setString(1, studentId);
                    insertStmt.setString(2, targetMonth.toString());
                    insertStmt.setDouble(3, attendanceRate);
                    insertStmt.setDouble(4, absenceRate);
                    insertStmt.setInt(5, tardiness);
                    insertStmt.setInt(6, earlyLeave);
                    insertStmt.setInt(7, cumulativeAbsence);
                    insertStmt.setInt(8, fraction);
                    insertStmt.setInt(9, otherAbsence);
                    insertStmt.setInt(10, leaveOfAbsence);
                    insertStmt.executeUpdate();
                }
            } else {
                // レコードが存在する場合はUPDATE
                String updateQuery =
                    "UPDATE ATTENDANCERATEFINALIZED SET "
                    + "ATTENDANCERATE = ?, ABSENCERATE = ?, "
                    + "TARDINESS = ?, EARLYLEAVE = ?, "
                    + "CUMULATIVEABSENCE = ?, FRACTION = ?, "
                    + "OTHERABSENCE = ?, LEAVEOFABSENCE = ? "
                    + "WHERE STUDENTID = ?";
                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setDouble(1, attendanceRate);
                    updateStmt.setDouble(2, absenceRate);
                    updateStmt.setInt(3, tardiness);
                    updateStmt.setInt(4, earlyLeave);
                    updateStmt.setInt(5, cumulativeAbsence);
                    updateStmt.setInt(6, fraction);
                    updateStmt.setInt(7, otherAbsence);
                    updateStmt.setInt(8, leaveOfAbsence);
                    updateStmt.setString(9, studentId);
                    updateStmt.executeUpdate();
                }
            }
        }
    }

    // 学生IDから入学年を計算するメソッド
    private int calculateAdmissionYearFromStudentIds(List<String> studentIds) {
        if (studentIds.isEmpty()) {
            throw new IllegalArgumentException("学生IDリストが空です");
        }

        String studentId = studentIds.get(0); // 最初の学生IDを取得
        String yearPrefix = studentId.substring(0, 2); // 学生IDの最初の2桁を取得
        return 2000 + Integer.parseInt(yearPrefix); // 2000年以降の西暦に変換
    }

    // 入学年月日を計算するメソッド
    private LocalDate calculateEnrollmentDate(String studentId) {
        int year = 2000 + Integer.parseInt(studentId.substring(0, 2));
        return LocalDate.of(year, 4, 1);
    }

	public boolean finalizeMonthlyAttendanceRates(String baseDateStr, String[] classCodes) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
