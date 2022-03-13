/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.manager;

import db.DBMaria;
import entities.Course;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.StudentsDB;

/**
 *
 * @author iarar
 */
public class StudentManagerDB {   
    
    //find students enrolled in a course
    public static List<Student> findCourseEnrolled(int id) {
        List<Student> list = new ArrayList<>();
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findCoursesEnrolled(id);
        return list;
    }
    
    //search students by first name and last name
    public static Student getStudentByName(String fname, String lname) {   
        Student std = new Student();
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        std = manager.searchByName(fname, lname);

        return std;
    }
    //find list of students by last name
    public static List<Student> findStudentsByLastName(String lname) {
        List<Student> list = new ArrayList<>();        
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findByLastName(lname);
        return list;       
    }
    
    //find list of students by first name
    public static List<Student> findStudentsByFirstName(String fname) {
        List<Student> list = new ArrayList<>();
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findByFirstName(fname);
        return list;       
    }
    //receive student data and delete it on the database
    public static void deleteStudent(Student obj) {
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        manager.delete(obj);
        JOptionPane.showMessageDialog(null, "Student Deleted!");
    }
    
    //find all students registered 
    public static List<Student> getStudentsList() {
        List<Student> list = new ArrayList<>();
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findAll();

        return list;
    }
    
    public static void updateStudent(Student std) {
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        manager.update(std);
        JOptionPane.showMessageDialog(null, "Student Updated!");
    }

    //to save or update students into database
    public static void insertStudents(Student std) {        
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        manager.insert(std);
        JOptionPane.showMessageDialog(null, "Student Inserted!");
    }
    
    //find students by course
    public static List<Student> getStudentsByCourse(Course crs) {
        List<Student> list = new ArrayList<>();
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findStudentsByCourse(crs);  
        return list;
    }
}
