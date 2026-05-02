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
        if (total >= 94.5 && total <= 100) return "A";
        if(total >=89.5 && total < 94.5) return "A-";
        if (total >= 84.5 && total < 89.5) return "B+";
        if(total >= 79.5 && total < 84.5) return "B";
        if(total >= 74.5 && total < 79.5) return "B-";
        if(total >= 69.5 && total < 74.5) return "C+";
        if(total >= 64.5 && total < 69.5) return "C";
        if(total >= 59.5 && total < 64.5) return "C-";
        if(total >= 54.5 && total < 59.5) return "D+";
        if(total >= 49.5 && total < 54.5) return "D";
        if (total < 49.5) return "F";
    }
    
    public double getGpaValue() {
        double total = calculateTotal();
         if (total >= 94.5 && total <= 100) return 4.00;
        if(total >=89.5 && total < 94.5) return 3.67;
        if (total >= 84.5 && total < 89.5) return 3.33;
        if(total >= 79.5 && total < 84.5) return 3.00;
        if(total >= 74.5 && total < 79.5) return 2.67;
        if(total >= 69.5 && total < 74.5) return 2.33;
        if(total >= 64.5 && total < 69.5) return 2.0;
        if(total >= 59.5 && total < 64.5) return 1.67;
        if(total >= 54.5 && total < 59.5) return 1.33;
        if(total >= 49.5 && total < 54.5) return 1.00;
        if (total < 49.5) return 0.00;
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