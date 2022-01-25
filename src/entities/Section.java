
package entities;

/**
 * Purpose: this class will be used to connect Teacher, Students, Courses, and Grade
 * it will contain the semester and sectionID
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class Section {
    private int sectionId;
    private int semester;

    public Section() {
    }

    public Section(int sectionId, int semester) {
        this.sectionId = sectionId;
        this.semester = semester;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
    
    
}


