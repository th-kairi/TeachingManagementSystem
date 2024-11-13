package bean;

import java.io.Serializable;

/**
 * STAFFTOCLASSのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class StaffToClass implements Serializable {
	/**
	 * 職員ID
	 */
	private String staffID;

	/**
	 * クラスコード
	 */
	private String classCD;

    public void setStaffToClass(String staffID, String classCD) {
        this.staffID = staffID;
        this.classCD = classCD;
    }

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getClassCD() {
		return classCD;
	}

	public void setClassCD(String classCD) {
		this.classCD = classCD;
	}


}
