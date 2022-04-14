/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.ProgramTrainingDB;
import dataaccess.UserDB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import models.*;

/**
 *service class to perform CRUD operations on db 
 * @author DWEI
 */
public class AccountServices {
    // If we want to let users update their password through a secure email, we have to add a clolumn to the database

    
    /**
     * method to update users program training list
     * @param user
     * @param programTrainingList
     * @throws Exception 
     */
    public void updateProgramTraining(User user, List<ProgramTraining> programTrainingList) throws Exception {
        user.setProgramTrainingList(programTrainingList);
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    /**
     * method to retrieve User object by email field and password field 
     * @param email
     * @param password
     * @return user User object 
     */
    public User login(String email, String password) {
        UserDB userDB = new UserDB();

        try {
            User user = userDB.get(email);
            if (getHash(password, user.getPasswordSalt()).equals(user.getPasswordHash())) {
                return user;
            }
        } catch (Exception e) {
        }

        return null;
    }
/**
 * method to get user by matching ID
 * @param ID
 * @return User object matching id 
 * @throws Exception 
 */
    
    public User getByID(int ID) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getByID(ID);
        return user;
    }

    // get a user by matching full name
    public User getUserByFullName(String firstName, String lastName) throws Exception {
        UserDB userDB = new UserDB();
        User userUpdate = userDB.getUserByFullName(firstName, lastName);
        return userUpdate;
    }

    /**
     * get users by matching full name
     * @param firstName
     * @param lastName
     * @return User object matching the above fields 
     * @throws Exception 
     */
    
    public List<User> getUsersByFullName(String firstName, String lastName) throws Exception {
        UserDB userDB = new UserDB();
        List<User> userUpdate = userDB.getUsersByFullName(firstName, lastName);
        return userUpdate;
    }
/**
 *  get all active users, used for a searched list
 * @return List of User objects that have isActive field set to "true"
 * @throws Exception 
 */
    
    public List<User> getAllActive() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllActive();
        return users;
    }
/**
 * get all active supervisor users
 * @param roleName
 * @return List of User objects 
 * @throws Exception 
 */
  
    public List<User> getAllActiveSupervisorsOrManagers(String roleName) throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllActiveSupervisorsOrManagers(roleName);
        return users;
    }

    /**
     * get all active supervisors
     * @param programId
     * @return List of User objects 
     * @throws Exception 
     */
    
    public List<User> getAllActiveSupervisorsByProgram(Short programId) throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllActiveSupervisorsByProgram(programId);
        return users;
    }

    public List<User> getAllActiveHotlineCoordinators() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAllActiveHotlineCoordinators();
        return users;
    }
/**
 * get all existing records from User table
 * @return List of User objects 
 * @throws Exception 
 */
   
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    /**
     * method for retrieving  a specific user
     * @param email 
     * @return User object that matches email 
     * @throws Exception 
     */

    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

/**
 * retrieve User object by last name
 * @param lastName last name
 * @return list of User objects that matches last name 
 * @throws Exception 
 */
    public List<User> getUserByLastName(String lastName) throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getUserByLastName(lastName);
        return users;
    }
/**
 * method to get isAdmin status of User object 
 * @param user_id
 * @return boolean true or false 
 * @throws Exception 
 */
    public boolean admin(int user_id) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.getByID(user_id);
        return user.getIsAdmin();
    }

 /**
  * method to persist new User into User table 
  * @param userId
  * @param email
  * @param isAdmin
  * @param userCity
  * @param firstName
  * @param lastName
  * @param isActive
  * @param password
  * @param dateOfBirth
  * @param phoneNumber
  * @param homeAddress
  * @param postalCode
  * @param registrationDate
  * @param teamId
  * @return String with message 
  * @throws Exception 
  */
    public String insert(int userId, String email, boolean isAdmin, String userCity, String firstName, String lastName, boolean isActive, String password, Date dateOfBirth, String phoneNumber, String homeAddress, String postalCode, Date registrationDate, int teamId) throws Exception {
        UserDB userDB = new UserDB();
        User checkUser = userDB.get(email);
        if (checkUser != null) {
            return "User with " + email + " already exists!";
        }
        String passwordSalt = getSalt();
        String passwordHash = getHash("password", passwordSalt);
        User user = new User(userId, email, isAdmin, firstName, lastName, isActive, registrationDate, passwordSalt, passwordHash);
        
        // setting users team
        TeamServices ts = new TeamServices();
        Team usersTeam = ts.get(teamId);
        
        user.setTeamId(usersTeam);
        user.setUserCity(userCity);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        user.setHomeAddress(homeAddress);
        user.setPostalCode(postalCode);

        userDB.insert(user);
        return "User with " + email + " successfully added!";
    }

