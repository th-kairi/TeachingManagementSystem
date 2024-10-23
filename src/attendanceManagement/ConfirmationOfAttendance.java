package attendanceManagement;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassList;
import dao.ClassListDAO;
import tool.CommonServlet;
import tool.DateInformationAcquisition;

/**
 * @author admin
 *
 */
@WebServlet(urlPatterns = { "/AttendanceManagement/ConfirmationOfAttendance" })
public class ConfirmationOfAttendance extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		// ----- 日付取得修正処理 ------------------------------------------------------------------------
		// 日付にかかわる変換を行なうクラスを生成
		DateInformationAcquisition dataInfo = new DateInformationAcquisition();
		// 基準日用を修正する為の変数
		String baseDate;
		// 引数を受け取る
		String strInputBaseDate = req.getParameter("baseDate");
		String strClassCode = req.getParameter("classCode");

		if (strInputBaseDate == null || strInputBaseDate.equals("")) {
			// LocalDateTimeのフォーマットを"yyyy-MM-dd"に修正する
			baseDate = dataInfo.getDateFormatter(LocalDateTime.now(), "yyyy-MM-dd");
		} else {
			baseDate = strInputBaseDate;
		}
		// 月末日付を取得するメソッド
		int lastDate = dataInfo.getLastDate(baseDate);

		req.setAttribute("baseDate", baseDate);
		req.setAttribute("classCode", strClassCode);

		// ----- クラスリスト取得処理 ------------------------------------------------------------------------
		ClassListDAO dao = new ClassListDAO();

		List<ClassList> classList = dao.all();
		req.setAttribute("list", classList);

		req.getRequestDispatcher("/AttendanceManagement/ConfirmationOfAttendance.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
