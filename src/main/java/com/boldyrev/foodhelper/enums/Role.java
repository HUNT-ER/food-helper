package com.boldyrev.foodhelper.enums;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("GUEST"), ADMIN("ADMIN"), USER("USER");

    private String name;

    Role(String name) {
        this.name = name;
    }
}
