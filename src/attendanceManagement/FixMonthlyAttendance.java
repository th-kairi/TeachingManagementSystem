package attendanceManagement;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassList;
import dao.AttendanceDAO;
import dao.ClassListDAO;
import tool.CommonServlet;
import tool.DateInformationAcquisition;

/**
 * 月ごとの出席確定処理を行うサーブレット
 */
@WebServlet(urlPatterns = { "/AttendanceManagement/FixMonthlyAttendance" })
public class FixMonthlyAttendance extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	     // 日付取得クラスのインスタンスを作成
	     DateInformationAcquisition dateInfo = new DateInformationAcquisition();

	     // リクエストパラメータ取得（入力がなければデフォルトで前月）
	     String inputBaseDate = req.getParameter("baseDate");
	     String baseDate;

	     if (inputBaseDate == null || inputBaseDate.isEmpty()) {
	         // LocalDate を LocalDateTime に変換してからフォーマットする
	         LocalDateTime defaultBaseDate = YearMonth.now().minusMonths(1).atDay(1).atStartOfDay();
	         baseDate = dateInfo.getDateFormatter(defaultBaseDate, "yyyy-MM");
	     } else {
	         baseDate = inputBaseDate;
	     }

        req.setAttribute("baseDate", baseDate);

        // クラスリスト取得
//        ClassListDAO classDao = new ClassListDAO();
//        List<ClassList> classList = classDao.all();
//

		ClassListDAO dao = new ClassListDAO();

		List<ClassList> classList = dao.all();
        req.setAttribute("list", classList);

        // JSPにフォワード
        req.getRequestDispatcher("/AttendanceManagement/FixMonthlyAttendance.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    	System.out.println("確定処理");
        // 確定処理
        String baseDateStr = req.getParameter("baseDate");
        String[] classCodes = req.getParameterValues("classCodes");

    	System.out.println("baseDateStr:" + baseDateStr);
    	System.out.println("classCodes:" + classCodes);
    	System.out.println("classCodes.length:" + classCodes.length);

        if (baseDateStr == null || classCodes == null || classCodes.length == 0) {
            req.setAttribute("error", "クラスと確定月を選択してください。");
            req.getRequestDispatcher("/AttendanceManagement/FixMonthlyAttendance.jsp").forward(req, resp);
            return;
        }

        YearMonth baseMonth = YearMonth.parse(baseDateStr);

        AttendanceDAO attendanceDao = new AttendanceDAO();
        boolean success = attendanceDao.finalizeAttendance(baseMonth, classCodes);



        if (success) {
        	System.out.println("seccess");
            resp.sendRedirect("/TeachingManagementSystem/AttendanceManagement/FixMonthlyAttendance");
        } else {
        	System.out.println("error");
            req.setAttribute("error", "出席確定処理中にエラーが発生しました。");
            req.getRequestDispatcher("/AttendanceManagement/FixMonthlyAttendance.jsp").forward(req, resp);
        }
    }
}
