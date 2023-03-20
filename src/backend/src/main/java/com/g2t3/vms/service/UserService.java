package com.g2t3.vms.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2t3.vms.exception.UserNotFoundException;
import com.g2t3.vms.model.User;
import com.g2t3.vms.model.UserType;
import com.g2t3.vms.repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    // GET
    public ArrayList<User> getAllUsers() throws UserNotFoundException, Exception {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userRepo.findAllUsers()) {
            users.add(user);
        }

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users have been created.");
        }
        
        return users;
    }

    public ArrayList<User> getUsersByType(UserType type) throws UserNotFoundException, Exception {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userRepo.findByUserType(type)) {
            users.add(user);
        }

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users of " + type + " type have been created.");
        }
        
        return users;
    }

    public User getUserById(String UID) throws UserNotFoundException, Exception {
        
        User user = userRepo.findbyId(UID);
        if (user == null) {
            throw new UserNotFoundException("User " + UID + " does not exist.");
        }
        return user;

    }

    public User getUserByEmail(String email) throws UserNotFoundException, Exception {
        
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with " + email + " does not exist.");
        }
        return user;
        
    }

    // POST OR PUT
    public void updateUser(String id, User newUserDetails) throws UserNotFoundException, Exception {

        User prevUserDetails = getUserById(id);

        prevUserDetails.setName(newUserDetails.getName());
        prevUserDetails.setEmail(newUserDetails.getEmail());
        prevUserDetails.setUserType(newUserDetails.getUserType());
        prevUserDetails.setCountry(newUserDetails.getCountry());
        prevUserDetails.setPassword(newUserDetails.getPassword());

        userRepo.save(prevUserDetails);

    }

    public void deleteUser(String id) throws UserNotFoundException, Exception {

        User user = getUserById(id);
        userRepo.deleteUserById(id);

    }

    // TO BE EDITED
}
