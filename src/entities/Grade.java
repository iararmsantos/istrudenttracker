
package entities;

/**
 * Purpose: It will be used to get all grades of each student for any activity
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class Grade {
    private int gradeId;
    private char letterGrade;
    private double numberGrade;

    public Grade() {
    }

    public Grade(int gradeId, char letterGrade, double numberGrade) {
        this.gradeId = gradeId;
        this.letterGrade = letterGrade;
        this.numberGrade = numberGrade;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public char getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getNumberGrade() {
        return numberGrade;
    }

    public void setNumberGrade(double numberGrade) {
        this.numberGrade = numberGrade;
    }
    
    
}
