package model;

import db.DBMaria;
import entities.Course;
import entities.Parent;
import entities.Student;
import entities.Teacher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Purpose: it will responsible to manage communication between the Teacher
 * class and database By: Iara Santos Date: 01/25/2022
 */
public class StudentsDB {

    private Connection conn;
    
    public StudentsDB(Connection conn) {
        this.conn = conn;
    }
    
    public void update(Student obj) {
        PreparedStatement st = null;
        String SQL = "UPDATE Student SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE studentid = ?";
        String SQLParents = "UPDATE Parent SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE parentid = ?";
        
        try {
            st = conn.prepareStatement(SQL);

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getPhone());
            st.setString(4, obj.getEmail());
            st.setInt(5, obj.getId()); 
            
            
            st.executeUpdate();
            
                
            st.close();
            
            
            st = conn.prepareStatement(SQLParents);            
            
            st.setString(1, obj.getParent()[0].getfName());
            st.setString(2, obj.getParent()[0].getlName());
            st.setString(3, obj.getParent()[0].getPhone());
            st.setString(4, obj.getParent()[0].getEmail());
            st.setInt(5, obj.getParent()[0].getId()); 
            st.executeUpdate();
            
                
            st.close();
            
            
            st = conn.prepareStatement(SQLParents, Statement.RETURN_GENERATED_KEYS);            
            
            st.setString(1, obj.getParent()[1].getfName());
            st.setString(2, obj.getParent()[1].getlName());
            st.setString(3, obj.getParent()[1].getPhone());
            st.setString(4, obj.getParent()[1].getEmail());
            st.setInt(5, obj.getParent()[1].getId());
            st.executeUpdate();
            
                
                st.close();
           
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            
        }
    }
    
    //search for courses student is enrolled by studentid
    public List<Course> searchCoursesEnrolledByID(int id){
        String QUERY = "SELECT course.courseid, course.title, course.year FROM student JOIN enrollment "
                + "ON student.studentid = enrollment.studentid JOIN section ON section.sectionID = enrollment.sectionID "
                + "JOIN Course "
                + "ON section.sectionID = course.sectionID WHERE student.studentid = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            //create list to save results found
            List<Course> list = new ArrayList<>();
            
            while(rs.next()){
                Course course = instantiateCourse(rs);
                list.add(course);
            }
            return list;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    //search student by firstname and lastname
    public Student searchByName(String fname, String lname) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String qStudent = "SELECT * FROM Student WHERE first_name = ? AND last_name = ?";
        String qParents = "SELECT parent.parentid, parent.first_name, parent.last_name, parent.email, parent.phone FROM parent JOIN studentparents ON parent.parentid = studentparents.parentid AND studentparents.studentid = ?";
        try {
            st = conn.prepareStatement(qStudent);

            st.setString(1, fname);
            st.setString(2, lname);
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            Student std = null;
            if (rs.next()) {
                //cria os objetos
                id = rs.getInt(1);
                std = instantiateStudent(rs);                   
            }
            rs.close();
                
            st.close();
            
            st = conn.prepareStatement(qParents);
            st.setInt(1, id);
            rs = st.executeQuery();
            int i = 0;
            Parent[] par = new Parent[2];
            //getting parent data
            while(rs.next()){                
                par[i] = instantiateParent(rs);                
                i++;                
            }
            std.setParent(par);
            return std;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
    

    //insert student into DB istracker
    public void insert(Student obj) {        
        PreparedStatement st = null;
        String SQL = "INSERT INTO Student (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        String SQLParents = "INSERT INTO Parent (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        
        String SQLStdPar = "INSERT INTO StudentParents (studentid, parentid) VALUES (?, ?)";
        try {
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getPhone());
            st.setString(4, obj.getEmail());        
            
            
            int rowsAffected = st.executeUpdate();
            int id = 0;
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    obj.setId(id);
                }  
                rs.close();
                
                st.close();
            }
            
            st = conn.prepareStatement(SQLParents, Statement.RETURN_GENERATED_KEYS);            
            
            st.setString(1, obj.getParent()[0].getfName());
            st.setString(2, obj.getParent()[0].getlName());
            st.setString(3, obj.getParent()[0].getPhone());
            st.setString(4, obj.getParent()[0].getEmail());
            
            int rowsAffectedPar1 = st.executeUpdate();
            if (rowsAffectedPar1 > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    obj.getParent()[0].setId(id);
                } 
                rs.close();
                
                st.close();
            }
            
            st = conn.prepareStatement(SQLParents, Statement.RETURN_GENERATED_KEYS);            
            
            st.setString(1, obj.getParent()[1].getfName());
            st.setString(2, obj.getParent()[1].getlName());
            st.setString(3, obj.getParent()[1].getPhone());
            st.setString(4, obj.getParent()[1].getEmail());
            
            int rowsAffectedPar2 = st.executeUpdate();
            if (rowsAffectedPar2 > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    obj.getParent()[1].setId(id);
                }  
                rs.close();
                
                st.close();
            }
            
            st = conn.prepareStatement(SQLStdPar, Statement.RETURN_GENERATED_KEYS);            
            
            st.setInt(1, obj.getId());
            st.setInt(2, obj.getParent()[0].getId());
            
            int rows = st.executeUpdate();
            
            st = conn.prepareStatement(SQLStdPar, Statement.RETURN_GENERATED_KEYS);            
            
            st.setInt(1, obj.getId());
            st.setInt(2, obj.getParent()[1].getId());
            
            int rowsA = st.executeUpdate();           
            
            
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            
        }
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
    
    //create one Parent object
    private Parent instantiateParent(ResultSet rs) {
        Parent parent = new Parent();
        try {
            
            parent.setId(rs.getInt("parentid"));
            parent.setfName(rs.getString("first_name"));
            parent.setlName(rs.getString("last_name"));
            parent.setEmail(rs.getString("email"));
            parent.setPhone(rs.getString("phone"));           
            return parent;
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    //create one Student object
    public static Student instantiateStudent(ResultSet rs) {
        Student student = new Student();
        try {
            student.setId(rs.getInt("studentid"));
            student.setfName(rs.getString("first_name"));
            student.setlName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));            
            
            return student;
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }



    public List<Student> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Student ORDER BY first_name");
            rs = st.executeQuery();

            //cria lista de resultados
            List<Student> list = new ArrayList<>();

            while (rs.next()) {
                Student student = instantiateStudent(rs);
                
                list.add(student);
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

}
