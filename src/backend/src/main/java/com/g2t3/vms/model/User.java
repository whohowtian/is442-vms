package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Document("User")
@Getter
@Setter
public class User {

    @Id
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private UserType userType;

    @NotBlank
    private String vendorName; // not sure if we need to create a model for Company
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String country;
    
    private String password;

    public User(String name, UserType userType, String email, String country, String password) {
        this.name = name;
        this.userType = userType;
        this.email = email;
        this.country = country;
        this.password = password;
    }
}
