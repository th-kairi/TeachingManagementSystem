package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


	public Staff getByID(String staffId) throws Exception {
		Staff staff = null;

		Connection connection;
		try {
			connection = getConnection();
			
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		

		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from staff where staffid=?");

			statement.setString(1, staffId);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
                staff = new Staff(
                		resultSet.getString("staffID"),
                		resultSet.getString("password"),
                		resultSet.getString("name"),
                		resultSet.getString("kflag")
                    );
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスはnullのまま

			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return staff;
	}


	public Staff getLoginUser(String staffID, String password) throws Exception {
		Staff staff = getByID(staffID);
		// 教員がnullまたはパスワードが一致しない場合
		if (staff == null || !staff.getPassword().equals(password)) {
			return null;
		}
		return staff;
	}
}
