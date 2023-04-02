package com.g2t3.vms.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nonapi.io.github.classgraph.json.Id;

@Document("EmailTemplate")
@Data @EqualsAndHashCode(callSuper=false)
public class EmailTemplate {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String subject;

    private String data;

}
