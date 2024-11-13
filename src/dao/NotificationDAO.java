package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Notification;

public class NotificationDAO extends DAO {

    // 全件検索
    public List<Notification> all() throws Exception {
        List<Notification> notificationList = new ArrayList<>();
        String sql = "SELECT * FROM public.notification";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotification(
                    rs.getString("studentid"),
                    rs.getInt("notification"),
                    rs.getDate("ndate")
                );
                notificationList.add(notification);
            }
        }
        return notificationList;
    }

    // データ登録
    public void insert(Notification notification) throws Exception {
        String sql = "INSERT INTO public.notification (studentid, notification, ndate) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, notification.getStudentID());
            pstmt.setInt(2, notification.getNotification());
            pstmt.setDate(3, new java.sql.Date(notification.getNDate().getTime()));
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(Notification notification) throws Exception {
        String sql = "UPDATE public.notification SET ndate = ? WHERE studentid = ? AND notification = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(notification.getNDate().getTime()));
            pstmt.setString(2, notification.getStudentID());
            pstmt.setInt(3, notification.getNotification());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String studentId, int notification) throws Exception {
        String sql = "DELETE FROM public.notification WHERE studentid = ? AND notification = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentId);
            pstmt.setInt(2, notification);
            pstmt.executeUpdate();
        }
    }
}
