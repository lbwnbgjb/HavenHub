package com.example.havenhub.database;

public class Dormitory {
    private int id;
    private String dormitoryId;
    private String building;
    private int floor;
    private String roomNumber;
    private int bedCount;
    private int occupiedCount;
    private String status;
    private String description;
    
    public Dormitory(String dormitoryId, String building, int floor, String roomNumber, int bedCount, int occupiedCount, String status, String description) {
        this.dormitoryId = dormitoryId;
        this.building = building;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.bedCount = bedCount;
        this.occupiedCount = occupiedCount;
        this.status = status;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDormitoryId() {
        return dormitoryId;
    }
    
    public void setDormitoryId(String dormitoryId) {
        this.dormitoryId = dormitoryId;
    }
    
    public String getBuilding() {
        return building;
    }
    
    public void setBuilding(String building) {
        this.building = building;
    }
    
    public int getFloor() {
        return floor;
    }
    
    public void setFloor(int floor) {
        this.floor = floor;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public int getBedCount() {
        return bedCount;
    }
    
    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }
    
    public int getOccupiedCount() {
        return occupiedCount;
    }
    
    public void setOccupiedCount(int occupiedCount) {
        this.occupiedCount = occupiedCount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Dormitory{id=" + id + ", dormitoryId='" + dormitoryId + "', building='" + building + "', floor=" + floor + ", roomNumber='" + roomNumber + "', bedCount=" + bedCount + ", occupiedCount=" + occupiedCount + ", status='" + status + "', description='" + description + "'}";
    }
}