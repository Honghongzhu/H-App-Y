package com.example.happy.queries;

public class Users {

    private String userId;
    private String androidId;
    private String alias;

    public String getUserId() {
        return userId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public String getAlias() {
        return alias;
    }

    public Users(String userId, String androidId, String alias) {
        this.userId = userId;
        this.androidId = androidId;
        this.alias = alias;
    }
}
