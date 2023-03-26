package com.g2t3.vms.model;

import java.util.ArrayList;

import org.springframework.data.annotation.PersistenceCreator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper=false)
public class Vendor extends User {
    
    private String entityUEN;

    private String entityName;

    private ArrayList<String> entityActivities;

    private boolean isGSTRegistered;

    @PersistenceCreator
    public Vendor(String userId, String email, String password, String name, String number,String entityUEN, String entityName,
            ArrayList<String> entityActivities, boolean isGSTRegistered) {
        super(userId, email, password, name, number, UserType.VENDOR, false, false);
        this.entityUEN = entityUEN;
        this.entityName = entityName;
        this.entityActivities = entityActivities;
        this.isGSTRegistered = isGSTRegistered;
    }
    
}
