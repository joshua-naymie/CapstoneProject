/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;
import models.User;
import java.util.List;
import jakarta.persistence.EntityManager;

/**
 *
 * @author DWEI
 */
public class UserDB {
    
    // irina
    // getAll for getting all users
public List<User> getAll() throws Exception {
    EntityManager em = DBUtil.getEMFactory().createEntityManager();
    try {
      List<User> allUsers= em.createNamedQuery("User.findAll", User.class).getResultList();
      return allUsers;
    } finally {
      em.close();
    }
    }
    
    //irina
    // get for getting a specific user

public User get (String email) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }       
    }
    
    //agambeer
    // getting a list of users based on matching last name
    
    //agambeer
    // Insert for creating users
    
    //agambeer
    // update for editing users
}
