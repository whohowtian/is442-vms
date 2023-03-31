package com.g2t3.vms.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    // @Indexed(unique = true, background = true)
    private String formNo;
    private String formName;
    // private String formEffDate;
    private int revNo;
    // private boolean isArchived;
    private HashMap<String, FormSection> formSections;
    private String lastEdited;
    private long deadlineDays;

    public FormTemplate(String formNo, String formName, String lastEdited, HashMap<String, FormSection> formSections, long deadlineDays) {
        this.formNo = formNo;
        this.formName = formName;
        this.formSections = formSections;
        this.deadlineDays = deadlineDays;

        // for (formSection currSection : formSections) {
        //     this.formSections.put(new ObjectId().toString(), currSection);
        // }

        this.revNo = 1;
        // this.isArchived = false;
        // LocalDate dateTimeNow = LocalDate.now();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        this.lastEdited = lastEdited;
        // this.formEffDate = dateTimeNow.format(formatter);

    }

}
