package domain.user;

import java.io.Serializable;

public class Mark implements Serializable {
    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private double attendanceBonus;
    
    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
        this.attendanceBonus = 0;
    }
    
    public double calculateTotal() {
        double total = firstAttestation + secondAttestation + finalExam + attendanceBonus;
        return Math.min(total, 100);
    }
    
    public String getLetterGrade() {
        double total = calculateTotal();
        if (total >= 90) return "A";
        if (total >= 75) return "B";
        if (total >= 60) return "C";
        if (total >= 50) return "D";
        return "F";
    }
    
    public double getGpaValue() {
        double total = calculateTotal();
        if (total >= 90) return 4.0;
        if (total >= 75) return 3.0;
        if (total >= 60) return 2.0;
        if (total >= 50) return 1.0;
        return 0.0;
    }
    
    // Getters
    public double getFirstAttestation() { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam() { return finalExam; }
    public double getAttendanceBonus() { return attendanceBonus; }
    
    // Setters
    public void setFirstAttestation(double firstAttestation) { this.firstAttestation = firstAttestation; }
    public void setSecondAttestation(double secondAttestation) { this.secondAttestation = secondAttestation; }
    public void setFinalExam(double finalExam) { this.finalExam = finalExam; }
    public void setAttendanceBonus(double attendanceBonus) { this.attendanceBonus = attendanceBonus; }
    
    @Override
    public String toString() {
        return String.format("Mark: %.1f (First: %.1f, Second: %.1f, Final: %.1f, Bonus: %.1f) - %s", 
                             calculateTotal(), firstAttestation, secondAttestation, finalExam, attendanceBonus, getLetterGrade());
    }
}