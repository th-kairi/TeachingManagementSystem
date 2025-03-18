package dataManagement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Score;
import bean.Student;
import dao.ScoreDAO;
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

		StudentDAO stDao = new StudentDAO();
		Student myData = stDao.idSearch(req.getParameter("studentID"));//テキストボックスから取得した学籍番号で該当者を検索

		//List<Student> tempALLStudent = stDao.all();

		String errMessage = "";
		List<Score> tempALLScore;
		List<Score> StudentScore;
        String[][] strArrayALL  = new String[0][0];//メモリリーク無視

		try{

    		ScoreDAO scDao = new ScoreDAO();
		    tempALLScore = scDao.all();
            StudentScore = new ArrayList<Score>();


		    //一旦既存のDAOは壊さないようにALLを使って内部的なLISTを作成する
		    for (Score s : tempALLScore){
			    //学籍番号の確認
			    if(req.getParameter("studentID").equals(s.getStudentID())){
			        //一致するなら追加
				    System.out.println(s.getStudentID()+"を追加");
			        StudentScore.add(s);
			        //TODU 下の処理、ここでうまくやったらできないかな？
			    }else{
				    System.out.println(s.getStudentID()+"を追加しない");
			    }
		    }

		    //多分無駄な処理、他の解決方法が思い浮かばない
		    strArrayALL = new String[StudentScore.size()][5];
            for(int i=0; i<StudentScore.size();i++){
        	    strArrayALL[i][0]=StudentScore.get(i).getStudentID();
        	    strArrayALL[i][1]=StudentScore.get(i).getSubjectCD();
        	    strArrayALL[i][2]=StudentScore.get(i).getYear();
        	    strArrayALL[i][3]=StudentScore.get(i).getMonth();
        	    strArrayALL[i][4]=Integer.toString(StudentScore.get(i).getScore());

            }
		}catch(Exception e){
			//errMessage = e.getMessage();
			errMessage = "エラー発生、正常な入力を入れて再実行してください。";
		}

		//レコードセットに入れて.jspへデータを送る
		req.setAttribute("studentID", myData.getStudentID());
		req.setAttribute("studentName", myData.getName());
		req.setAttribute("ALLData", myData);
		req.setAttribute("ALLGrade", strArrayALL);//DBから取得したスコアを画面に送信
		req.setAttribute("errMessage", errMessage);
		req.getRequestDispatcher("/DataManagement/StudentGradeRegistration.jsp").forward(req, resp);

	}
}
