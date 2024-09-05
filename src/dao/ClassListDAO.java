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
        List<ClassList> classListList = new ArrayList<>();
        String sql = "SELECT * FROM public.classlist";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClassList classList = new ClassList(
                    rs.getString("classcd"),
                    rs.getString("name")
                );
                classListList.add(classList);
            }
        }
        return classListList;
    }

    // データ登録
    public void insert(ClassList classList) throws Exception {
        String sql = "INSERT INTO public.classlist (classcd, name) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classList.getClassCD());
            pstmt.setString(2, classList.getName());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(ClassList classList) throws Exception {
        String sql = "UPDATE public.classlist SET name = ? WHERE classcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classList.getName());
            pstmt.setString(2, classList.getClassCD());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String classCD) throws Exception {
        String sql = "DELETE FROM public.classlist WHERE classcd = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classCD);
            pstmt.executeUpdate();
        }
    }
}