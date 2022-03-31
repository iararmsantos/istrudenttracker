/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.manager;

import db.DBMaria;
import entities.Course;
import entities.Grade;
import entities.Section;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.CoursesDB;
import model.GradesDB;
import model.StudentsDB;

/**
 *
 * @author iarar
 */
public class CourseManagerDB {
    //find grades and 
    public static Grade getStudentGradeByCourse(int stdId, int courseId) {
        Grade gradeTemp = new Grade();
        GradesDB manager = new GradesDB(DBMaria.getConnection());
        gradeTemp = manager.findGradesByStdCourse(stdId, courseId);
        return gradeTemp;
    }
    
    //search courses students is enrolled
    public static List<Course> getCoursesEnrolled(int id) {
        List<Course> crs = new ArrayList<>();
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        crs = manager.searchCoursesEnrolledByID(id);
        return crs;
    }
    //find list of courses by title
    public static List<Course> findCoursesByTitle(String title) {
        List<Course> list = new ArrayList<>();
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        list = manager.findByTitle(title);
        return list;       
    }    
    
    //receive a course and student data and dismiss it from the course
    public static void dismissStudent(Student std) {
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        manager.dismissStudent(std);
        JOptionPane.showMessageDialog(null, "Student Dismissed!");
    }
    //receive the course and section data and delete it from database
    public static void deleteCourse(Section section) {
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        manager.delete(section);
        JOptionPane.showMessageDialog(null, "Course Deleted!");
    }
    
    //enroll student using studentid and course data
    public static void enrollStudent(int id, Course courseTemp) {
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        manager.enrollStudent(id, courseTemp);
        JOptionPane.showMessageDialog(null, "Student Enrolled!");
    }
    //insert new grades into db
    public static void updateGradesDB(Double[] grade, int id, Course obj) {
        GradesDB manager = new GradesDB(DBMaria.getConnection());
        manager.updateGrades(grade, id, obj);
    }

    //find all courses registered 
    public static List<Course> getCoursesList() {
        List<Course> list = new ArrayList<>();
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        list = manager.findAll();

        return list;
    }
    
    //find all sections registered 
    public static List<Section> getSectionList() {
        List<Section> list = new ArrayList<>();
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        list = manager.findAllSections();

        return list;
    }

    //find section by course courseId - get teacher and semester data
    public static Section getSection(int courseId) {
        Section section = new Section();
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        section = manager.getSection(courseId);
        return section;
    }
    
    //receive teacher courseId and find courses teacher teachs
    public static List<Course> getCoursesTaught(int id) {
        List<Course> list = new ArrayList<>();
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        list = manager.findCoursesTaught(id);
        
        return list;
    }

    //find course using title and year as reference
    public static Course getCourseByTitleYear(String title, int year) {
        Course course = new Course();
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        course = manager.searchCoursesByTitleYear(title, year);
        
        return course;
    }

    //to save or update courses into database
    public static void insertCourses(Course cour) { 
        Course crs = new Course();
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        //verify if course already exist
        crs = getCourseByTitleYear(cour.getTitle(), cour.getYear());
        int nextid = manager.findNextId("crs") + 1;
        if(crs == null){
            manager.insertCourse(cour);
            JOptionPane.showMessageDialog(null, "Course Inserted!");
            //update nextvalue for 'crs'
            manager.updateNextValue(nextid, "crs");
            //update nextvalue for 'sec'
        }else{
            JOptionPane.showMessageDialog(null, "Course Already Exist!");
        }
    }
    
    public static void updateCourse(Course obj) {
        //connect to database
        CoursesDB manager = new CoursesDB(DBMaria.getConnection());
        if(manager.update(obj)){
            JOptionPane.showMessageDialog(null, "Course Updated!");
        }else{
            JOptionPane.showMessageDialog(null, "Something got wrong while trying update course. Try again!");
        }        
    }
}
