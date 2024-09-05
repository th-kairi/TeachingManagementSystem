package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.NotificationName;

public class NotificationNameDAO extends DAO {

    // 全件検索
    public List<NotificationName> all() throws Exception {
        List<NotificationName> notificationNameList = new ArrayList<>();
        String sql = "SELECT * FROM notificationname";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NotificationName notificationName = new NotificationName(
                    rs.getInt("notification"),
                    rs.getString("notificationname")
                );
                notificationNameList.add(notificationName);
            }
        }
        return notificationNameList;
    }

    // データ登録
    public void insert(NotificationName notificationName) throws Exception {
        String sql = "INSERT INTO notificationname (notification, notificationname) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, notificationName.getNotification());
            pstmt.setString(2, notificationName.getNotificationName());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(NotificationName notificationName) throws Exception {
        String sql = "UPDATE notificationname SET notificationname = ? WHERE notification = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, notificationName.getNotificationName());
            pstmt.setInt(2, notificationName.getNotification());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(int notification) throws Exception {
        String sql = "DELETE FROM notificationname WHERE notification = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, notification);
            pstmt.executeUpdate();
        }
    }
}
