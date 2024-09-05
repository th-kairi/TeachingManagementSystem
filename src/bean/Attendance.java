package bean;

import java.io.Serializable;
import java.sql.Date;

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
	private Date atDate;

	/**
	 * 出席確定フラグ
	 */
	private boolean point;

    public Attendance(String studentId, String attendance, String atReason, Date atDate, boolean point) {
        this.studentID = studentId;
        this.attendance = attendance;
        this.atReason = atReason;
        this.atDate = atDate;
        this.point = point;
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

}
