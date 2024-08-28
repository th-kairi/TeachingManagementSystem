package bean;

import java.io.Serializable;

/**
 * NOTIFICATIONNAMEのbean
 * 
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class NotificationName implements Serializable {
	/**
	 * 通知種別
	 */
	private int notification;

	/**
	 * 種別名
	 */
	private String notificationName;

	public int getNotification() {
		return notification;
	}

	public void setNotification(int notification) {
		this.notification = notification;
	}

	public String getNotificationName() {
		return notificationName;
	}

	public void setNotificationName(String notificationName) {
		this.notificationName = notificationName;
	}

}
