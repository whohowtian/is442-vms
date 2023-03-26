package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.g2t3.vms.enums.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Document(collection = "User")
@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper=false)
// @NoArgsConstructor
public abstract class User {

    @Id
    private String userId;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String name;

    private String number;

    @Field("userType")
    private UserType userType;

    private boolean isAdmin;

    private boolean isApprover;

    @PersistenceCreator
    public User(String userId, String email, String password, String name, String number, UserType userType,
            boolean isAdmin, boolean isApprover) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.number = number;
        this.userType = userType;
        this.isAdmin = isAdmin;
        this.isApprover = isApprover;
    }
    
}
