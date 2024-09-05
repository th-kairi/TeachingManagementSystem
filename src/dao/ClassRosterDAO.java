package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ClassRoster;

public class ClassRosterDAO extends DAO {

    // 全件検索
    public List<ClassRoster> all() throws Exception {
        List<ClassRoster> classRosterList = new ArrayList<>();
        String sql = "SELECT * FROM public.classroster";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ClassRoster classRoster = new ClassRoster(
                    rs.getInt("id"),
                    rs.getString("classcd"),
                    rs.getString("name"),
                    rs.getString("studentid")
                );
                classRosterList.add(classRoster);
            }
        }
        return classRosterList;
    }

    // データ登録
    public void insert(ClassRoster classRoster) throws Exception {
        String sql = "INSERT INTO public.classroster (classcd, name, studentid) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, classRoster.getClassCD());
            pstmt.setString(2, classRoster.getName());
            pstmt.setString(3, classRoster.getStudentID());
            pstmt.executeUpdate();

            // 自動生成されたIDを取得
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    classRoster.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    // データ更新
    public void update(ClassRoster classRoster) throws Exception {
        String sql = "UPDATE public.classroster SET classcd = ?, name = ?, studentid = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classRoster.getClassCD());
            pstmt.setString(2, classRoster.getName());
            pstmt.setString(3, classRoster.getStudentID());
            pstmt.setInt(4, classRoster.getId());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM public.classroster WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
