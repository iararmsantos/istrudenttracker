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
    
    //find all teachers
    public List<Teacher> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Teacher ORDER BY first_name");
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
    private Teacher instantiateTeacher(ResultSet rs) {
        Teacher teacher = new Teacher();
        try {
            teacher.setId(rs.getInt("teacherid"));
            teacher.setfName(rs.getString("first_name"));
            teacher.setlName(rs.getString("last_name"));
            teacher.setEmail(rs.getString("email"));
            teacher.setPhone(rs.getString("phone"));            
            
            return teacher;
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
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

//

//

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
