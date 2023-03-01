package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Question")
public class Question {
    @Id
    private String id;

    private String title;
    private String inputType;

    public Question(String title, String inputType) {
        this.title = title;
        this.inputType = inputType;
    }

    @Override
    public String toString() {
        return String.format("id='%s', title='%s', inputType='%s", id, title, inputType);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getTitle() {
        return title;
    }

    public String getInputType() {
        return inputType;
    }


}
