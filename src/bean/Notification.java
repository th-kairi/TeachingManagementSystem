package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * NOTIFICATIONのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Notification implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 通知種別
	 */
	private int notification;

	/**
	 * 通知日
	 */
	private Date nDate;

    public void setNotification(String studentID, int notification, Date nDate) {
        this.studentID = studentID;
        this.notification = notification;
        this.nDate = nDate;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public int getNotification() {
		return notification;
	}

	public void setNotification(int notification) {
		this.notification = notification;
	}

	public Date getNDate() {
		return nDate;
	}

	public void setNDate(Date nDate) {
		this.nDate = nDate;
	}

}
