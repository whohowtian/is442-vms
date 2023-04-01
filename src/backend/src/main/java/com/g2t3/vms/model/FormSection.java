package com.g2t3.vms.model;

import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Document("FormSection")
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class FormSection {

    private String sectionName;

    private boolean adminUseOnly;

    private boolean approvalViewOnly;

    private boolean doScoreCalculation;
    
    private HashMap<String, Question> questions;

    public FormSection (String sectionName, boolean doScoreCalculation, HashMap<String, Question> questions) {
        this.sectionName = sectionName;
        this.questions = questions;
        this.doScoreCalculation = doScoreCalculation;
        this.questions = questions;
    }

    public FormSection (String sectionName, boolean doScoreCalculation, HashMap<String, Question> questions, boolean adminUseOnly, boolean approvalViewOnly) {
        this(sectionName, doScoreCalculation, questions);
        this.adminUseOnly = adminUseOnly;
        this.approvalViewOnly = approvalViewOnly;
    }

}
