package db.manager;

import db.DBMaria;
import entities.Course;
import entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.CoursesDB;
import model.StudentsDB;

/**
 * Purpose: it will be responsible to manage communication between Home and
 * StudentsDB class and database By: Iara Santos Date: 03/17/2022
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
        if(manager.update(std)){
            JOptionPane.showMessageDialog(null, "Student Updated!");
        }else{
            JOptionPane.showMessageDialog(null, "Something got wrong while trying update student. Try again!");
        }
        
    }

    //to save or update students into database
    public static void insertStudents(Student student) {
        Student stu = new Student();
        //connect to database
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        CoursesDB crs = new CoursesDB(DBMaria.getConnection());
        stu = getStudentByName(student.getfName(), student.getlName());
        int nextid = crs.findNextId("stu") + 1;
        if (stu.getfName() == null && stu.getlName() == null) {
            manager.insert(student);
            //update nextvalue for 'stu'
            crs.updateNextValue(nextid, "stu");
            JOptionPane.showMessageDialog(null, "Student Inserted!");
        } else {
            JOptionPane.showMessageDialog(null, "Student Already exist!");
        }
    }

    //find students by course
    public static List<Student> getStudentsByCourse(Course crs) {
        List<Student> list = new ArrayList<>();
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        list = manager.findStudentsByCourse(crs);
        return list;
    }

    public static List<String> getStudentNotes(int stdId) {
        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
        List<String> notes = new ArrayList<>();
        notes = manager.findNoteById(stdId);
        if(notes.size() > 0){
            return notes;
        }else{
            return null;
        }
        
    }

//    public static void addStudentNotes(int id) {
//        Student stu = new Student();
//        //connect to database
//        StudentsDB manager = new StudentsDB(DBMaria.getConnection());
//        stu = getStudentByName(student.getfName(), student.getlName());
//        int nextid = crs.findNextId("stu") + 1;
//        if (stu.getfName() == null && stu.getlName() == null) {
//            manager.insert(student);
//            //update nextvalue for 'stu'
//            crs.updateNextValue(nextid, "stu");
//            JOptionPane.showMessageDialog(null, "Student Inserted!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Student Already exist!");
//        }
//    }
}
