
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
    public List<Student> findStudentsByCourse(Course course) {
        List<Student> list = new ArrayList<>();
        Grade[] grade = new Grade[5];
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT student.studentid, student.first_name, student.last_name, student.email, student.phone, grade.gradeid, grade.activity1, grade.activity2, grade.activity3,     grade.activity4, grade.activity5 "
                + "FROM Student "
                + "JOIN Enrollment ON student.studentID = Enrollment.EnrollID "
                + "JOIN Course ON Enrollment.EnrollID = Course.sectionID "
                + "JOIN Grade ON Course.sectionId = Grade.gradeId "
                + "WHERE Course.courseID = ?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, course.getId());
            rs = st.executeQuery();
            //st.setInt(2, obj.getParent()[1].getId());

            while (rs.next()) {
                Student std = StudentsDB.instantiateStudent(rs);
                Grade grd = instantiateGrade(rs);
                std.setGrades(grd);
                list.add(std);
                
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
                    "SELECT * FROM Course ORDER BY title");
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
            if (rs.next()) {
                //cria os objetos
                
                section = instantiateSection(rs);                   
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
                
    //get list of students in a course
    public List<Student> findCoursesEnrolled(Integer id) {
        List<Student> std = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT student.studentid, student.first_name, student.last_name, student.email, student.phone, course.title, course.year FROM Student JOIN Enrollment ON student.studentID = Enrollment.enrollID JOIN Course ON Enrollment.sectionId = Course.sectionID WHERE Course.courseID = ?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Student student = StudentsDB.instantiateStudent(rs);
                std.add(student);
            }
            return std;

        } catch (SQLException e) {
            e.printStackTrace();
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
        Teacher teacher = new Teacher();
        try{
            section.setSectionId(rs.getInt("sectionid"));
            section.setSemester(Semester.valueOf(rs.getString("semester").toUpperCase()));
            teacher = TeachersDB.instantiateTeacher(rs);
            section.setTeacher(teacher);
            
            return section;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    //create one grade obejct
    private Grade instantiateGrade(ResultSet rs) {
        Grade grade = new Grade();
        try{
            grade.setGradeId(rs.getInt("gradeID"));
            grade.setGrade(0, rs.getDouble("activity1"));
            grade.setGrade(1, rs.getDouble("activity2"));
            grade.setGrade(2, rs.getDouble("activity3"));
            grade.setGrade(3, rs.getDouble("activity4"));
            grade.setGrade(4, rs.getDouble("activity5"));
            
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
        String SQLSection = "INSERT INTO Section(teacherID, semester) VALUES(?, ?)";
        String SQLCourse = "INSERT INTO Course(sectionID, title, year) VALUES(?, ?, ?)";
        try{
            st = conn.prepareStatement(SQLSection, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getSection().getTeacher().getId());
            st.setString(2, obj.getSection().getSemester().toString());
            st.executeUpdate();
            st.close();
            
            st = conn.prepareStatement(SQLCourse, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getSection().getSectionId());
            st.setString(2, obj.getTitle());
            st.setInt(3, obj.getYear());
            
            st.executeUpdate();
            st.close();
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            DBMaria.closeStatement(st);
        }
    }
/*String query = "SELECT student.studentid, student.first_name, student.last_name, grade.activity FROM\n"
                + "Student JOIN Enrollment ON student.studentID = Enrollment.EnrollmentID \n"
                + "JOIN Course ON Enrollment.EnrollmentID = Course.sectionID\n"
                + "JOIN Grade ON Course.sectionId = Grade.gradeId\n"
                + "WHERE Course.courseID = ?";*/

    

}

//source get enum using resutset: https://stackoverflow.com/questions/65197006/saving-and-reading-the-enum-value-to-the-database-with-jdbc
