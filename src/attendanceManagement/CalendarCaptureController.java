package attendanceManagement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 *
 *カレンダー取り込み機能の画面表示、登録処理を行なう
 *
 * @author 大宮校　藤井
 *
 */

@WebServlet(urlPatterns = { "/AttendanceManagement/calendarCapture" })
public class CalendarCaptureController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		req.getRequestDispatcher("/AttendanceManagement/CalendarCapture.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("CalendarCaptureController.post()が実行されました");

		req.getRequestDispatcher("/AttendanceManagement/CalendarCapture.jsp").forward(req, resp);

	}

}
