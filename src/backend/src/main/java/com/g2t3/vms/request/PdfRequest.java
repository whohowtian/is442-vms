package com.g2t3.vms.request;

import java.io.InputStream;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PdfRequest {

    @Id
    private String fileId;

    private String formName;

    private String vendorUEN;

    private InputStream stream;
    
}
