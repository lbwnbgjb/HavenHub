package com.example.havenhub.model;

public class RoomMate {
    private String name;
    private String StudentId;
    private int age;
    private String ziZe;


    public RoomMate(String name, String StudentId, String ziZe, int age) {
        this.name = name;
        this.StudentId = StudentId;
        this.ziZe = ziZe;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getZiZe() {
        return ziZe;
    }

    public void setZiZe(String ziZe) {
        this.ziZe = ziZe;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
