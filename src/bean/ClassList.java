package bean;

import java.io.Serializable;

/**
 * CLASSLISTのbean
 *
 * @author 東京情報校　辻本秀樹
 * @version 1.0
 */
public class ClassList implements Serializable {
	/**
	 * クラスコード
	 */
	private String classCD;

	/**
	 * クラス名
	 */
	private String name;

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


}
