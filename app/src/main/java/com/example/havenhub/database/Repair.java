package com.example.havenhub.database;

public class Repair {
    private int id;
    private String studentId;
    private String dormitoryId;
    private String date;
    private String type;
    private String description;
    private String status;
    private String handler;
    private String handleDate;
    private String remark;
    private String studentName; // 非数据库字段，用于显示
    private String dormitoryInfo; // 非数据库字段，用于显示
    
    public Repair(int id, String studentId, String dormitoryId, String date, String type, String description, String status, String handler, String handleDate, String remark) {
        this.id = id;
        this.studentId = studentId;
        this.dormitoryId = dormitoryId;
        this.date = date;
        this.type = type;
        this.description = description;
        this.status = status;
        this.handler = handler;
        this.handleDate = handleDate;
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
    
    public String getDormitoryId() {
        return dormitoryId;
    }
    
    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getHandler() {
        return handler;
    }
    
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    public String getHandleDate() {
        return handleDate;
    }
    
    public void setHandleDate(String handleDate) {
        this.handleDate = handleDate;
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
    
    public String getDormitoryInfo() {
        return dormitoryInfo;
    }
    
    public void setDormitoryInfo(String dormitoryInfo) {
        this.dormitoryInfo = dormitoryInfo;
    }
    
    @Override
    public String toString() {
        return "Repair{id=" + id + ", studentId='" + studentId + "', dormitoryId='" + dormitoryId + "', date='" + date + "', type='" + type + "', description='" + description + "', status='" + status + "', handler='" + handler + "', handleDate='" + handleDate + "', remark='" + remark + "'}";
    }
}