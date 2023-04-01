package com.g2t3.vms.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2t3.vms.model.FormTemplate;

@Repository
public interface FormTemplateRepo extends MongoRepository<FormTemplate, String> {
    
    @Query("{}")
    ArrayList<FormTemplate> findAll();

    @Query("{'formNo': ?0}")
    FormTemplate getFormTemplateByNo(String no);

    @Query("{'_id': ?0}")
    FormTemplate getFormTemplateByDocId(String id);

    @DeleteQuery("{'formNo': ?0}")
    FormTemplate deleteFormTemplateByNo(String no);

}
