package bean;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * SUBJECTのbean
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Subject implements Serializable {
	/**
	 * 科目コード
	 */
	private String subjectCD;

	/**
	 * 科目名
	 */
	private String subjectName;

	/**
	 * 単位
	 */
	private String credit;

	/**
	 * 締め日（成績）
	 */
	private LocalDate closingDate;

	public String getSubjectCD() {
		return subjectCD;
	}

	public void setSubjectCD(String subjectCD) {
		this.subjectCD = subjectCD;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public LocalDate getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

}
