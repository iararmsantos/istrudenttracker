package model;

import db.DBMaria;
import entities.Course;
import entities.Grade;
import entities.Parent;
import entities.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static model.CoursesDB.instantiateGrade;

/**
 * Purpose: it will be responsible to manage communication between StudentManager and the database
 * class and database 
 * By: Iara Santos 
 * Date: 03/17/2022
 */
public class StudentsDB {

    private Connection conn;

    public StudentsDB(Connection conn) {
        this.conn = conn;
    }

    //get list of students in a course
    public List<Student> findCoursesEnrolled(Integer id) {
        List<Student> std = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT student.studentid, student.first_name, student.last_name, student.email, student.phone, course.title, course.year FROM Student JOIN grade ON grade.studentid = student.studentid\n"
                + "JOIN Course ON course.sectionID = grade.sectionid WHERE Course.courseID = ?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                Student student = StudentsDB.instantiateStudent(rs);
                std.add(student);
            }
            return std;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> findStudentsByCourse(Course course) {
        List<Student> list = new ArrayList<>();
        PreparedStatement st = null;
        ResultSet rs = null;
        String query = "SELECT student.studentid, student.first_name, student.last_name, student.email, student.phone, grade.gradeid, grade.activity1, grade.activity2, grade.activity3, grade.activity4, grade.activity5, grade.average\n"
                + "FROM Student \n"
                + "JOIN grade ON grade.studentid = student.studentid \n"
                + "JOIN Course ON course.sectionID = grade.sectionid\n"
                + "WHERE Course.courseID = ?";
        try {
            st = conn.prepareStatement(query);
            st.setInt(1, course.getId());
            rs = st.executeQuery();
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

    //search list of students by first name
    public List<Student> findByLastName(String lname) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String qStudent = "SELECT * FROM Student WHERE last_name = ?";
        String qParents = "SELECT parent.parentid, parent.first_name, parent.last_name, parent.email, parent.phone FROM parent JOIN studentparents ON parent.parentid = studentparents.parentid AND studentparents.studentid = ?";
        try {
            st = conn.prepareStatement(qStudent);
            st.setString(1, lname);

            List<Student> students = new ArrayList<>();
            List<Student> list = new ArrayList<>();
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            Student std = null;
            while (rs.next()) {
                //cria os objetos
                id = rs.getInt(1);
                std = instantiateStudent(rs);
                students.add(std);
            }
            rs.close();
            st.close();

            //students list
            for (int i = 0; i < students.size(); i++) {
                st = conn.prepareStatement(qParents);
                st.setInt(1, students.get(i).getId());

                rs = st.executeQuery();
                int j = 0;
                Parent[] par = new Parent[2];
                //getting parent data
                while (rs.next()) {
                    par[j] = instantiateParent(rs);
                    j++;
                }
                students.get(i).setParent(par);
                rs.close();
                st.close();
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }

    //search list of students by first name
    public List<Student> findByFirstName(String fname) {
        //alterar banco de dados
        PreparedStatement st = null;
        ResultSet rs = null;
        String qStudent = "SELECT * FROM Student WHERE first_name = ?";
        String qParents = "SELECT parent.parentid, parent.first_name, parent.last_name, parent.email, parent.phone FROM parent JOIN studentparents ON parent.parentid = studentparents.parentid AND studentparents.studentid = ?";
        try {
            st = conn.prepareStatement(qStudent);
            st.setString(1, fname);

            List<Student> students = new ArrayList<>();
            List<Student> list = new ArrayList<>();
            rs = st.executeQuery();
            //getting the student data
            int id = 0;
            Student std = null;
            while (rs.next()) {
                //cria os objetos
                id = rs.getInt(1);
                std = instantiateStudent(rs);
                students.add(std);
            }
            rs.close();
            st.close();

            //students list
            for (int i = 0; i < students.size(); i++) {
                st = conn.prepareStatement(qParents);
                st.setInt(1, students.get(i).getId());

                rs = st.executeQuery();
                int j = 0;
                Parent[] par = new Parent[2];
                //getting parent data
                while (rs.next()) {
                    par[j] = instantiateParent(rs);
                    j++;
                }
                students.get(i).setParent(par);
            }

            return students;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }
//delete student from database using studentid

    public void delete(Student obj) {
        PreparedStatement st = null;
        String gradeSQL = "DELETE FROM Grade WHERE studentid = ?";
        String stdSQL = "DELETE FROM Student WHERE studentid = ?";
        String parSQL = "DELETE FROM Parent WHERE parentid = ?";
        String parStdSQL = "DELETE FROM StudentParents WHERE studentid = ?";
        String notesSQL = "DELETE FROM notes WHERE studentid = ?";

        try {
            st = conn.prepareStatement(gradeSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(parStdSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(stdSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(parSQL);
            st.setInt(1, obj.getParent()[0].getId());
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(parSQL);
            st.setInt(1, obj.getParent()[1].getId());
            st.executeUpdate();
            st.close();
            
            st = conn.prepareStatement(notesSQL);
            st.setInt(1, obj.getId());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
        }
    }

    public boolean update(Student obj) {
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

    //search for courses student is enrolled by studentid
    public List<Course> searchCoursesEnrolledByID(int id) {
        String QUERY = "SELECT course.courseid, course.title, course.year FROM student JOIN Grade ON grade.studentID = student.studentID\n"
                + "JOIN Section ON grade.sectionID = section.sectionID \n"
                + "JOIN Course ON course.sectionID = section.sectionID\n"
                + "WHERE student.studentid = ?";
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();

            //create list to save results found
            List<Course> list = new ArrayList<>();

            while (rs.next()) {
                Course course = instantiateCourse(rs);
                list.add(course);
            }
            return list;
        } catch (SQLException ex) {
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
            Student std = new Student();
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
            while (rs.next()) {
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
    private Parent par;

    private Parent[] parentExist(Student obj) {
        int count = 0;
        Parent[] parent = new Parent[2];
        for (int i = 0; i < obj.getParent().length; i++) {
            if (findParentByName(obj.getParent()[i].getfName(), obj.getParent()[i].getlName())) {
                parent[i] = par;
                ++count;
            }
        }
        if (count > 1) {
            return parent;
        } else {
            return null;
        }

    }

    //insert student into DB istracker
    public void insert(Student obj) {
        PreparedStatement st = null;
        String SQL = "INSERT INTO Student (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        String SQLParents = "INSERT INTO Parent (first_name, last_name, phone, email) VALUES (?, ?, ?, ?)";
        String SQLStdPar = "INSERT INTO StudentParents (studentid, parentid) VALUES (?, ?)";
        String SQLNotes = "INSERT INTO notes (studentid, note) VALUES (?, ?)";
        Parent[] parent = new Parent[2];
        parent = parentExist(obj);
        
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
            st = conn.prepareStatement(SQLNotes, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getId());
            st.setString(2, obj.getNotes());
            st.executeUpdate();
            st.close();
            if (parent.length > 0) {
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
            }
            st = conn.prepareStatement(SQLStdPar, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getId());
            st.setInt(2, obj.getParent()[0].getId());
            st.executeUpdate();

            st = conn.prepareStatement(SQLStdPar, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, obj.getId());
            st.setInt(2, obj.getParent()[1].getId());
            st.executeUpdate();
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
        try {
            course.setId(rs.getInt("courseid"));
            course.setTitle(rs.getString("title"));
            course.setYear(rs.getInt("year"));

            return course;
        } catch (SQLException ex) {
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

    //get all students from database
    public List<Student> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Student ORDER BY studentid");
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

    private boolean findParentByName(String fName, String lName) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT * FROM Parent WHERE first_name = ? AND last_name = ?");
            st.setString(1, fName);
            st.setString(2, lName);
            rs = st.executeQuery();
            //cria lista de resultados

            if (rs.next()) {
                par = instantiateParent(rs);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return false;
    }

    public List<String> findNoteById(int stdId) {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<String> notes = new ArrayList<>();
        try {
            st = conn.prepareStatement(
                    "SELECT * FROM notes WHERE studentId = ?");
            st.setInt(1, stdId);
            
            rs = st.executeQuery();
            //cria lista de resultados
            String note = "";
            while(rs.next()) {                
                note = rs.getString("note");
                notes.add(note);
                notes.add("\n");
            }
            return notes;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBMaria.closeStatement(st);
            DBMaria.closeResultSet(rs);
        }
        return null;
    }

    public boolean updateNote(int id, String note) {
        PreparedStatement st = null;
        String SQL = "UPDATE notes SET note = ? WHERE studentid = ?";
        try {
            st = conn.prepareStatement(SQL);
            st.setString(1, note);            
            st.setInt(2, id);
            st.executeUpdate();
            st.close();
            return true;
        }catch(SQLException ex){
            ex.printStackTrace();
        }finally {
            DBMaria.closeStatement(st);
        }
        return false;
    }

    
}
