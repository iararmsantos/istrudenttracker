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
    private int year;
    private Section section;
    
    private static int count = 0;

    public Course() {
        setId(++count);
    }

    public Course(String title, int year) {
        
        this.title = title;
        this.year = year;
        setId(++count);
    }

    public Course(String title, int year, Section section) {        
        this.title = title;
        this.year = year;
        this.section = section;
        setId(++count);
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }       

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Course.count = count;
    }

    @Override
    public String toString() {
        return  title + " - " + year;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    
    
}
