package com.g2t3.vms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public ArrayList<User> getAllUsers() throws UserNotFoundException, Exception {
        ArrayList<User> users = new ArrayList<>();

        for (User user : userRepo.findAll()) {
            users.add(form);
        }

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users have been created.");
        }

        return users;
    }

    // TO BE EDITED
}
