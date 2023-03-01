package com.g2t3.vms;

import com.g2t3.vms.model.*;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
@RestController
@EnableMongoRepositories
public class ApplicationTest {
    @Autowired
    private QuestionRepo qn_repo;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }

    @GetMapping("/test_create")
    public String test_create() {
        qn_repo.save(new Question("what is your name", "textbox"));
        qn_repo.save(new Question("what is your age?", "number"));
        return "create success";
    }

    @GetMapping("/get_all_qn")
    public ArrayList<String> get_all_qn() {
        ArrayList<String> result = new ArrayList<String>();

        for (Question question : qn_repo.findAll()) {
            result.add(question.toString());
        }

        return result;
    } 
    
}






