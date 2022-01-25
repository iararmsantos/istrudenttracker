package model;

import db.DBPostgres;
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

    public void insert(Teacher obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO Teacher "
                    + "(fName, lName, email) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getEmail());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DBPostgres.closeResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
        }
    }

    public void update(Teacher obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE Teacher SET fName = ?, lName = ?, email = ? "
                    + "WHERE Id = ?");

            st.setString(1, obj.getfName());
            st.setString(2, obj.getlName());
            st.setString(3, obj.getEmail());
            st.setInt(4, obj.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
        }
    }

    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM Teacher WHERE Id = ?");
            st.setInt(1, id);
            int rows = st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
        }
    }

    public Teacher findById(Integer id) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Teacher WHERE Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();
            //o resultset retorna uma tabela e na OO trabalhamos com UML
            //devemos transformar a tabela em objeto
            //testar se veio resultado
            if (rs.next()) {
                //cria os objetos
                Teacher teacher = instantiateTeacher(rs);

                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
            DBPostgres.closeResultSet(rs);
        }
        return null;
    }

    public List<Teacher> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();

            //cria lista de resultados
            List<Teacher> list = new ArrayList<>();

            while (rs.next()) {
                Teacher teacher = instantiateTeacher(rs);
                teacher.setId(rs.getInt("Id"));
                teacher.setfName(rs.getString("fName"));
                teacher.setfName(rs.getString("lName"));
                list.add(teacher);
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
            DBPostgres.closeResultSet(rs);
        }
        return null;
    }

    private Teacher instantiateTeacher(ResultSet rs) {
        Teacher teacher = new Teacher();
        try {
            teacher.setId(rs.getInt("Id"));
            teacher.setfName(rs.getString("fName"));
            teacher.setlName(rs.getString("lName"));
        } catch (SQLException ex) {
            Logger.getLogger(TeachersDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return teacher;
    }

    public Teacher findByName(String name) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM department WHERE Name = ?");

            st.setString(1, name);
            rs = st.executeQuery();
            //o resultset retorna uma tabela e na OO trabalhamos com UML
            //devemos transformar a tabela em objeto
            //testar se veio resultado
            if (rs.next()) {
                //cria os objetos
                Teacher dep = instantiateTeacher(rs);

                return dep;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBPostgres.closeStatement(st);
            DBPostgres.closeResultSet(rs);
        }
        return null;
    }

}
