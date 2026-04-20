package com.example.havenhub.nettest.request;

public class Student {
    private String name;
    private String studentId;
    private String gender;

    public Student() {
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getGender() {return gender;}

    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }




}
