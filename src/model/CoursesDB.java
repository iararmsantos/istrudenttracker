
package model;

import db.DBMaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import entities.Course;
import entities.Grade;
import entities.Section;
import entities.Semester;
import entities.Student;
import entities.Teacher;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 * Purpose: it will responsible to manage communication between the Course class
 * and database
 * By: Iara Santos
 * Date: 02/01/2022
 */
public class CoursesDB {
    private Connection conn;

    public CoursesDB(Connection conn) {
        this.conn = conn;
    }
    
    //find courses by title
    public List<Course> findByTitle(String title) {
        //alterar banco de dados
        List<Course> courses = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT * FROM Course WHERE title = ?";
        try {
            st = conn.prepareStatement(QUERY);

            st.setString(1, title);
            
            rs = st.executeQuery();
            //getting the student data            
            
            while (rs.next()) {
                //cria os objetos and add to the list       
                 
                courses.add(instantiateCourse(rs));
            }
            rs.close();
                
            st.close();            

            return courses;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
    //receive course and student data and dismiss student from the course
    public void dismissStudent(Student std) {
        PreparedStatement st = null;
        String dismissQuery = "DELETE FROM Grade WHERE studentid = ?";
        
        try {
            st = conn.prepareStatement(dismissQuery);
            st.setInt(1, std.getId());                    
            st.executeUpdate();
            st.close();            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);            
        }        
    }
    //receive course and section data to delete it from database
    public void delete(Section section) {
        PreparedStatement st = null;        
        String gradeSQL = "DELETE FROM Grade WHERE sectionid = ?";
        
        String sectionSQL = "DELETE FROM Section WHERE sectionid = ?";
        String courseSQL = "DELETE FROM Course WHERE sectionid = ?";
        try {
            st = conn.prepareStatement(gradeSQL);
            st.setInt(1, section.getSectionId());
            st.executeUpdate();
            st.close();
            
            st = conn.prepareStatement(courseSQL);
            st.setInt(1, section.getSectionId());
            st.executeUpdate();
            st.close();
            
            st = conn.prepareStatement(sectionSQL);
            st.setInt(1, section.getSectionId());
            st.executeUpdate();
            st.close();
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
    }
    
    //receive teacher teacherId and find all courses this teach is enrolled
    public List<Course> findCoursesTaught(int teacherId) {
        List<Course> list = new ArrayList<>(); 
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT course.courseid, section.sectionid, course.title, course.year FROM course\n" +
                       "JOIN section ON course.courseid = section.sectionid\n" +
                       "JOIN teacher ON teacher.teacherid = section.sectionid\n" +
                       "WHERE teacher.teacherid = ?";
        try {
            st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, teacherId);
                     
            rs = st.executeQuery();

            //cria lista de resultados
            while (rs.next()) {
                Course course = instantiateCourse(rs);                
                list.add(course);
            }
            return list;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);            
        }        
        return null;        
    }
    
