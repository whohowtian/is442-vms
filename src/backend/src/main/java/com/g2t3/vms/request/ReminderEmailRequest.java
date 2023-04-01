package com.g2t3.vms.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ReminderEmailRequest {

    @NotEmpty
    private String name;
    
    @NotEmpty
    private String email;

    @NotEmpty
    private String formName;

    @NotEmpty
    private String deadline;
    
}
