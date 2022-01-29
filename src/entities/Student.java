
package entities;

/**
 * Purpose: entity that will save student personal data
 * 
 */
public class Student extends Person{
    private Parent parent;
    private static int count = 0;

    public Student() {
       setId(++count);
    }

    public Student(Parent parent, String fName, String lName, String phone, String email) {
        super(fName, lName, phone, email);
        setId(++count);
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
    
    
}
