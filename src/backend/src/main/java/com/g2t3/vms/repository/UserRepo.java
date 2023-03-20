package com.g2t3.vms.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.g2t3.vms.model.User;
import com.g2t3.vms.model.UserType;

public interface UserRepo extends MongoRepository<User, String>{
    
    @Query("{}")
    ArrayList<User> findAllUsers();

    // depends on how you want to query the user, delete if needed
    @Query("{'userId': ?0}")
    User findbyId(String userId);

    @Query("{'email': ?0}")
    User findByEmail(String email);

    @Query("{'userType': ?0}")
    ArrayList<User> findByUserType(UserType userType);

    @DeleteQuery("{'userId': ?0}")
    User deleteUserById(String userId);

}