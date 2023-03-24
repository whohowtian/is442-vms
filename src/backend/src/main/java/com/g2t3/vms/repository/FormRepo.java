package com.g2t3.vms.repository;

import com.g2t3.vms.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.*;
import org.bson.types.ObjectId;

public interface FormRepo extends MongoRepository<Form, String> {
    
    @Query("{}")
    ArrayList<Form> findAll();

    @Query("{'_id': ?0}")
    Form getFormByID(ObjectId id);
}
