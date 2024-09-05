package bean;

import java.io.Serializable;

/**
 * CLASSROSTERのbean
 * 
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class Classroster implements Serializable {
	/**
	 * ID
	 */
	private int id;

	/**
	 * クラスコード
	 */
	private int classCD;

	/**
	 * 学籍番号
	 */
	private String studentID;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassCD() {
		return classCD;
	}

	public void setClassCD(int classCD) {
		this.classCD = classCD;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

}
