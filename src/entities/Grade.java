
package entities;

/**
 * Purpose: It will be used to get all grades of each student for any activity
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class Grade {
    private int gradeId;      
    private double[] activities = new double[5];
    private static int count = 0;
    

    public Grade() {
        setGradeId(++count);
    }

    public Grade(double[] activities) {
        this.activities = activities;
    }
    
    

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public double[] getActivities() {
        return activities;
    }

    public void setActivities(double[] activities) {
        this.activities = activities;
    }

    /**
     * Set new marks for the student
     * @param markID the specific mark to adjust
     * @param mark the new mark
     * by: James Ronholm
     */
    public void setGrade(int markID, double mark) {
        
        this.activities[markID] = mark;
    }
    /**
     * Get the student's marks
     * @param markID the specific mark to get
     * by: James Ronholm
     * @return the mark specified by the ID
     */
    public double getGrade(int markID) {       
        
        return activities[markID];
    }
    
    
}
