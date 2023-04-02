package com.g2t3.vms.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.g2t3.vms.model.Form;

public interface FormRepo extends MongoRepository<Form, String> {
    
    @Query("{}")
    ArrayList<Form> findAll();

    @Query("{'_id': ?0}")
    Form getFormByID(String id);

    @Query("{'status': ?0}")
    ArrayList<Form> getFormByStatus(String status);

    @Query("{'assigned_vendor_email': ?0}")
    ArrayList<Form> getFormByVendor(String vendorEmail);

    // @Query("{'formContent.formSections.questions._id: ?0'}")
    // Form getQuestionObjByID(ObjectId id);
}
