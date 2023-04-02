package com.g2t3.vms.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Document("FormTemplate")
@Data @EqualsAndHashCode(callSuper=false)
public class FormTemplate {
    @Id
    private String id;

    private String formNo;

    private String formName;

    private int revNo;

    private HashMap<String, FormSection> formSections;

    private String lastEdited;

    private long deadlineDays;

    private String editedBy;

    public FormTemplate(String formNo, String formName, String lastEdited, HashMap<String, FormSection> formSections, long deadlineDays, String editedBy) {
        
        this.formNo = formNo;
        this.formName = formName;
        this.formSections = formSections;
        this.deadlineDays = deadlineDays;
        this.revNo = 1;
        this.lastEdited = lastEdited;
        this.editedBy = editedBy;
    }

}
