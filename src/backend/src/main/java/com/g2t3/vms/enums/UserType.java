package com.g2t3.vms.enums;

import lombok.Getter;

// REF: https://blog.tericcabrel.com/using-mongodb-with-spring-boot-project-part-1/

@Getter
public enum UserType {

    VENDOR("VENDOR"),
    ADMIN("ADMIN"),
    APPROVER("APPROVER");

    private final String value;

    UserType(String value) {
        this.value = value;
    }
}
