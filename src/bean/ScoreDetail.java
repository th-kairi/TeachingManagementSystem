package bean;

import java.io.Serializable;

public class ScoreDetail implements Serializable {

	private String studentId;
	private String name;
	private String subjectCd;
	private String subjectName;
	private String year;
	private String month;
	private int score;
	private int count;


    public String getStudentId() {
		return studentId;
	}


	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSubjectCd() {
		return subjectCd;
	}


	public void setSubjectCd(String subjectCd) {
		this.subjectCd = subjectCd;
	}


	public String getSubjectName() {
		return subjectName;
	}


	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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


	public void setScoreDetail(String studentId, String name, String subjectCd, String subjectName, String year, String month, int score, int count) {
        this.studentId = studentId;
        this.name = name;
        this.subjectCd = subjectCd;
        this.subjectName = subjectName;
        this.year = year;
        this.month = month;
        this.score = score;
        this.count = count;
    }


}
