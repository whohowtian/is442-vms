package com.g2t3.vms.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2t3.vms.model.EmailTemplate;

@Repository
public interface EmailTemplateRepo extends MongoRepository<EmailTemplate, String>{

    @Query("{}")
    ArrayList<EmailTemplate> findAll();

    @Query("{'id': ?0}")
    EmailTemplate findbyId(String id);

    @Query("{'name': ?0}")
    EmailTemplate findByName(String name);
    
}
