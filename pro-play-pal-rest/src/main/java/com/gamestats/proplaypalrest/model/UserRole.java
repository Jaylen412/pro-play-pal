package com.gamestats.proplaypalrest.model;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");
    private String value;

    private UserRole(String value) {
        this.value = value;
    }

}
