package com.g2t3.vms.model;

import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.validation.annotation.Validated;

import com.g2t3.vms.enums.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Validated
public class Admin extends User {
    
    @PersistenceCreator
    public Admin(String userId, String email, String password, String name, String number) {
        super(userId, email, password, name, number, UserType.ADMIN);
    }

}
