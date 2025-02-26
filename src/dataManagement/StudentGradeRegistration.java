package dataManagement;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Score;
import bean.Student;
import dao.StudentDAO;
import tool.CommonServlet;

/**
 * @author yamamoto
 * クラス別点数入力画面用
 */
@WebServlet(urlPatterns = { "/DataManagement/StudentGradeRegistration" })
public class StudentGradeRegistration extends CommonServlet {
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		//別画面からの画面遷移時の挙動（初期表示）「
		req.getRequestDispatcher("/DataManagement/StudentGradeRegistration.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		System.out.println("ダミーデータ：");//TODO デバッグ

		// TODO データベースアクセスの前にダミーデータで動作確認
		//String studentID = "2501001";
		//String name = "大原22太郎";
		//String sex = "男";
		//String studentTEL = "09012345678";
		//String parentTEL  = "09087654321";
		//boolean dropFlag = false;


		//TODO DAOアクセスがうまくいかない
		Student myData = new Student();
		StudentDAO stDao = new StudentDAO();

		List<Student> tempALLStudent = stDao.all();

		//Student myData2 = new Student();
		myData = tempALLStudent.get(0);

		//DB登録できるようなソースを書かないといけない

		//学籍番号から成績データを取得
		//学年,月,科目コード,科目名,科目点数
		String[] strArray1 = {"1","7","AA1","PythonⅠ","80"};
		String[] strArray2 = {"1","7","AA2","PythonⅡ","70"};
		String[] strArray3 = {"1","10","ZZ1","情報基礎1","95"};
		String[] strArray4 = {"1","12","ZZ2","情報基礎2","90"};
		String[] strArray5 = {"1","12","ZZ3","情報基礎3","85"};

		Score objScore1 = new Score();
		Score objScore2 = new Score();
		Score objScore3 = new Score();
		Score objScore4 = new Score();
		Score objScore5 = new Score();

		objScore1.setScore("2501001", "AA1", "2025", "7", 80, 1);
		objScore2.setScore("2501001", "AA2", "2025", "7", 80, 1);
		objScore3.setScore("2501001", "ZZ1", "2025", "10", 95, 1);
		objScore4.setScore("2501001", "ZZ2", "2025", "12", 90, 1);
		objScore5.setScore("2501001", "ZZ3", "2025", "12", 85, 1);


		Score[] aryScore = {objScore1};
		String[][] strArrayALL = {strArray1,strArray2,strArray3,strArray4,strArray5};
		//TODO DAO周りを修正する。


		//レコードセットに入れて.jspへデータを送る
		req.setAttribute("studentID", myData.getStudentID());
		req.setAttribute("studentName", myData.getName());
		req.setAttribute("ALLData", myData);
		req.setAttribute("ALLGrade1", strArrayALL);
		req.setAttribute("ALLGrade2", strArrayALL);
		req.setAttribute("errMessage", "");

		System.out.println("ここまでうごいた_変更が反映されない："+myData.getName());//TODO デバッグ

		req.getRequestDispatcher("/DataManagement/StudentGradeRegistration.jsp").forward(req, resp);

	}
}
