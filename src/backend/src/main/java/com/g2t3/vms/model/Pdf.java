package com.g2t3.vms.model;

import java.io.InputStream;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document("Pdf")
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Pdf {

    @Id
    private String fileId;

    private String title;

    private InputStream stream;
    
}
