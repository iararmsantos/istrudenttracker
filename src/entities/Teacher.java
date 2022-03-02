
package entities;

/**
 *Purpose: it will create a teacher entity with personal and professional data
 * 
 */
public class Teacher extends Person{
    private static int count = 0;

    public Teacher() {
        setId(++count);
    }

    public Teacher(String fName, String lName, String phone, String email) {
        super(fName, lName, phone, email);
        setId(++count);
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getfName(), this.getlName());
    }
    
    
}
