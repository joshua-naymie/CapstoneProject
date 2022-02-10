/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.UserDB;
import java.util.*;
import models.*;

/**
 *
 * @author DWEI
 */
public class AccountServices {
    // If we want to let users update their password through a secure email, we have to add a clolumn to the database
    
    // irina
    // getAll for getting all users
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
}
    //irina
    // get for getting a specific user
    public User get (String email)throws Exception{
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;


}
    //agambeer
    // get for searching users by last name
    public List<User> getUserByLastName(String lastName) throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getUserByLastName(lastName);
        return users;
    }

    //agambeer
    // Insert for creating users
    public String insert(String userId, boolean isAdmin, String userCity, String firstName, String lastName, boolean isActive, String userPassword, Date dateOfBirth, String phoneNumber, String homeAddress, String postalCode, Date registrationDate, int teamId) throws Exception {
        UserDB userDB = new UserDB();
        User checkUser = userDB.get(userId);
        if (checkUser != null) {
            return "User with " + userId + " already exists!";
        }
        User user = new User(userId, isAdmin, firstName, lastName, isActive, userPassword, registrationDate);

        user.setTeamId(new Team(teamId));
        user.setUserCity(userCity);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        user.setHomeAddress(homeAddress);
        user.setPostalCode(postalCode);

        userDB.insert(user);
        return "User with " + userId + " successfully added!";
    }

    //agambeer
    // update for editing users
    // account status change
    public String update(String userId, boolean isAdmin, String userCity, String firstName, String lastName, boolean isActive, String userPassword, Date dateOfBirth, String phoneNumber, String homeAddress, String postalCode, Date registrationDate, int teamId) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(userId);
        if (user == null) {
            return "User does not exist!";
        }

        user.setIsAdmin(isAdmin);
        user.setTeamId(new Team(teamId));
        user.setUserCity(userCity);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setIsActive(isActive);
        user.setUserPassword(userPassword);
        user.setDateOfBirth(dateOfBirth);
        user.setPhoneNumber(phoneNumber);
        user.setHomeAddress(homeAddress);
        user.setPostalCode(postalCode);

        userDB.update(user);

        return "User with " + userId + " successfully updated!";
    }
    
}
