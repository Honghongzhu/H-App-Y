package com.example.happy.queries;

public class Users {

    private int userId;
    private String androidId;

    public int getUserId() {
        return userId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public Users(int userId, String androidId) {
        this.userId = userId;
        this.androidId = androidId;
    }
}
