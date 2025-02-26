package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.ScoreDetail;

public class ScoreManagementDAO extends DAO {

	// 該当クラスの学生の、該当科目の得点を返す
	// 未入力でもクラスメイト全員分を返す
	// 未入力の場合、学生ID、学生名以外はnull
	public List<ScoreDetail> getScoreByClassCdSubjectCd(String classCd, String subjectCd) throws Exception {

		List<ScoreDetail> scoreDetailList = new ArrayList<>();

		String sql = "SELECT"
				+ " tbl1.studentid, tbl1.name, score.subjectcd, subject.subjectname, score.year, score.month, score.score, score.count"
				+ " FROM"
				+ "		(select student.studentid, student.name"
				+ "			from student"
				+ "				where student.studentid in (SELECT studentid FROM classroster WHERE classcd = ?)"
				+ "		) tbl1"
				+ "	LEFT OUTER JOIN score ON tbl1.studentid = score.studentid and score.subjectcd = ?"
				+ "	LEFT OUTER JOIN subject ON score.subjectcd = subject.subjectcd";

		try {
			Connection conn = getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, classCd);
			pstmt.setString(2, subjectCd);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ScoreDetail scoreDetail = new ScoreDetail();
				scoreDetail.setScoreDetail(
						rs.getString("studentid"),
						rs.getString("name"),
						rs.getString("subjectcd"),
						rs.getString("subjectname"),
						rs.getString("year"),
						rs.getString("month"),
						rs.getInt("score"),
						rs.getInt("count")
						);
				scoreDetailList.add(scoreDetail);
			}

		}
		catch (Exception e) {
			e.printStackTrace();  // エラーメッセージを表示する
		}

		return scoreDetailList;
	}

}
