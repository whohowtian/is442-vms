package com.g2t3.vms.model;

import java.io.InputStream;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class InputPdf {

    @Id
    private String fileId;

    private String title;

    private InputStream stream;
    
}
