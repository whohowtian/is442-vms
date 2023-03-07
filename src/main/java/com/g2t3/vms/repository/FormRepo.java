package com.g2t3.vms.repository;

import com.g2t3.vms.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.*;

public interface FormRepo extends MongoRepository<FormTemplate, String> {
    
    @Query("{}")
    ArrayList<FormTemplate> findAll();

    @Query("{'formNo': ?0}")
    FormTemplate getFormTemplateByNo(String no);

    @Query("{'_id': ?0}")
    FormTemplate getFormTemplateByDocId(String id);

}
