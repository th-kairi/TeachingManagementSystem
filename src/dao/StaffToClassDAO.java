package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.StaffToClass;

public class StaffToClassDAO extends DAO {

    // 全件検索
    public List<StaffToClass> all() throws Exception {
        List<StaffToClass> staffToClassList = new ArrayList<>();
        String sql = "SELECT * FROM stafftoclass";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StaffToClass staffToClass = new StaffToClass(
                    rs.getString("staffID"),
                    rs.getString("classCD")
                );
                staffToClassList.add(staffToClass);
            }
        }
        return staffToClassList;
    }

    // データ登録
    public void insert(StaffToClass staffToClass) throws Exception {
        String sql = "INSERT INTO stafftoclass (staffID, classcd) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staffToClass.getStaffID());
            pstmt.setString(2, staffToClass.getClassCD());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String staffID, String classCD) throws Exception {
        String sql = "DELETE FROM stafftoclass WHERE staffid = ? AND classcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, staffID);
            pstmt.setString(2, classCD);
            pstmt.executeUpdate();
        }
    }
}
