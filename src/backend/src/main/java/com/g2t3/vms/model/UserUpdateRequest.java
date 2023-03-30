package com.g2t3.vms.model;

import java.util.ArrayList;

import com.g2t3.vms.enums.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserUpdateRequest {
    
    private String userId;

    private String email;

    private String password;

    private String name;

    private String number;

    private UserType userType;

    private String entityUEN;

    private String entityName;

    private ArrayList<String> entityActivities;

    private boolean isGSTRegistered;

    private String gstRegisteredNo;


}
