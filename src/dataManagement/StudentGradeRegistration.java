package dataManagement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Student;
import tool.CommonServlet;

/**
 * @author admin
 *
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

		// TODO データベースアクセスの前にダミーデータで動作確認
		String studentID = "2501001";
		String name = "大原太郎";
		String sex = "男";
		String studentTEL = "09012345678";
		String parentTEL  = "09087654321";
		boolean dropFlag = false;
		
		//Strudent headerData = new Student(,'f');
		

		//TODO DAOアクセスがうまくいかない
		Student myData = new Student(studentID,name,  sex,  studentTEL,  parentTEL,  dropFlag);

		//DB登録できるようなソースを書かないといけない

		//学籍番号から成績データを取得
		//学年,月,科目コード,科目名,科目点数
		String[] strArray1 = {"1","7","AA1","PythonⅠ","80"};
		String[] strArray2 = {"1","7","AA2","PythonⅡ","70"};
		String[] strArray3 = {"1","10","ZZ1","情報基礎1","95"};
		String[] strArray4 = {"1","12","ZZ2","情報基礎2","90"};
		String[] strArray5 = {"1","12","ZZ3","情報基礎3","85"};



		String[][] strArrayALL = {strArray1,strArray2,strArray3,strArray4,strArray5};
		//TODO DAO周りを修正する。


		//レコードセットに入れて.jspへデータを送る
		req.setAttribute("studentID", myData.getStudentID());
		req.setAttribute("studentName", myData.getName());
		req.setAttribute("ALLData", myData);
		req.setAttribute("ALLGrade", strArrayALL);
		req.setAttribute("errMessage", "");


		System.out.println("ここまでうごいた："+myData.getName());//TODO デバッグ

		req.getRequestDispatcher("/DataManagement/StudentGradeRegistration.jsp").forward(req, resp);


	}
}
