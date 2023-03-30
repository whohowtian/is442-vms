package com.g2t3.vms.model;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Document("Question")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    // @Id
    // private String id;
    private String qnTitle;
    private String inputType;
    private ArrayList<HashMap<String, String>> inputOptions;
    private String answer;
    private boolean isRequired;

    public Question(String qnTitle, String inputType, ArrayList inputOptions, boolean isRequired) {
        this(qnTitle, inputType, inputOiptions);
        this.isRequired = isRequired;
    }

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
