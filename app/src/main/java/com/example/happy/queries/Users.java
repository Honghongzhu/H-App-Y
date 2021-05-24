package com.example.happy.queries;

public class Users {

    private String userId;
    private String androidId;

    public String getUserId() {
        return userId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public Users(String userId, String androidId) {
        this.userId = userId;
        this.androidId = androidId;
    }
}
