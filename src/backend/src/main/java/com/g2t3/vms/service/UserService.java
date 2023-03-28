package com.g2t3.vms.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2t3.vms.enums.UserType;
import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Admin;
import com.g2t3.vms.model.Approver;
import com.g2t3.vms.model.User;
import com.g2t3.vms.model.Vendor;
import com.g2t3.vms.repository.UserRepo;

@Service
public class UserService {
    
    @Autowired
    private UserRepo userRepo;

    public Vendor createVendor(Vendor vendor) throws ResourceAlreadyExistException, Exception {
        
        // errors
        Vendor user = (Vendor) userRepo.findByEmail(vendor.getEmail());
        if (user != null) {
            throw new ResourceAlreadyExistException(String.format("User with this email (%s) already exist.", vendor.getEmail()));
        }
        Vendor user2 = (Vendor) userRepo.findByEntityUEN(vendor.getEntityUEN());
        if (user2 != null) {
            throw new ResourceAlreadyExistException(String.format("A user with this entity UEN (%s) already exist.", vendor.getEntityUEN()));
        }

        // save new vendor
        Vendor newUser = userRepo.save(vendor);
        return newUser;
        
    }

    public Admin createAdmin(Admin admin) throws ResourceAlreadyExistException, Exception {
        
        // errors
        Admin user = (Admin) userRepo.findByEmail(admin.getEmail());
        if (user != null) {
            throw new ResourceAlreadyExistException(String.format("User with this email (%s) already exist.", admin.getEmail()));
        }

        // save new admin
        Admin newUser = userRepo.save(admin);
        return newUser;
        
    }

    public Approver createApprover(Approver approver) throws ResourceAlreadyExistException, Exception {
        
        // errors
        Approver user = (Approver) userRepo.findByEmail(approver.getEmail());
        if (user != null) {
            throw new ResourceAlreadyExistException(String.format("User with this email (%s) already exist.", approver.getEmail()));
        }

        // save new approver
        Approver newUser = userRepo.save(approver);
        return newUser;
        
    }

    public ArrayList<User> getAllUsers() throws ResourceNotFoundException, Exception {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            users.add(user);
        }

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users have been created.");
        }
        
        return users;
    }

    public User getUserById(String UID) throws ResourceNotFoundException, Exception {
        
        User user = userRepo.findbyId(UID);
        if (user == null) {
            throw new ResourceNotFoundException("User " + UID + " does not exist.");
        }
        return user;

    }

    public User getUserByEmail(String email) throws ResourceNotFoundException, Exception {
        
        User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User with " + email + " does not exist.");
        }
        return user;
        
    }

    public ArrayList<User> getUsersByType(UserType type) throws ResourceNotFoundException, Exception {
        ArrayList<User> users = new ArrayList<>();
        for (User user : userRepo.findByUserType(type)) {
            users.add(user);
        }

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users of " + type + " type have been created.");
        }
        
        return users;
    }

    public <T> UserType getType(T type) {

        UserType result = null;
        switch((String) type) {
            case "VENDOR":
                result = UserType.VENDOR;
                break;
            case "ADMIN":
                result = UserType.ADMIN;
                break;
            case "APPROVER":
                result = UserType.APPROVER;
                break;
        }
        return result;

    }

    public <T> User updateAdmin(UserType prevType, HashMap<String, T> user) throws ResourceNotFoundException, Exception {

        // check
        getUserById((String) user.get("userId"));
        
        UserType type = getType((String) user.get("userType"));
        if (prevType != type) {
            deleteUser((String) user.get("userId"));
        }
        
        Admin newDetails = new Admin();
        newDetails = newDetails.toBuilder()
            .userId( (String) user.get("userId"))
            .name( (String) user.get("name"))
            .email( (String) user.get("email"))
            .password( (String) user.get("password"))
            .number( (String) user.get("number"))
            .userType(type)
            .isApprover(false)
            .isAdmin(true)
            .build();
        System.out.println("check2");

        userRepo.save(newDetails);
        return newDetails;

    }

    public <T> User updateApprover(UserType prevType, HashMap<String, T> user) throws ResourceNotFoundException, Exception {

        // check
        getUserById((String) user.get("userId"));

        UserType type = getType((String) user.get("userType"));
        if (prevType != type) {
            deleteUser((String) user.get("userId"));
        }

        Approver newDetails = new Approver();
        newDetails = newDetails.toBuilder()
            .userId( (String) user.get("userId"))
            .name( (String) user.get("name"))
            .email( (String) user.get("email"))
            .password( (String) user.get("password"))
            .number( (String) user.get("number"))
            .userType(type)
            .isApprover(true)
            .isAdmin(false)
            .build();

        userRepo.save(newDetails);
        return newDetails;

    }

    public <T> User updateVendor(UserType prevType, HashMap<String, T> user) throws ResourceNotFoundException, Exception {

        // check
        getUserById((String) user.get("userId"));
        
        UserType type = getType((String) user.get("userType"));
        if (prevType != type) {
            deleteUser((String) user.get("userId"));
        }

        Vendor newDetails = new Vendor();
        newDetails = newDetails.toBuilder()
            .userId( (String) user.get("userId"))
            .name( (String) user.get("name"))
            .email( (String) user.get("email"))
            .password( (String) user.get("password"))
            .number( (String) user.get("number"))
            .userType(type)
            .entityName((String) user.get("entityName"))
            .entityActivities((ArrayList<String>) user.get("entityActivities"))
            .isGSTRegistered((boolean) user.get("isGSTRegistered"))
            .build();

        userRepo.save(newDetails);
        return newDetails;

    }

    public User setPassword(User user) throws ResourceNotFoundException, Exception {

        User prevDetails = getUserByEmail(user.getEmail());
        prevDetails.setPassword(user.getPassword());

        userRepo.save(prevDetails);
        return prevDetails;

    }

    public void deleteUser(String id) throws ResourceNotFoundException, Exception {

        getUserById(id);
        userRepo.deleteUserById(id);

    }

    public void deleteUserByEmail(String email) throws ResourceNotFoundException, Exception {

        getUserByEmail(email);
        userRepo.deleteUserByEmail(email);

    }

    

}
