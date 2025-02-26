package bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * ATTENDANCEのbean
 *
 * @author 東京情報校　渡邉悠介
 * @version 1.0
 */
public class AttendanceDetail implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 出席状況
	 */
	private String attendance;

	/**
	 * 出欠理由
	 */
	private String atReason;

	/**
	 * 日付
	 */
	private Date atDate;

	/**
	 * 出席確定フラグ
	 */
	private boolean point;

	/**
	 * 累計欠席
	 */
	private double attendanceAllSum;

	/**
	 * 今月累計欠席
	 */
	private double attendanceMonthSum;

	/**
	 * 欠席
	 */
	private int attendanceAbsenceSum;

	/**
	 * 遅刻
	 */
	private int attendanceLatenessSum;

	/**
	 * 早退
	 */
	private int attendanceEarlySum;

	/**
	 * その他欠席
	 */
	private int attendanceOtherSum;

	/**
	 * 休学
	 */
	private int attendanceSuspensionSum;

	/**
	 * 電話番号
	 */
	private String studenttel;


    public void setAttendance(String studentID, String attendance, String atReason, Date atDate, boolean point
    		, double attendanceAllSum, double attendanceMonthSum, int attendanceAbsenceSum, int attendanceLatenessSum, int attendanceEarlySum
    		, int attendanceOtherSum, int attendanceSuspensionSum, String studenttel) {
        this.studentID = studentID;
        this.attendance = attendance;
        this.atReason = atReason;
        this.atDate = atDate;
        this.point = point;
        this.attendanceAllSum = attendanceAllSum;
        this.attendanceMonthSum = attendanceMonthSum;
        this.attendanceAbsenceSum = attendanceAbsenceSum;
        this.attendanceLatenessSum = attendanceLatenessSum;
        this.attendanceEarlySum = attendanceEarlySum;
        this.attendanceOtherSum = attendanceOtherSum;
        this.attendanceSuspensionSum = attendanceSuspensionSum;
        this.studenttel = studenttel;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getAtReason() {
		return atReason;
	}

	public void setAtReason(String atReason) {
		this.atReason = atReason;
	}

	public Date getAtDate() {
		return atDate;
	}

	public void setAtDate(Date atDate) {
		this.atDate = atDate;
	}

	public boolean isPoint() {
		return point;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}

	public double getAttendanceAllSum() {
		return attendanceAllSum;
	}

	public void setAttendanceAllSum(double attendanceAllSum) {
		this.attendanceAllSum = attendanceAllSum;
	}

	public double getAttendanceMonthSum() {
		return attendanceMonthSum;
	}

	public void setAttendanceMonthSum(double attendanceMonthSum) {
		this.attendanceMonthSum = attendanceMonthSum;
	}

	public int getAttendanceAbsenceSum() {
		return attendanceAbsenceSum;
	}

	public void setAttendanceAbsenceSum(int attendanceAbsenceSum) {
		this.attendanceAbsenceSum = attendanceAbsenceSum;
	}

	public int getAttendanceLatenessSum() {
		return attendanceLatenessSum;
	}

	public void setAttendanceLatenessSum(int attendanceLatenessSum) {
		this.attendanceLatenessSum = attendanceLatenessSum;
	}

	public int getAttendanceEarlySum() {
		return attendanceEarlySum;
	}

	public void setAttendanceEarlySum(int attendanceEarlySum) {
		this.attendanceEarlySum = attendanceEarlySum;
	}

	public int getAttendanceOtherSum() {
		return attendanceOtherSum;
	}

	public void setAttendanceOtherSum(int attendanceOtherSum) {
		this.attendanceOtherSum = attendanceOtherSum;
	}

	public int getAttendanceSuspensionSum() {
		return attendanceSuspensionSum;
	}

	public void setAttendanceSuspensionSum(int attendanceSuspensionSum) {
		this.attendanceSuspensionSum = attendanceSuspensionSum;
	}

	public String getStudenttel() {
		return studenttel;
	}

	public void setStudenttel(String studenttel) {
		this.studenttel = studenttel;
	}


}
