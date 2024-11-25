package bean;


public class AttenderRate {
    private String studentId;        // 学生番号
    private double attendanceRate;   // 出席率
    private double absenceRate;      // 欠席率
    private int tardiness;           // 遅刻
    private int earlyLeave;          // 早退
    private int cumulativeAbsence;   // 累計欠席
    private int fraction;            // 1未満累積欠席
    private int otherAbsence;        // その他欠席
    private int leaveOfAbsence;      // 休学

    // 全てのプロパティを設定するメソッド
    public void setAttenderRate(String studentId, double attendanceRate, double absenceRate,
                                     int tardiness, int earlyLeave, int cumulativeAbsence,
                                     int fraction, int otherAbsence, int leaveOfAbsence) {
        this.studentId = studentId;
        this.attendanceRate = attendanceRate;
        this.absenceRate = absenceRate;
        this.tardiness = tardiness;
        this.earlyLeave = earlyLeave;
        this.cumulativeAbsence = cumulativeAbsence;
        this.fraction = fraction;
        this.otherAbsence = otherAbsence;
        this.leaveOfAbsence = leaveOfAbsence;
    }

    // ゲッターとセッター
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(double attendanceRate) { this.attendanceRate = attendanceRate; }

    public double getAbsenceRate() { return absenceRate; }
    public void setAbsenceRate(double absenceRate) { this.absenceRate = absenceRate; }

    public int getTardiness() { return tardiness; }
    public void setTardiness(int tardiness) { this.tardiness = tardiness; }

    public int getEarlyLeave() { return earlyLeave; }
    public void setEarlyLeave(int earlyLeave) { this.earlyLeave = earlyLeave; }

    public int getCumulativeAbsence() { return cumulativeAbsence; }
    public void setCumulativeAbsence(int cumulativeAbsence) { this.cumulativeAbsence = cumulativeAbsence; }

    public int getFraction() { return fraction; }
    public void setFraction(int fraction) { this.fraction = fraction; }

    public int getOtherAbsence() { return otherAbsence; }
    public void setOtherAbsence(int otherAbsence) { this.otherAbsence = otherAbsence; }

    public int getLeaveOfAbsence() { return leaveOfAbsence; }
    public void setLeaveOfAbsence(int leaveOfAbsence) { this.leaveOfAbsence = leaveOfAbsence; }
}
