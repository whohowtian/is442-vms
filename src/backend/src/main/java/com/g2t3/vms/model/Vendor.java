package com.g2t3.vms.model;

import java.util.ArrayList;

import org.springframework.data.annotation.PersistenceCreator;

import com.g2t3.vms.enums.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true) 
@Data @EqualsAndHashCode(callSuper=false) 
@NoArgsConstructor
public class Vendor extends User {
    
    private String entityUEN;

    private String entityName;

    private ArrayList<String> entityActivities;

    private boolean isGSTRegistered;

    private String gstRegisteredNo;

    @PersistenceCreator
    public Vendor(String userId, String email, String password, String name, String number,
            String entityUEN, String entityName, ArrayList<String> entityActivities, boolean isGSTRegistered,
            String gstRegisteredNo) {
        super(userId, email, password, name, number, UserType.VENDOR);
        this.entityUEN = entityUEN;
        this.entityName = entityName;
        this.entityActivities = entityActivities;
        this.isGSTRegistered = isGSTRegistered;
        this.gstRegisteredNo = gstRegisteredNo;
    }
    
    

}
