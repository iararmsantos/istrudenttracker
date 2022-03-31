package model;

import db.DBMaria;
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
 * Purpose: it will responsible to manage communication between the Teacher class
 * and database
 * By: Iara Santos
 * Date: 01/25/2022
 */
public class TeachersDB {    

    private Connection conn;

    //to call: TeachersDB tDB = new TeachersDB(DBPosgres.getConnection());
    public TeachersDB(Connection conn) {
        this.conn = conn;
    }
    //receive teacher and delete it from the database
    public void delete(Teacher obj) {
        PreparedStatement st = null;        
        String sectionSQL = "DELETE FROM Section WHERE teacherid = ?";
        String teacherSQL = "DELETE FROM Teacher WHERE teacherid = ?";
        try {
            st = conn.prepareStatement(sectionSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(teacherSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
    }
    //update dabase with edited data
    public boolean update(Teacher obj) {
        PreparedStatement st = null;
        String SQL = "UPDATE Teacher SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE teacherid = ?";
        try {
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getPhone());
            st.setString(4, obj.getEmail());
            st.setInt(5, obj.getId());             
            
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
    
    //find all teachers
    public List<Teacher> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Teacher");
            rs = st.executeQuery();

            //cria lista de resultados
            List<Teacher> list = new ArrayList<>();

            while (rs.next()) {
                Teacher teacher = instantiateTeacher(rs);
                
                list.add(teacher);
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
    
    //find teacher by first and last name  in istracker
    public Teacher searchByName(String fname, String lname) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String QUERY = "SELECT * FROM Teacher WHERE first_name = ? AND last_name = ?";
        try {
            st = conn.prepareStatement(QUERY);

            st.setString(1, fname);
            st.setString(2, lname);
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            Teacher teacher = null;
            if (rs.next()) {
                //cria os objetos
                id = rs.getInt(1);
                teacher = instantiateTeacher(rs);                   
            }
            rs.close();
                
            st.close();            

            return teacher;
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }

    //insert teacher into istracker
    public void insert(Teacher obj) {
        PreparedStatement st = null;
        String SQL = "INSERT INTO Teacher (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        try {
            st = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getPhone());
            st.setString(4, obj.getEmail());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DBMaria.closeResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
    }
    public static Teacher instantiateTeacher(ResultSet rs) {
        Teacher teacher = new Teacher();
        try {
            teacher.setId(rs.getInt("teacherid"));
            teacher.setfName(rs.getString("first_name"));
            teacher.setlName(rs.getString("last_name"));            
            teacher.setPhone(rs.getString("phone"));  
            teacher.setEmail(rs.getString("email"));
            
            return teacher;
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
