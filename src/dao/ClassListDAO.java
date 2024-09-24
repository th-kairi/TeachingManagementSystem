package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ClassList;

public class ClassListDAO extends DAO {

    // 全件検索
    public List<ClassList> all() throws Exception {
        List<ClassList> list = new ArrayList<>();
        String sql = "SELECT * FROM classlist";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ClassList classList = new ClassList();
                classList.setClassCD(rs.getString("classcd"));
                classList.setName(rs.getString("name"));
                list.add(classList);
            }
        }
        return list;
    }

    // データ登録
    public void insert(ClassList classList) throws Exception {
        String sql = "INSERT INTO classlist (classcd, name) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classList.getClassCD());
            pstmt.setString(2, classList.getName());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(ClassList classList) throws Exception {
        String sql = "UPDATE classlist SET name = ? WHERE classcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classList.getName());
            pstmt.setString(2, classList.getClassCD());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String classCD) throws Exception {
        String sql = "DELETE FROM classlist WHERE classcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classCD);
            pstmt.executeUpdate();
        }
    }
}
