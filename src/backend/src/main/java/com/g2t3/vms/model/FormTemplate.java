package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Document("FormTemplate")
@Getter
@Setter
public class FormTemplate {
    @Id
    private String id;
    // @Indexed(unique = true, background = true)
    private String formNo;
    private String formName;
    private String formEffDate;
    private int revNo;
    // private boolean isArchived;
    private HashMap<String, FormSection> formSections;
    private String lastEdited;

    public FormTemplate(String formNo, String formName, String lastEdited, HashMap<String, FormSection> formSections) {
        this.formNo = formNo;
        this.formName = formName;
        this.formSections = formSections;

        // for (formSection currSection : formSections) {
        //     this.formSections.put(new ObjectId().toString(), currSection);
        // }

        this.revNo = 1;
        // this.isArchived = false;
        LocalDate dateTimeNow = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        this.lastEdited = lastEdited;
        this.formEffDate = dateTimeNow.format(formatter);

    }

}
