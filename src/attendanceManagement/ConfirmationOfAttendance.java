package attendanceManagement;

import java.time.LocalDateTime;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;
import tool.DateInformationAcquisition;

@WebServlet(urlPatterns = { "/AttendanceManagement/ConfirmationOfAttendance" })
public class ConfirmationOfAttendance extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		// 日付にかかわる変換を行なうクラスを生成
		DateInformationAcquisition dataInfo = new DateInformationAcquisition();
		// 基準日用を修正する為の変数
		String baseDate;
		// 引数を受け取る
		String strInputBaseDate = req.getParameter("baseDate");

		if (strInputBaseDate == null || strInputBaseDate.equals("")) {
			// LocalDateTimeのフォーマットを"yyyy-MM-dd"に修正する
			baseDate = dataInfo.getDateFormatter(LocalDateTime.now(), "yyyy-MM-dd");
		} else {
			baseDate = strInputBaseDate;
		}
		// 月末日付を取得するメソッド
		int lastDate = dataInfo.getLastDate(baseDate);

		req.setAttribute("baseDate", baseDate);

		req.getRequestDispatcher("/AttendanceManagement/ConfirmationOfAttendance.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
