package com.g2t3.vms.model;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputFormTemplate {
    private Map<String, Object> details;
}
