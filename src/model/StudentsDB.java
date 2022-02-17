package model;

import db.DBMaria;
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

    //to call: TeachersDB tDB = new TeachersDB(DBPosgres.getConnection());
    public StudentsDB(Connection conn) {
        this.conn = conn;
    }
    
    //search student by firstname and lastname
    public Student searchByName(String fname, String lname) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Student WHERE first_name = ? AND last_name = ?");

            st.setString(1, fname);
            st.setString(2, lname);
            rs = st.executeQuery();
            //o resultset retorna uma tabela e na OO trabalhamos com UML
            //devemos transformar a tabela em objeto
            //testar se veio resultado
            if (rs.next()) {
                //cria os objetos
                Student std = instantiateStudent(rs);
                System.out.println("StudentsDB" + std.getfName());
                return std;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }

    public List<Student> findbyCourseEnrolled(Integer id) {
        List<Student> std = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT student.studentid, student.first_name, student.last_name, grade.activity FROM\n"
                + "Student JOIN Enrollment ON student.studentID = Enrollment.EnrollmentID \n"
                + "JOIN Course ON Enrollment.EnrollmentID = Course.sectionID\n"
                + "JOIN Grade ON Course.sectionId = Grade.gradeId\n"
                + "WHERE Course.courseID = ?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Student student = instantiateStudent(rs);
                std.add(student);
            }
            return std;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

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
            int std_id = 0;
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
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
                    int id = rs.getInt(1);
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
                    int id = rs.getInt(1);
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
    
    private Student instantiateStudent(ResultSet rs) {
        Student student = new Student();
        try {
            student.setId(rs.getInt("studentid"));
            student.setfName(rs.getString("first_name"));
            student.setlName(rs.getString("last_name"));
            student.setEmail(rs.getString("email"));
            student.setPhone(rs.getString("phone"));
            
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return student;
    }

//    public void update(Teacher obj) {
//        PreparedStatement st = null;
//        try {
//            st = conn.prepareStatement(
//                    "UPDATE Teacher SET fName = ?, lName = ?, email = ? "
//                    + "WHERE Id = ?");
//
//            st.setString(1, obj.getfName());
//            st.setString(2, obj.getlName());
//            st.setString(3, obj.getEmail());
//            st.setInt(4, obj.getId());
//
//            st.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBMaria.closeStatement(st);
//        }
//    }
//
//    public void deleteById(Integer id) {
//        PreparedStatement st = null;
//        try {
//            st = conn.prepareStatement(
//                    "DELETE FROM Teacher WHERE Id = ?");
//            st.setInt(1, id);
//            int rows = st.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBMaria.closeStatement(st);
//        }
//    }
//
//    public Teacher findById(Integer id) {
//        //alterar banco de dados
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.prepareStatement(
//                    "SELECT * FROM Teacher WHERE Id = ?");
//
//            st.setInt(1, id);
//            rs = st.executeQuery();
//            //o resultset retorna uma tabela e na OO trabalhamos com UML
//            //devemos transformar a tabela em objeto
//            //testar se veio resultado
//            if (rs.next()) {
//                //cria os objetos
//                Teacher teacher = instantiateTeacher(rs);
//
//                return teacher;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBMaria.closeStatement(st);
//            DBMaria.closeResultSet(rs);
//        }
//        return null;
//    }
//
//    public List<Teacher> findAll() {
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.prepareStatement(
//                    "SELECT * FROM department ORDER BY Name");
//            rs = st.executeQuery();
//
//            //cria lista de resultados
//            List<Teacher> list = new ArrayList<>();
//
//            while (rs.next()) {
//                Teacher teacher = instantiateTeacher(rs);
//                teacher.setId(rs.getInt("Id"));
//                teacher.setfName(rs.getString("fName"));
//                teacher.setfName(rs.getString("lName"));
//                list.add(teacher);
//            }
//
//            return list;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBMaria.closeStatement(st);
//            DBMaria.closeResultSet(rs);
//        }
//        return null;
//    }
//
//    private Teacher instantiateTeacher(ResultSet rs) {
//        Teacher teacher = new Teacher();
//        try {
//            teacher.setId(rs.getInt("Id"));
//            teacher.setfName(rs.getString("fName"));
//            teacher.setlName(rs.getString("lName"));
//        } catch (SQLException ex) {
//            Logger.getLogger(StudentsDB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return teacher;
//    }
//
//    public Teacher findByName(String name) {
//        //alterar banco de dados
//        PreparedStatement st = null;
//        ResultSet rs = null;
//        try {
//            st = conn.prepareStatement(
//                    "SELECT * FROM department WHERE Name = ?");
//
//            st.setString(1, name);
//            rs = st.executeQuery();
//            //o resultset retorna uma tabela e na OO trabalhamos com UML
//            //devemos transformar a tabela em objeto
//            //testar se veio resultado
//            if (rs.next()) {
//                //cria os objetos
//                Teacher dep = instantiateTeacher(rs);
//
//                return dep;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBMaria.closeStatement(st);
//            DBMaria.closeResultSet(rs);
//        }
//        return null;
//    }
}
