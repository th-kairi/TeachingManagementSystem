package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDAO extends DAO {

    // 全件検索
    public List<Subject> all() throws Exception {
        List<Subject> subjectList = new ArrayList<>();
        String sql = "SELECT * FROM subject";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubject(
                    rs.getString("subjectcd"),
                    rs.getString("subjectname"),
                    rs.getInt("credit"),
                    rs.getDate("closingdate")
                );
                subjectList.add(subject);
            }
        }
        return subjectList;
    }

    // データ登録
    public void insert(Subject subject) throws Exception {
        String sql = "INSERT INTO subject (subjectcd, subjectname, credit, closingdate) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject.getSubjectCD());
            pstmt.setString(2, subject.getSubjectName());
            pstmt.setInt(3, subject.getCredit());
            pstmt.setDate(4, new java.sql.Date(subject.getClosingDate().getTime()));
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(Subject subject) throws Exception {
        String sql = "UPDATE subject SET subjectname = ?, credit = ?, closingdate = ? WHERE subjectcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subject.getSubjectName());
            pstmt.setInt(2, subject.getCredit());
            pstmt.setDate(3, new java.sql.Date(subject.getClosingDate().getTime()));
            pstmt.setString(4, subject.getSubjectCD());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String subjectCd) throws Exception {
        String sql = "DELETE FROM subject WHERE subjectcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, subjectCd);
            pstmt.executeUpdate();
        }
    }
}
