package bean;

import java.io.Serializable;

/**
 * STUDENTのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Student implements Serializable {
	/**
	 * 学籍番号
	 */
	private String studentID;

	/**
	 * 学生名
	 */
	private String name;

	/**
	 * 性別
	 */
	private String sex;

	/**
	 * 本人連絡先
	 */
	private String studentTEL;

	/**
	 * 保護者連絡先
	 */
	private String parentTEL;

	/**
	 * 退学フラグ
	 */
	private boolean dropFlag;


    public void setStudent(String studentID, String name, String sex, String studentTEL, String parentTEL, boolean dropFlag) {
        this.studentID = studentID;
        this.name = name;
        this.sex = sex;
        this.studentTEL = studentTEL;
        this.parentTEL = parentTEL;
        this.dropFlag = dropFlag;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStudentTEL() {
		return studentTEL;
	}

	public void setStudentTEL(String studentTEL) {
		this.studentTEL = studentTEL;
	}

	public String getParentTEL() {
		return parentTEL;
	}

	public void setParentTEL(String parentTEL) {
		this.parentTEL = parentTEL;
	}

	public boolean getDropFlag() {
		return dropFlag;
	}

	public void setDropFlag(boolean dropFlag) {
		this.dropFlag = dropFlag;
	}

}
