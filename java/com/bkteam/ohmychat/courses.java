package com.bkteam.ohmychat;

public class courses {
    String courseId,courseName;

    public courses(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}
