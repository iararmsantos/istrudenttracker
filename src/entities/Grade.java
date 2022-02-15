
package entities;

/**
 * Purpose: It will be used to get all grades of each student for any activity
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class Grade {
    private int gradeId;
    private String activity;    
    private double numberGrade;
    private static int count = 0;
    

    public Grade() {
        setGradeId(++count);
    }

    public Grade(double numberGrade, String activity) {
        setGradeId(++count);        
        this.numberGrade = numberGrade;
        this.activity = activity;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public double getNumberGrade() {
        return numberGrade;
    }

    public void setNumberGrade(double numberGrade) {
        this.numberGrade = numberGrade;
    }
    
    
}
