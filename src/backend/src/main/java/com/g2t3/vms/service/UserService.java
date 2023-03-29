package com.g2t3.vms.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g2t3.vms.enums.UserType;
import com.g2t3.vms.exception.ResourceAlreadyExistException;
import com.g2t3.vms.exception.ResourceNotFoundException;
import com.g2t3.vms.model.Admin;
import com.g2t3.vms.model.Approver;
import com.g2t3.vms.model.User;
import com.g2t3.vms.model.UserUpdateRequest;
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

    public void updateAdmin(UserType prevType, UserUpdateRequest user) throws ResourceNotFoundException, Exception {

        // check
        getUserById(user.getUserId());
        
        UserType type = user.getUserType();
        if (prevType != type) {
            deleteUser(user.getUserId());
        }
        
        Admin newDetails = new Admin();
        newDetails = newDetails.toBuilder()
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .number(user.getNumber())
            .userType(type)
            .isApprover(false)
            .isAdmin(true)
            .build();

        userRepo.save(newDetails);

    }

    public void updateApprover(UserType prevType, UserUpdateRequest user) throws ResourceNotFoundException, Exception {

        // check
        getUserById(user.getUserId());
        
        UserType type = user.getUserType();
        if (prevType != type) {
            deleteUser(user.getUserId());
        }

        Approver newDetails = new Approver();
        newDetails = newDetails.toBuilder()
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .number(user.getNumber())
            .userType(type)
            .isApprover(false)
            .isAdmin(true)
            .build();
        userRepo.save(newDetails);

    }

    public void updateVendor(UserType prevType, UserUpdateRequest user) throws ResourceNotFoundException, Exception {

         // check
         getUserById(user.getUserId());
        
         UserType type = user.getUserType();
        if (prevType != type) {
            deleteUser(user.getUserId());
        }

        Vendor newDetails = new Vendor();
        newDetails = newDetails.toBuilder()
            .userId(user.getUserId())
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .number(user.getNumber())
            .userType(type)
            .entityUEN(user.getEntityUEN())
            .entityName(user.getEntityName())
            .entityActivities(user.getEntityActivities())
            .isGSTRegistered(user.isGSTRegistered())
            .isApprover(false)
            .isAdmin(false)
            .build();

        userRepo.save(newDetails);

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
