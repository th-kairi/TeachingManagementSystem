package bean;

public class StudentAttendance {
    private String studentId;
    private int requiredAttendanceDays; // 必要出席日数
    private int tardinessCount; // 遅刻の回数
    private int earlyLeaveCount; // 早退の回数
    private int absenceCount; // 欠席の回数
    private int otherAbsence; // その他の欠席
    private int leaveOfAbsence; // 休学
    private int previousFraction; // 前月からの繰越分 (0, 1, 2)

    // 繰越を考慮した最終欠席数を計算
    public int calculateFinalAbsence() {
        int totalTardinessAndEarlyLeave = tardinessCount + earlyLeaveCount + previousFraction;
        int finalAbsence = absenceCount + (totalTardinessAndEarlyLeave / 3);
        return finalAbsence;
    }

    // 次月へ繰り越す fraction (3で割った余り)
    public int getNextFraction() {
        return (tardinessCount + earlyLeaveCount + previousFraction) % 3;
    }

    // ゲッター・セッター
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public int getRequiredAttendanceDays() { return requiredAttendanceDays; }
    public void setRequiredAttendanceDays(int requiredAttendanceDays) { this.requiredAttendanceDays = requiredAttendanceDays; }

    public int getTardinessCount() { return tardinessCount; }
    public void setTardinessCount(int tardinessCount) { this.tardinessCount = tardinessCount; }

    public int getEarlyLeaveCount() { return earlyLeaveCount; }
    public void setEarlyLeaveCount(int earlyLeaveCount) { this.earlyLeaveCount = earlyLeaveCount; }

    public int getAbsenceCount() { return absenceCount; }
    public void setAbsenceCount(int absenceCount) { this.absenceCount = absenceCount; }

    public int getOtherAbsence() { return otherAbsence; }
    public void setOtherAbsence(int otherAbsence) { this.otherAbsence = otherAbsence; }

    public int getLeaveOfAbsence() { return leaveOfAbsence; }
    public void setLeaveOfAbsence(int leaveOfAbsence) { this.leaveOfAbsence = leaveOfAbsence; }

    public int getPreviousFraction() { return previousFraction; }
    public void setPreviousFraction(int previousFraction) { this.previousFraction = previousFraction; }
}
