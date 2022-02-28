
package entities;

import java.util.List;
import java.util.Map;

/**
 * Purpose: entity that will save student personal data
 * 
 */
public class Student extends Person{
    private Parent[] parent;
    private static int count = 0;
    private Grade grades;
    private List<Enrollment> enroll;

    public Student() {
       setId(++count);
    }

    public Student(Parent[] parent, String fName, String lName, String phone, String email) {
        super(fName, lName, phone, email);
        this.parent = parent;
        setId(++count);
    }
    

    public Parent[] getParent() {
        return parent;
    }

    public void setParent(Parent[] parent) {
        this.parent = parent;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Student.count = count;
    }

    
    public List<Enrollment> getEnroll() {
        return enroll;
    }

    public void setEnroll(List<Enrollment> enroll) {
        this.enroll = enroll;
    }

    
    public String printStudent() {
        return "Student{" + getfName() + " " + getlName() + ", grades1=" + grades.getGrade(0) +", grades2=" + grades.getGrade(1) +", grades3=" + grades.getGrade(2) +", grades4=" + grades.getGrade(3) +", grades5=" + grades.getGrade(4) + '}';
    }
    
    public String printParent(){
        return "Parent 1: " + getParent()[0].getfName() + " " + getParent()[0].getlName() + "\nParent 2: " + 
                getParent()[1].getfName() + " " + getParent()[1].getlName();
    }

    @Override
    public String toString() {
        return getfName() + " " + getlName();
    }

    public Grade getGrades() {
        return grades;
    }

    public void setGrades(Grade grades) {
        this.grades = grades;
    }
    
}
