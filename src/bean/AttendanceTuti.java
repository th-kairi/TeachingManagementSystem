package bean;

import java.io.Serializable;

/**
 * ATTENDANCEのbean
 *
 * @author 東京情報校　渡邉悠介
 * @version 1.0
 */
public class AttendanceTuti implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 通知状況
	 */
	private String tutiStatus;


    public void setAttendanceTuti(String studentID, String tutiStatus) {
        this.studentID = studentID;
        this.tutiStatus = tutiStatus;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getTutiStatus() {
		return tutiStatus;
	}

	public void setTutiStatus(String tutiStatus) {
		this.tutiStatus = tutiStatus;
	}


}
