
package model;

import db.DBMaria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import entities.Course;
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
    
    
    
    
}
