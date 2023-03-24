package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

@Document("FormTemplate")
@Getter
@Setter
public class FormTemplate {
    @Id
    private String id;
    @Indexed(unique = true, background = true)
    private String formNo;
    private String formName;
    private String formEffDate;
    private int revNo;
    private boolean isArchived;
    private ArrayList<FormSection> formSections;

    public FormTemplate(String formNo, String formName, ArrayList<FormSection> formSections) {
        this.formNo = formNo;
        this.formName = formName;
        this.formSections = formSections;
        this.revNo = 1;
        this.isArchived = false;
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        formEffDate = date.format(formatter);
    }

}
