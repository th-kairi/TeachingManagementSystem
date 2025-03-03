package attendanceManagement;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassList;
import dao.AttendancerateDAO;
import dao.AttendancerateFinalizedDAO;
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
        System.out.println("classCodes:" + Arrays.toString(classCodes));  // 配列の中身を表示
        System.out.println("classCodes.length:" + classCodes.length);

        // クラスコードが選択されていない場合のエラーハンドリング
        if (baseDateStr == null || classCodes == null || classCodes.length == 0) {
            req.setAttribute("error", "クラスと確定月を選択してください。");
            req.getRequestDispatcher("/AttendanceManagement/FixMonthlyAttendance.jsp").forward(req, resp);
            return;
        }
//        YearMonth baseMonth = YearMonth.parse(baseDateStr);

        AttendancerateDAO attendancerateDAO = new AttendancerateDAO();
        AttendancerateFinalizedDAO attendancerateFinalizedDAO = new AttendancerateFinalizedDAO();

        boolean overallSuccess = true;  // 複数クラス処理の結果を追跡

        // 各クラスコードについてループ
        for (String classCode : classCodes) {
        	System.out.println("attendancerateDAO.updateAttendanceRateByClass("+classCode+")");
            boolean success = attendancerateDAO.updateAttendanceRateByClass(classCode);
            System.out.print("実行結果：" + success);
        	System.out.println("attendancerateFinalizedDAO.finalizeMonthlyAttendanceRates("+baseDateStr+", "+classCode+")");
            boolean result = attendancerateFinalizedDAO.updateAttendanceRateByClass(classCode, baseDateStr);
            System.out.println("実行結果：" + result);

            // どれかのクラスで失敗した場合、全体の処理を失敗とする
            if (!success || !result) {
                overallSuccess = false;
                break;
            }
        }

        // 処理結果による遷移
        if (overallSuccess) {
            System.out.println("success");
            resp.sendRedirect("/TeachingManagementSystem/AttendanceManagement/FixMonthlyAttendance");
        } else {
            System.out.println("error");
            req.setAttribute("error", "出席確定処理中にエラーが発生しました。");
            resp.sendRedirect("/TeachingManagementSystem/AttendanceManagement/FixMonthlyAttendance");
        }
    }

}
