package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Document("FormSection")
@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("{ \"sectionName\": '%s', \"questions\": %s", sectionName, questions + "}");
    }

}
