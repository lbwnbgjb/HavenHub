package com.example.havenhub.database;

public class Attendance {
    private int id;
    private String studentId;
    private String date;
    private String status;
    private String remark;
    private String studentName; // 非数据库字段，用于显示
    
    public Attendance(int id, String studentId, String date, String status, String remark) {
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.status = status;
        this.remark = remark;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getStudentName() {
        return studentName;
    }
    
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
    @Override
    public String toString() {
        return "Attendance{id=" + id + ", studentId='" + studentId + "', date='" + date + "', status='" + status + "', remark='" + remark + "'}";
    }
}