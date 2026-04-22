package com.example.havenhub.nettest.request;

public class StudentUpdateResponse {
    private boolean success;//后端部分为isSuccess，但是会自动被映射为success，所以这里用success
    private String message;

    public StudentUpdateResponse() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
