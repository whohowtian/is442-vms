package com.g2t3.vms.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.g2t3.vms.model.User;
import com.g2t3.vms.model.UserType;
import com.g2t3.vms.model.Vendor;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
    
    @Query("{}")
    ArrayList<User> findAll();

    @Query("{'userType': ?0}")
    ArrayList<User> findByUserType(UserType userType);

    @Query("{'userId': ?0}")
    User findbyId(String userId);

    @Query("{'email': ?0}")
    User findByEmail(String email);

    @Query("{'entityUEN': ?0}")
    Vendor findByEntityUEN(String entityUEN);

    @DeleteQuery("{'userId': ?0}")
    User deleteUserById(String userId);

    @DeleteQuery("{'email': ?0}")
    User deleteUserByEmail(String email);

}