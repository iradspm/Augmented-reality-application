package com.bkteam.ohmychat;

public class Student {
    private int id;
    private String registration_number;
    private String year;
    private String course;
    private String gender;
    private String unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
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


    public Student(int id, String registration_number, String year,String course, String gender, String unit) {
        this.id=id;
        this.registration_number=registration_number;
        this.year=year;
        this.gender=gender;
        this.unit=unit;

    }

}
