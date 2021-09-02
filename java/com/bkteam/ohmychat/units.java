package com.bkteam.ohmychat;

public class units {
    String key,course,unitId, unit, startDate, endDate;

    public units(String key, String course, String unitId, String unit, String startDate, String endDate) {
        this.key=key;
        this.course=course;
        this.unitId = unitId;
        this.unit = unit;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
