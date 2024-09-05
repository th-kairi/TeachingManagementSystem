package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.Student;

public class StudentDAO extends DAO {

    // 全件検索
    public List<Student> all() throws Exception {
        List<Student> studentList = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student(
                    rs.getString("studentID"),
                    rs.getString("name"),
                    rs.getString("sex"),
                    rs.getString("studentTEL"),
                    rs.getString("parentTEL"),
                    rs.getBoolean("dropflag")
                );
                studentList.add(student);
            }
        }
        return studentList;
    }

    // データ登録
    public void insert(Student student) throws Exception {
        String sql = "INSERT INTO student (studentID, name, sex, studentTEL, parentTEL, dropflag) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getSex());
            pstmt.setString(4, student.getStudentTEL());
            pstmt.setString(5, student.getParentTEL());
            pstmt.setBoolean(6, student.getDropFlag());
            pstmt.executeUpdate();
        }
    }

    // データ更新
    public void update(Student student) throws Exception {
        String sql = "UPDATE student SET name = ?, sex = ?, studentTEL = ?, parentTEL = ?, dropflag = ? WHERE studentid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.setString(2, student.getSex());
            pstmt.setString(3, student.getStudentTEL());
            pstmt.setString(4, student.getParentTEL());
            pstmt.setBoolean(5, student.getDropFlag());
            pstmt.setString(6, student.getStudentID());
            pstmt.executeUpdate();
        }
    }

    // データ削除
    public void delete(String studentID) throws Exception {
        String sql = "DELETE FROM student WHERE studentid = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, studentID);
            pstmt.executeUpdate();
        }
    }
}
