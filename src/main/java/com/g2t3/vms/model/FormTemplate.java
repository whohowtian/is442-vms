package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;

@Document("FormTemplate")
public class FormTemplate {
    @Id
    private String id;

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

    @Override
    public String toString() {
        // return String.format("formTemplate: { formNo: '%s', formName: '%s', revNo: '%s', formEffDate: '%s'}", formNo, formName, revNo, formEffDate);
        return String.format("{ id: '%s' formNo: '%s', formName: '%s', revNo: '%s', formEffDate: '%s', isArchived: %s, formSections: %s}", id, formNo, formName, revNo, formEffDate, isArchived, formSections.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public boolean getIsArchived() {
        return isArchived;
    }

    public ArrayList<FormSection> getFormSections() {
        return formSections;
    }

    public String getFormEffDate() {
        return formEffDate;
    }

    public void setFormEffDate(String formEffDate) {
        this.formEffDate = formEffDate;
    }

    public int getRevNo() {
        return revNo;
    }

    public void setRevNo(int revNo) {
        this.revNo = revNo;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public void setFormSections(ArrayList<FormSection> formSections) {
        this.formSections = formSections;
    }



}
