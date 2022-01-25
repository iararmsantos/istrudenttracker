/*
 * Purpose: this class will be responsible to enroll students in courses and 
 * semesters
 * By: Iara Santos
 * Date: 01/25/2022
 */
package entities;

public class Enrollment {
    private Student stdId;
    private Section secId;

    public Enrollment() {
    }

    public Enrollment(Student stdId, Section secId) {
        this.stdId = stdId;
        this.secId = secId;
    }

    public Student getStdId() {
        return stdId;
    }

    public void setStdId(Student stdId) {
        this.stdId = stdId;
    }

    public Section getSecId() {
        return secId;
    }

    public void setSecId(Section secId) {
        this.secId = secId;
    }
    
    
}
