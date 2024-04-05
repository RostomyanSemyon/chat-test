package com.example.chattest.model.user;

public enum UserPermission {
    WRITE("user:write"),
    JOIN("user:join");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
