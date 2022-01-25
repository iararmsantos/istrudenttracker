/*
 * This class will be the parent class for Teacher, Students and Parents classes
 * By: Iara Santos
 * Date: 01/25/2022
 */
package entities;

public class Person {
    private int Id;
    private String fName;
    private String lName;
    private String email;

    public Person() {
    }

    public Person(int Id, String fName, String lName, String email) {
        this.Id = Id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
