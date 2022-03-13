/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import db.DBMaria;
import entities.Course;
import entities.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iarar
 */
public class GradesDB {
    private Connection conn;
    
    public GradesDB(Connection conn) {
        this.conn = conn;
    }

    public void updateGrades(Double[] grade, int id, Course obj) {
        PreparedStatement st = null;
        String query = "UPDATE grade g JOIN student s ON s.studentid = g.studentid SET g.activity1 = ?, g.activity2 = ?, g.activity3 = ?, g.activity4 = ?, g.activity5 = ?, g.average = ? WHERE g.studentid = ? AND g.sectionid = ?";
        
        try {           
            st = conn.prepareStatement(query);           
            
            st.setDouble(1, grade[0]);
            st.setDouble(2, grade[1]);
            st.setDouble(3, grade[2]);
            st.setDouble(4, grade[3]);
            st.setDouble(5, grade[4]);
            st.setDouble(6, grade[5]);
            st.setInt(7, id);
            st.setInt(8, obj.getSection().getSectionId());
            st.executeUpdate();           
           
        } catch (SQLException e) {            
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }        
    }

    public Grade findGradesByStdCourse(int stdId, int courseId) {
        ResultSet rs = null;
        PreparedStatement st = null;
        String query = "SELECT course.courseid, grade.gradeid, grade.activity1, grade.activity2, grade.activity3, grade.activity4, grade.activity5, grade.average FROM grade JOIN course ON grade.sectionid = course.sectionid WHERE grade.studentid = ? AND courseId = ?";
        Grade g = null;
        try{
            st = conn.prepareStatement(query);  
            st.setInt(1, stdId);
            st.setInt(2, courseId);
            rs = st.executeQuery(); 
            if(rs.next()) {
                //create grade objects                  
                g = instantiateGrade(rs);
            }
            
            return g;
        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }        
        return null;        
    }

    private Grade instantiateGrade(ResultSet rs) {
        Grade grade = new Grade();
        double[] grd = new double[6];
        try{
            grade.setGradeId(rs.getInt("gradeid"));
            grd[0] = rs.getDouble("activity1");
            grd[1] = rs.getDouble("activity2");
            grd[2] = rs.getDouble("activity3");
            grd[3] = rs.getDouble("activity4");
            grd[4] = rs.getDouble("activity5");
            grd[5] = rs.getDouble("average");
            grade.setActivities(grd);
            
            return grade;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
