package com.g2t3.vms.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper=false)
public class InputFormTemplate {

    private Map<String, Object> details;
    
}
