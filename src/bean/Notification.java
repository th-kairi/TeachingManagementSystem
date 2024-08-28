package bean;

import java.io.Serializable;
import java.time.LocalDate;

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
	private String studentId;

	/**
	 * 通知種別
	 */
	private int notification;

	/**
	 * 通知日
	 */
	private LocalDate nDate;

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getNotification() {
		return notification;
	}

	public void setNotification(int notification) {
		this.notification = notification;
	}

	public LocalDate getnDate() {
		return nDate;
	}

	public void setnDate(LocalDate nDate) {
		this.nDate = nDate;
	}

}
