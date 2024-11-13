package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Score;

public class ScoreDAO extends DAO {

    // 全件検索
    public List<Score> all() throws Exception {
        List<Score> scoreList = new ArrayList<>();
        String sql = "SELECT * FROM score";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Score score = new Score();
                score.setScore(
                    rs.getString("studentID"),
                    rs.getString("subjectCD"),
                    rs.getString("year"),
                    rs.getString("month"),
                    rs.getInt("score"),
                    rs.getInt("count")
                );
                scoreList.add(score);
            }
        }
        return scoreList;
    }

    // データ登録
    public void insert(Score score) throws Exception {
        String sql = "INSERT INTO score (studentid, subjectcd, year, month, score, count) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, score.getStudentID());
            pstmt.setString(2, score.getSubjectCD());
            pstmt.setString(3, score.getYear());
            pstmt.setString(4, score.getMonth());
            pstmt.setInt(5, score.getScore());
            pstmt.setInt(6, score.getCount());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(Score score) throws Exception {
        String sql = "UPDATE score SET year = ?, month = ?, score = ? WHERE studentid = ? AND subjectcd = ? AND count = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, score.getYear());
            pstmt.setString(2, score.getMonth());
            pstmt.setInt(3, score.getScore());
            pstmt.setString(4, score.getStudentID());
            pstmt.setString(5, score.getSubjectCD());
            pstmt.setInt(6, score.getCount());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String studentID, String subjectCD, int count) throws Exception {
        String sql = "DELETE FROM score WHERE studentID = ? AND subjectCD = ? AND count = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            pstmt.setString(2, subjectCD);
            pstmt.setInt(3, count);
            pstmt.executeUpdate();
        }
    }
}
