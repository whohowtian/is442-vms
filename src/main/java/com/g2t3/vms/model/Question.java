package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Question")
public class Question {
    @Id
    private String id;

    private String qnTitle;
    private String inputType;

    public Question(String qnTitle, String inputType) {
        this.qnTitle = qnTitle;
        this.inputType = inputType;
    }

    @Override
    public String toString() {
        return String.format("{id='%s', qnTitle='%s', inputType='%s'}", id, qnTitle, inputType);
    }

    public void setQnTitle(String qnTitle) {
        this.qnTitle = qnTitle;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getQnTitle() {
        return qnTitle;
    }

    public String getInputType() {
        return inputType;
    }


}
