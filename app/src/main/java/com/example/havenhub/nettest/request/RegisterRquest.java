package com.example.havenhub.nettest.request;

public class RegisterRquest {
    private String username;
    private String realname;
    private String password;
    private String gender;



    public RegisterRquest(String username, String realname, String password, String gender) {
        this.username = username;
        this.realname = realname;
        this.password = password;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
