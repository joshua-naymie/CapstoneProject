/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;

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


    // agambeer
    // get list for matching last name
        // might need to change a drop down for different search( will ask )
    
    //agameer
    // Insert for creating users
    
    //agambeer
    // update for editing users
        // account status change
    
}
