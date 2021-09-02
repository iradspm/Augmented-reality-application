package com.bkteam.ohmychat;

public class EnrollStudents {
    String email,registration_number,year,gender,unit;

    public EnrollStudents(String email, String registration_number, String year, String gender, String unit) {
        //this.course=course;
        this.email = email;
        this.registration_number = registration_number;
        this.year = year;
        this.gender = gender;
        this.unit = unit;
    }
    public String getEmail() {
        return email;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public String getYear() {
        return year;
    }

    public String getGender() {
        return gender;
    }


    public String getUnit() {
        return unit;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setUnit(String unit) {
        this.unit = unit;
    }
}
