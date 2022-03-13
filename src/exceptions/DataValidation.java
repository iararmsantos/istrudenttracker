/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import gui.Home;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author iarar
 */
public class DataValidation extends JFrame{
    Home home = new Home();
    
    public boolean isLoginValid(String name, String pass) {
        return isPresent(name, "Email") && isPresent(pass, "Password");
    }
    //verify if data is valid
    public boolean isUserValid(String fName, String lName, String phone, String email, String pass){
        //improvement: isPhone
        return isEmail(email) && isPresent(fName, "First Name") && isPresent(lName, "Last Name")
                && isPresent(phone, "Phone") && isNumber(phone) && isPresent(email, "Email")
                && isPresent(pass, "Password") 
                ;
    }

    //verify if required fields are filled
    private boolean isPresent(String txtField, String title) {
        if ("".equals(txtField)) {
            JOptionPane.showMessageDialog(this, title + " is a required field");
            return false;
        } else {
            return true;
        }
    }

    //verify if required fields are filled
    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Email invalid!");
            return false;
        }
    }
    private boolean isNumber(String number){
        for(int i = 0; i < number.length(); i++){
            if(!Character.isDigit(number.charAt(i))){
                JOptionPane.showMessageDialog(this, "Phone invalid!");
                return false;
            }
        }
        return true;
    }
}
