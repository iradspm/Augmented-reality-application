package com.bkteam.ohmychat;

public class RegisteredStudents {
     String email, registration_number,year,gender,unit;

    public RegisteredStudents() {
    }

    public RegisteredStudents(String email, String registration_number, String year, String gender, String unit) {
        this.email = email;
        this.registration_number = registration_number;
        this.year = year;
        this.gender = gender;
       // this.course = course;
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Bookings{" +
                "email='" + email + '\'' +
                ", registration_number='" + registration_number + '\'' +
                ", year='" + year + '\'' +
                ", gender='" + gender + '\'' +
                ", unit='" + unit + '\'' +
                '}';
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
