package com.g2t3.vms.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Document("Question")
@Getter
@Setter
public class Question {
    // @Id
    // private String id;
    private String qnTitle;
    private String inputType;
    private ArrayList inputOptions;
    private String answer;

    public Question(String qnTitle, String inputType, ArrayList inputOptions) {
        this.qnTitle = qnTitle;
        this.inputType = inputType;
        this.inputOptions = inputOptions;
        this.answer = "";
        // this.id = new ObjectId().toString();
    }

    // @Override
    // public String toString() {
    //     return String.format("{ \"qnTitle\"='%s', \"inputType\"='%s', \"inputOptions\"='%s', \"answer\"='%s' }", qnTitle, inputType, inputOptions, answer);
    //     // return String.format("{ id='%s', qnTitle='%s', inputType='%s'}", id, qnTitle, inputType);
    // }

}
