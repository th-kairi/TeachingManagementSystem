package bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * ATTENDANCEMANAGEMENTのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class AttendanceManagement implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 日付
	 */
	private Date atDate;

	/**
	 * 出席状況
	 */
	private String attendance;

	/**
	 * 出欠理由
	 */
	private String atReason;

	/**
	 * 出席確定フラグ
	 */
	private boolean point;

}
