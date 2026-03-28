package com.example.havenhub.database;

import android.telephony.mbms.StreamingServiceInfo;

public class User {
    private  int id;
    private  String username;
    private  String password;
    private String name;
    public User(String username,String password){
        super();
        this.username = username;
        this.password = password;
    }
    public  int getId() {
        return  id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User{id ="+ id + ", username = "+ username +",password ="+password +"name"+name+"}";
    }
}
