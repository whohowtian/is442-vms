package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.g2t3.vms.enums.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Document(collection = "User")
@SuperBuilder(toBuilder = true) // Generates a protected constructor on the class that takes a builder instance as a parameter
@Data @EqualsAndHashCode(callSuper=false) // Creates Getters, Setters and Constructor with All Arguments
@NoArgsConstructor
public abstract class User {

    @Id
    private String userId;

    @Indexed(unique = true) // MongoDB will reject duplicate values for the indexed field
    private String email;

    private String password;

    private String name;

    private String number;

    private boolean isEnabled = false; // User needs to set a password before account is activated

    @Field("userType") // Configure the name of a field we want to use when MongoDB presists the document
    private UserType userType;

    @PersistenceCreator // Telling MongoDB that this constructor should be used when creating instances of the User class
    public User(String userId, String email, String password, String name, String number, UserType userType) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
        this.userType = userType;
    }

}
