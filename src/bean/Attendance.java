package bean;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * ATTENDANCEのbean
 * 
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Attendance implements Serializable {
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
	private LocalDate atDate;

	/**
	 * 出席確定フラグ
	 */
	private boolean point;

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

	public LocalDate getAtDate() {
		return atDate;
	}

	public void setAtDate(LocalDate atDate) {
		this.atDate = atDate;
	}

	public boolean isPoint() {
		return point;
	}

	public void setPoint(boolean point) {
		this.point = point;
	}

}
