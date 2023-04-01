package com.g2t3.vms.request;

import java.util.ArrayList;

import com.g2t3.vms.enums.UserType;
import com.g2t3.vms.model.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserUpdateRequest extends User {

    private String entityUEN;

    private String entityName;

    private ArrayList<String> entityActivities;

    private boolean isGSTRegistered;

    private String gstRegisteredNo;

    public UserUpdateRequest(String userId, @NotEmpty String email, String password, @NotEmpty String name,
            @NotEmpty String number, UserType userType, String entityUEN, String entityName,
            ArrayList<String> entityActivities, boolean isGSTRegistered, String gstRegisteredNo) {
        super(userId, email, password, name, number, userType);
        this.entityUEN = entityUEN;
        this.entityName = entityName;
        this.entityActivities = entityActivities;
        this.isGSTRegistered = isGSTRegistered;
        this.gstRegisteredNo = gstRegisteredNo;
    }

    

}
