package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Staff;

public class StaffDAO extends DAO {

    // 全件検索
    public List<Staff> all() throws Exception {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getString("staffID"),
                    rs.getString("password"),
                    rs.getString("name"),
                    rs.getString("kflag")
                );
                staffList.add(staff);
            }
        }
        return staffList;
    }

    // データ登録
    public void insert(Staff staff) throws Exception {
        String sql = "INSERT INTO staff (staffID, password, name, kflag) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staff.getStaffID());
            pstmt.setString(2, staff.getPassword());
            pstmt.setString(3, staff.getName());
            pstmt.setString(4, staff.getKFlag());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(Staff staff) throws Exception {
        String sql = "UPDATE staff SET password = ?, name = ?, kflag = ? WHERE staffid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staff.getPassword());
            pstmt.setString(2, staff.getName());
            pstmt.setString(3, staff.getKFlag());
            pstmt.setString(4, staff.getStaffID());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String staffID) throws Exception {
        String sql = "DELETE FROM staff WHERE staffid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staffID);
            pstmt.executeUpdate();
        }
    }
}
