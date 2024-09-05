package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Attendance;

public class AttendanceDAO extends DAO {
	public List<Attendance> all()  throws Exception{
		List<Attendance> list = new ArrayList<Attendance>();

		// データベースに接続
		Connection con = getConnection();

		// 実行したいSQL文をプリペアードステートメントで準備
		PreparedStatement st = con.prepareStatement(
				"select * from attendance");
		// SQL文を実行した結果をリザルトセットに格納
		ResultSet rs = st.executeQuery();

		// 結果から1件ずつ取り出すループ
		while (rs.next()) {
			// Attendanceクラスをインスタンス化
			Attendance atten = new Attendance();
			// 値をセット
			atten.setStudentID(rs.getString("studentid"));
			atten.setAttendance(rs.getString("attendance"));
			atten.setAtReason(rs.getString("atreason"));
			atten.setAtDate(rs.getDate("stdate"));
			atten.setPoint(rs.getBoolean("point"));
			// リストに追加
			list.add(atten);
		}

		// データベースとの接続を解除
		st.close();
		con.close();

		// 商品リストを返却
		return list;

	}


}
