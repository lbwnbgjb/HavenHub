package com.example.havenhub.modle;

public class RoomMate {
    private String name;
    private String StudentId;
    private String ziZe;


    public RoomMate(String name, String studentId, String ziZe) {
        this.name = name;
        StudentId = studentId;
        this.ziZe = ziZe;
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
}
