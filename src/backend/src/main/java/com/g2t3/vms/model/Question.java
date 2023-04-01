package com.g2t3.vms.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Document("Question")
@Data @EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class Question {

    private String qnTitle;

    private String inputType;

    private ArrayList<HashMap<String, String>> inputOptions;

    private String answer;

    private boolean isRequired;

    public Question(String qnTitle, String inputType, ArrayList<HashMap<String, String>> inputOptions, boolean isRequired) {
        this(qnTitle, inputType, inputOptions);
        this.isRequired = isRequired;
    }

    public Question(String qnTitle, String inputType, ArrayList<HashMap<String, String>> inputOptions) {
        this.qnTitle = qnTitle;
        this.inputType = inputType;
        this.inputOptions = inputOptions;
        this.answer = "";
    }

}
