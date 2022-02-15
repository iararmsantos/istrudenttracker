
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
    private Teacher teacher;      
    
    private static int count = 0;

    public Section() {
        setSectionId(++count);
    }

    public Section(Semester semester) {        
        this.semester = semester;
        setSectionId(++count);
    }

    public Section(Semester semester, Teacher teacher) {
        this.semester = semester;
        this.teacher = teacher;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    

}


