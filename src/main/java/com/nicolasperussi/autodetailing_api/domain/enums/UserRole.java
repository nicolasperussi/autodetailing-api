package com.nicolasperussi.autodetailing_api.domain.enums;

public enum UserRole {
    MANAGER("manager"),
    DETAILER("detailer");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
