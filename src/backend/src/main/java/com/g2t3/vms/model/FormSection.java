package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



@Document("FormSection")
@Getter
@Setter
public class FormSection {
    // @Id
    // private String id;
    // section name may be optional?
    private String sectionName;
    private boolean adminUseOnly;
    private boolean doScoreCalculation;
    private HashMap<String, Question> questions;

    public FormSection (String sectionName, boolean doScoreCalculation, HashMap<String, Question> questions) {
        this.sectionName = sectionName;
        this.questions = questions;
        this.doScoreCalculation = doScoreCalculation;
        this.questions = questions;
        // this.id = new ObjectId().toString();
    }

    // @Override
    // public String toString() {
    //     return String.format("{ \"sectionName\": '%s', \"questions\": %s", sectionName, questions + "}");
    // }

    public boolean getAdminUseOnly() {
        return adminUseOnly;
    }

}
