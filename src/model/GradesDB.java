/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import db.DBMaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author iarar
 */
public class GradesDB {
    private Connection conn;
    
    public GradesDB(Connection conn) {
        this.conn = conn;
    }

    public void updateGrades(Double[] grade, int id) {
        PreparedStatement st = null;
        System.out.println(id);
        String query = "UPDATE grade g JOIN student s ON s.gradeid = g.gradeid SET g.activity1 = ?, g.activity2 = ?, g.activity3 = ?, g.activity4 = ?, g.activity5 = ? WHERE s.studentid = ?";
        
        try {           
            st = conn.prepareStatement(query);           
            
            st.setDouble(1, grade[0]);
            st.setDouble(2, grade[1]);
            st.setDouble(3, grade[2]);
            st.setDouble(4, grade[3]);
            st.setDouble(5, grade[4]);
            st.setInt(6, id);

            st.executeUpdate();           
           
        } catch (SQLException e) {
            System.out.println("Student Update");
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }        
    }
}
