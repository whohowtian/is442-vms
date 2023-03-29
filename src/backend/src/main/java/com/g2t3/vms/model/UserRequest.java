package com.g2t3.vms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserRequest {
    
    private String email;

    private String password;

}
