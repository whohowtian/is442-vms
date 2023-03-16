package com.g2t3.vms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("Question")
@Getter
@Setter
public class Question {
    @Id
    private String id;
    private String qnTitle;
    private String inputType;

    // possbily need QuestionController?
    private String value;

    public Question(String qnTitle, String inputType) {
        this.qnTitle = qnTitle;
        this.inputType = inputType;
        this.value = "";
    }

    @Override
    public String toString() {
        return String.format("{ \"qnTitle\"='%s', \"inputType\"='%s', \"value\"='%s' }", qnTitle, inputType, value);
        // return String.format("{ id='%s', qnTitle='%s', inputType='%s'}", id, qnTitle, inputType);
    }

}
