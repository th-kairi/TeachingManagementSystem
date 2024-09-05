package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.AttendanceName;

public class AttendanceNameDAO extends DAO {

    // 全件検索
    public List<AttendanceName> all() throws Exception {
        List<AttendanceName> attendanceNameList = new ArrayList<>();
        String sql = "SELECT * FROM attendancename";

        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AttendanceName attendanceName = new AttendanceName(
                    rs.getString("attendance"),
                    rs.getString("attendancename")
                );
                attendanceNameList.add(attendanceName);
            }
        }
        return attendanceNameList;
    }

    // データ登録
    public void insert(AttendanceName attendanceName) throws Exception {
        String sql = "INSERT INTO attendancename (attendance, attendancename) VALUES (?, ?)";

        try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendanceName.getAttendance());
            pstmt.setString(2, attendanceName.getAttendanceName());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(AttendanceName attendanceName) throws Exception {
        String sql = "UPDATE attendancename SET attendancename = ? WHERE attendance = ?";

        try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendanceName.getAttendanceName());
            pstmt.setString(2, attendanceName.getAttendance());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String attendance) throws Exception {
        String sql = "DELETE FROM attendancename WHERE attendance = ?";

        try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, attendance);
            pstmt.executeUpdate();
        }
    }
}