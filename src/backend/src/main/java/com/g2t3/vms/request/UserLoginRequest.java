package com.g2t3.vms.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class UserLoginRequest {
    
    private String email;

    private String password;

}
