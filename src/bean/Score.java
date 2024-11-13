package bean;

import java.io.Serializable;

/**
 * SCOREのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Score implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 科目コード
	 */
	private String subjectCD;

	/**
	 * 年度
	 */
	private String year;

	/**
	 * 評価月
	 */
	private String month;

	/**
	 * 得点
	 */
	private int score;

	/**
	 * 回数
	 */
	private int count;

    public void setScore(String studentID, String subjectCD, String year, String month, int score, int count) {
        this.studentID = studentID;
        this.subjectCD = subjectCD;
        this.year = year;
        this.month = month;
        this.score = score;
        this.count = count;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getSubjectCD() {
		return subjectCD;
	}

	public void setSubjectCD(String subjectCD) {
		this.subjectCD = subjectCD;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
