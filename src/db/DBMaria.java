package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBMaria {

    private static Connection c = null;

    public static Connection getConnection() {
        if (c == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Books",
            "root", "");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DBMaria.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Opened database successfully");
        }
        return c;
    }

    public static void closeConnection() {
        if (c != null) {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }    

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
