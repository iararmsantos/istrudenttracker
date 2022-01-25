/*
 * Purpose: This class will represent the Course entitie in the iStudent Tracker program.
 * It will contain the id an title properties of each course
 * By: Iara Santos
 * Date: 01/25/2022
 */
package entities;

public class Course {
    private int id;
    private String title;

    public Course() {
    }

    public Course(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    
}
