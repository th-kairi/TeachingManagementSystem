package bean;

import java.io.Serializable;
import java.util.Date;

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
	private int credit;

	/**
	 * 締め日（成績）
	 */
	private Date closingDate;

    public Subject(String subjectCd, String subjectName, int credit, Date closingDate) {
        this.subjectCD = subjectCd;
        this.subjectName = subjectName;
        this.credit = credit;
        this.closingDate = closingDate;
    }

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

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

}
