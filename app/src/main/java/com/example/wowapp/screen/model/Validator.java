package com.example.wowapp.screen.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
public class Validator {
    private static  Validator instance = null;
    private Pattern pattern;
    private Matcher matcher;
    private final String  regexPhone = "^(?:\\+91|0)?[6-9]\\d{9}$";
    private final String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,}$";
    private  final  String regexUsername = "^[a-zA-Z_][a-zA-Z_0-9]{2,}$";
    private final String regexPassword = "^[a-zA-Z0-9]{2,}$";
    private final String regexDate = "^(0?[1-9]|1\\d|2\\d|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
    private Validator() {

    }
    public static Validator getInstance() {
        if(instance == null) {
            instance = new Validator();
        }
        return instance;
    }
    public boolean isValidPhone(String phone) {
        if(phone.isEmpty()) {
            return false;
        }
        pattern = Pattern.compile(regexPhone);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public boolean isValidEmail(String email) {
        if(email.isEmpty()) {
            return false;
        }
        pattern = Pattern.compile(regexEmail);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isValidUsername(String username) {
        if(username.isEmpty()) {
            return false;
        }
        pattern = Pattern.compile(regexUsername);
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

    public boolean isValidDate(String dateStr) {

        return true;
    }

    public boolean isValidBirthdate(String birthdate) {
        if(birthdate.isEmpty()) {
            return true;
        }
        return isValidDate(birthdate);
    }

    public boolean isValidPassword(String password) {
        pattern = Pattern.compile(regexPassword);//. represents single character
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
