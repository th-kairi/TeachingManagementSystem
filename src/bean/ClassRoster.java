package bean;

import java.io.Serializable;

/**
 * CLASSROSTERのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class ClassRoster implements Serializable {
	/**
	 * ID
	 */
	private int id;

	/**
	 * クラスコード
	 */
	private String classCD;

	/**
	 * コース名
	 */
	private String name;

	/**
	 * 学籍番号
	 */
	private String studentID;

    public ClassRoster(int id, String classCD, String name, String studentID) {
        this.id = id;
        this.classCD = classCD;
        this.name = name;
        this.studentID = studentID;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassCD() {
		return classCD;
	}

	public void setClassCD(String classCD) {
		this.classCD = classCD;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

}
