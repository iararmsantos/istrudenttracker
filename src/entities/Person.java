
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
    private String phone;
    private String email;

    public Person() {
        

    }

    public Person(String fName, String lName, String phone, String email) {
        
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
