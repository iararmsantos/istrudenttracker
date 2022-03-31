/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.manager;

import db.DBMaria;
import entities.Teacher;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.CoursesDB;
import model.TeachersDB;

/**
 *
 * @author iarar
 */
public class TeacherManagerDB {
    public static void updateTeacher(Teacher tec) {
        //connect to database
        TeachersDB manager = new TeachersDB(DBMaria.getConnection());
        if(manager.update(tec)){
            JOptionPane.showMessageDialog(null, "Teacher Updated!");
        }else{
            JOptionPane.showMessageDialog(null, "Something got wrong while trying update teacher. Try again!");
        }
    }

    //to save or update teachers into database
    public static void insertTeachers(Teacher tec) {
        Teacher teacher = new Teacher();
        //connect to database
        TeachersDB manager = new TeachersDB(DBMaria.getConnection());
        teacher = getTeacherByName(tec.getfName(), tec.getlName());
        
        if(teacher == null){
            manager.insert(tec);
            //update nextvalue for 'tch'
            CoursesDB cman = new CoursesDB(DBMaria.getConnection());
            int nextid = cman.findNextId("tch") + 1;
            cman.updateNextValue(nextid, "tch");
            JOptionPane.showMessageDialog(null, "Teacher Inserted!");
        }else{
            JOptionPane.showMessageDialog(null, "Teacher Already Exist!");
        }
    }
    
    //find all teachers registered 
    public static List<Teacher> getTeachersList() {        
        //connect to database
        List<Teacher> list = new ArrayList<>();
        TeachersDB manager = new TeachersDB(DBMaria.getConnection());
        list = manager.findAll();
        return list;
    }

    //find teacher by first name and last name
    public static Teacher getTeacherByName(String fname, String lname) {
        Teacher teacher = new Teacher();
        //connect to database
        TeachersDB manager = new TeachersDB(DBMaria.getConnection());
        teacher = manager.searchByName(fname, lname);
        return teacher;
    }
    //receive teacher data and delete it from the database
     public static void deleteTeacher(Teacher obj) {
         //connect to database
        TeachersDB manager = new TeachersDB(DBMaria.getConnection());
        manager.delete(obj);
        JOptionPane.showMessageDialog(null, "Teacher Deleted!");
    }
}