/**
 * method to update existing User record in db
 * @param userId
 * @param email
 * @param isAdmin
 * @param userCity
 * @param firstName
 * @param lastName
 * @param isActive
 * @param password
 * @param dateOfBirth
 * @param phoneNumber
 * @param homeAddress
 * @param postalCode
 * @param registrationDate
 * @param teamId
 * @return String msg 
 * @throws Exception 
 */
    public String update(int userId, String email, boolean isAdmin, String userCity, String firstName, String lastName, boolean isActive, String password, Date dateOfBirth, String phoneNumber, String homeAddress, String postalCode, Date registrationDate, int teamId) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        if (user == null) {
            return "User does not exist!";
        }
        
        // setting users team
        TeamServices ts = new TeamServices();
        Team usersTeam = ts.get(teamId);
        
        user.setIsAdmin(isAdmin);
        user.setTeamId(usersTeam);
        user.setUserCity(userCity);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsActive(isActive);

        String passwordSalt = getSalt();
        String passwordHash = getHash(password, passwordSalt);
        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);

        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        user.setHomeAddress(homeAddress);
        user.setPostalCode(postalCode);

        userDB.update(user);

        return "User with " + email + " successfully updated!";
    }
/**
 * method to update existing User record field password 
 * @param email
 * @param path
 * @param url
 * @return boolean
 * @throws Exception 
 */
    public boolean resetPassword(String email, String path, String url) throws Exception {
        UserDB userDB = new UserDB();

        if (userDB.get(email) == null) {
            return false;
        }

        User user = userDB.get(email);

        //Context env = (Context) new InitialContext().lookup("java:comp/env");
        //String to = (String) env.lookup("webmail-username");
        String to = user.getEmail();
        String subject = "Ecssen Pro";
        String template = path + "/emailtemplates/accountinfo.html";

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", user.getFirstName());
        //tags.put("lastname", user.getLastName());
        //tags.put("email", user.getEmail());
        tags.put("link", url);

        try {
            GmailService.sendMail(to, subject, template, tags);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
/**
 * method to change password field in User table 
 * @param uuid
 * @param password
 * @return boolean
 */
    public boolean changePassword(String uuid, String password) {
        UserDB userDB = new UserDB();

        try {
            User user = userDB.getByUUID(uuid);
            String passwordSalt = getSalt();
            String passwordHash = getHash(password, passwordSalt);
            user.setPasswordSalt(passwordSalt);
            user.setPasswordHash(passwordHash);
            user.setResetPasswordUuid(null);
            updateNoCheck(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
/**
 * method to change(update) password field in User table
 * @param user
 * @param password
 * @return boolean 
 */
    public boolean changePasswordAccountPage(User user, String password) {
        //UserService us = new UserService();
        UserDB userDB = new UserDB();

        try {
            String passwordSalt = getSalt();
            String passwordHash = getHash(password, passwordSalt);
            user.setPasswordSalt(passwordSalt);
            user.setPasswordHash(passwordHash);
            user.setResetPasswordUuid(null);
            updateNoCheck(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * update password field in User table
     * @param user 
     */
    public void updateNoCheck(User user) {
        UserDB userDB = new UserDB();
        try {
            userDB.update(user);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * method to get Salt 
     * @return String
     */
    public String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[32];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
/**
 * method to get hash 
 * @param password
 * @param salt
 * @return string 
 * @throws NoSuchAlgorithmException 
 */
    public String getHash(String password, String salt) throws NoSuchAlgorithmException {
        String hashPassword = password + salt;

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.reset();
        messageDigest.update(hashPassword.getBytes());

        byte[] bytes = messageDigest.digest();
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            int v = aByte & 0xff;
            if (v < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(v));
        }
        return stringBuilder.toString();
    }
/**
 * get all User objects where isActive field is set to "true" then match 
 * to hotline program 
 * @return
 * @throws Exception 
 */
   
   
    public List<User> getActiveHotline() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getActiveHotline();
        return users;
    }

    public List<User> getVolunteers() throws Exception {
        ProgramTrainingDB programTrainingDB = new ProgramTrainingDB();
        List<User> volunteerList = new ArrayList<>();
        for (ProgramTraining programTraining : programTrainingDB.getAll()) {
            if (programTraining.getRoleId().getRoleName().equals("Volunteer")) {
                volunteerList.add(programTraining.getUser());
            }
        }
        return volunteerList;
    }
}
