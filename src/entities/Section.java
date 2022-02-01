
package entities;

import java.util.List;

/**
 * Purpose: this class will be used to connect Teacher, Students, Courses, and Grade
 * it will contain the semester and sectionID
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class Section {
    private int sectionId;
    private Semester semester;
    List<Grade> grades;
    List<Enrollment> enroll;
    
    
    private static int count = 0;

    public Section() {
        setSectionId(++count);
    }

    public Section(int sectionId, Semester semester) {
        this.sectionId = sectionId;
        this.semester = semester;
        List<Grade> grades;
        setSectionId(++count);
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Section.count = count;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }   

    public List<Enrollment> getEnroll() {
        return enroll;
    }

    public void setEnroll(List<Enrollment> enroll) {
        this.enroll = enroll;
    }
    
}


