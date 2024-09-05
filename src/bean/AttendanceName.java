package bean;

import java.io.Serializable;

/**
 * ATTENDANCENAMEのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class AttendanceName implements Serializable {
	/**
	 * 出席状況
	 */
	private String attendance;

	/**
	 * 出席状況名
	 */
	private String attendanceName;

	public AttendanceName(String attendance, String attendanceName) {
        this.attendance = attendance;
        this.attendanceName = attendanceName;
    }

	public String getAttendance() {
		return attendance;
	}

	public void setAttendance(String attendance) {
		this.attendance = attendance;
	}

	public String getAttendanceName() {
		return attendanceName;
	}

	public void setAttendanceName(String attendanceName) {
		this.attendanceName = attendanceName;
	}


}
