package bean;

import java.io.Serializable;

/**
 * Staffのbeen
 *
 * @author admin
 * @version 1.0
 */
public class Staff implements Serializable {
	private String staffID;
	private String password;
	private String name;
	private String kFlag;

	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKFlag() {
		return kFlag;
	}
	public void setKFlag(String kFlag) {
		this.kFlag = kFlag;
	}

}