    //receive student id and course data to enroll student into a section/course and grade
    public void enrollStudent(int id, Course obj) {        
        PreparedStatement st = null;        
        String gradeQuery = "INSERT INTO Grade (sectionid, studentid) VALUES (?, ?)";
        try {
            st = conn.prepareStatement(gradeQuery);
            st.setInt(1, obj.getSection().getSectionId()); 
            st.setInt(2, id); 
            
            st.executeUpdate();  
                
            st.close(); 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);            
        }        
    }
    
    //update dabase with edited data
    public boolean update(Course obj) {
        PreparedStatement st = null;
        String SQLSection = "UPDATE Section SET teacherID = ?, semester = ? WHERE sectionid = ?";
        String SQLCourse = "UPDATE Course SET sectionID = ?, title = ?, year = ? WHERE courseid = ?";
        
        try {
            st = conn.prepareStatement(SQLSection, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getSection().getTeacher().getId());
            st.setString(2, obj.getSection().getSemester().toString());
            st.setInt(3, obj.getSection().getSectionId());
            st.executeUpdate();
            st.close();
            
            st = conn.prepareStatement(SQLCourse, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getSection().getSectionId());
            st.setString(2, obj.getTitle());
            st.setInt(3, obj.getYear());
            st.setInt(4, obj.getId());
            
            int rowsAffected = st.executeUpdate();

            if (rowsAffected < 1) {
                return false;
            }
            st.close();
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
        return true;
    }
    
    //find all sections registered
    public List<Section> findAllSections() {
        PreparedStatement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT section.sectionid, teacher.teacherID, teacher.first_name, teacher.last_name, teacher.phone, teacher.email, section.semester\n" +
                       "FROM Course JOIN Section ON course.sectionID = section.sectionID\n" +
                       "JOIN Teacher ON section.teacherID = teacher.teacherID";
                       
        try {
            st = conn.prepareStatement(QUERY);
            rs = st.executeQuery();

            //cria lista de resultados
            List<Section> list = new ArrayList<>();

            while (rs.next()) {
                Section section = instantiateSection(rs);                
                list.add(section);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
    
    //find all courses registered
    public List<Course> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Course");
            rs = st.executeQuery();

            //cria lista de resultados
            List<Course> list = new ArrayList<>();

            while (rs.next()) {
                Course course = instantiateCourse(rs);                
                list.add(course);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
    
    //receive course id and find section
    public Section getSection(int id){
        String QUERY = "SELECT section.sectionid, teacher.teacherID, teacher.first_name, teacher.last_name, teacher.phone, teacher.email, section.semester\n" +
                       "FROM Course JOIN Section ON course.sectionID = section.sectionID\n" +
                       "JOIN Teacher ON section.teacherID = teacher.teacherID\n" +
                       "WHERE course.courseID = ?";
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(QUERY);
            st.setInt(1, id);            
            rs = st.executeQuery();
            //getting the student data            
            Section section = null;
            Teacher teacher = null;
            if (rs.next()) {
                //cria os objetos                
                section = instantiateSection(rs);  
                teacher = TeachersDB.instantiateTeacher(rs);
                section.setTeacher(teacher);                
            }
            rs.close();                
            st.close();         

            return section;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;    
}
                
    //search course by title and year
    public Course searchCoursesByTitleYear(String title, int year) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT * FROM Course WHERE title = ? AND year = ?";
        try {
            st = conn.prepareStatement(QUERY);

            st.setString(1, title);
            st.setInt(2, year);
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            Course course = null;
            if (rs.next()) {
                //cria os objetos
                id = rs.getInt(1);
                course = instantiateCourse(rs);                   
            }
            rs.close();
                
            st.close();            

            return course;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
    private Section instantiateSection(ResultSet rs) {
        Section section = new Section();
        //Teacher teacher = new Teacher();
        try{
            section.setSectionId(rs.getInt("sectionid"));
            section.setSemester(Semester.valueOf(rs.getString("semester").toUpperCase()));
            //TeachersDB.instantiateTeacher(rs);
            //section.setTeacher(teacher);
            
            return section;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    //create one grade obejct
    public static Grade instantiateGrade(ResultSet rs) {
        Grade grade = new Grade();
        try{
            grade.setGradeId(rs.getInt("gradeID"));
            grade.setGrade(0, rs.getDouble("activity1"));
            grade.setGrade(1, rs.getDouble("activity2"));
            grade.setGrade(2, rs.getDouble("activity3"));
            grade.setGrade(3, rs.getDouble("activity4"));
            grade.setGrade(4, rs.getDouble("activity5"));
            grade.setGrade(5, rs.getDouble("average"));
            
            return grade;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    //create one course object
    private Course instantiateCourse(ResultSet rs) {
        Course course = new Course();
        try{
            course.setId(rs.getInt("courseid"));
            course.setTitle(rs.getString("title"));
            course.setYear(rs.getInt("year"));  
            
            return course;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public void insertCourse(Course obj){
        PreparedStatement st = null;
        String SQLSection = "INSERT INTO Section(sectionID, teacherID, semester) VALUES(?, ?, ?)";
        String SQLCourse = "INSERT INTO Course(courseid, sectionID, title, year) VALUES(?, ?, ?, ?)";
        try{
            st = conn.prepareStatement(SQLSection, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getSection().getSectionId());
            st.setInt(2, obj.getSection().getTeacher().getId());
            st.setString(3, obj.getSection().getSemester().toString());
            int rowsAffected = st.executeUpdate();
            int id = 0;            
                
            st.close();            
            
            st = conn.prepareStatement(SQLCourse, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getId());
            st.setInt(2, obj.getSection().getSectionId());
            st.setString(3, obj.getTitle());
            st.setInt(4, obj.getYear());
            
            st.executeUpdate();
            st.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            DBMaria.closeStatement(st);
        }
    }

    public int findNextId(String tch) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT * FROM nextid WHERE idtype = ?";
        try {
            st = conn.prepareStatement(QUERY);

            st.setString(1, tch);
            
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            if (rs.next()) {
                //cria os objetos
                id = rs.getInt("nextvalue");
                                   
            }
            rs.close();
                
            st.close();            

            return id;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return 0;
    }
    public void updateNextValue(int id, String crs) {
        String SQL = "UPDATE nextid SET nextvalue= ? WHERE idtype = ?";
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, id);
            st.setString(2, crs);
            st.executeUpdate();
            st.close();         
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
    }
    
}

//source get enum using resutset: https://stackoverflow.com/questions/65197006/saving-and-reading-the-enum-value-to-the-database-with-jdbc
