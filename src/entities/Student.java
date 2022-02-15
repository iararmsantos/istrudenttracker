
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
    private Map<Section, Grade> grades;
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
    
    
}
