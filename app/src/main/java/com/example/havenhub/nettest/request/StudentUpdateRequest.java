package com.example.havenhub.nettest.request;

public class StudentUpdateRequest {
    private String studentId;
    private String oldPassword;
    private String newPassword;
    private String newName;

    public StudentUpdateRequest(String studentId, String oldPassword, String newPassword, String newName) {
        this.studentId = studentId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newName = newName;
    }


}
