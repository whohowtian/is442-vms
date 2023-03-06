package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;


@Document("FormSection")
public class FormSection {
    @Id
    private String id;

    // section name may be optional?
    private String sectionName;

    private boolean adminUseOnly;

    private boolean doScoreCalculation;

    private ArrayList<Question> questions; 

    public FormSection (String sectionName, boolean doScoreCalculation, ArrayList<Question> questions) {
        this.sectionName = sectionName;
        this.questions = questions;
        this.doScoreCalculation = doScoreCalculation;
    }

    public boolean isDoScoreCalculation() {
        return doScoreCalculation;
    }

    public void setDoScoreCalculation(boolean doScoreCalculation) {
        this.doScoreCalculation = doScoreCalculation;
    }

    @Override
    public String toString() {
        return String.format("sectionName: '%s', questions: %s", sectionName, questions);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public boolean isAdminUseOnly() {
        return adminUseOnly;
    }

    public void setAdminUseOnly(boolean adminUseOnly) {
        this.adminUseOnly = adminUseOnly;
    }

}
