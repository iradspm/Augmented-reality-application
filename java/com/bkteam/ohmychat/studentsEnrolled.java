package com.bkteam.ohmychat;

public class studentsEnrolled {
    String email,registration_number,year,gender,course,unit;


    public studentsEnrolled() {
    }

    public studentsEnrolled(String email, String registration_number, String year, String gender, String course, String unit) {
        this.email = email;
        this.registration_number = registration_number;
        this.year = year;
        this.gender = gender;
        this.course = course;
        this.unit = unit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
