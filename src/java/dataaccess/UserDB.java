/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import models.User;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

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
//            List<User> allUsers = em.createNamedQuery("User.findAll", User.class).getResultList();
            Query q = em.createQuery("SELECT u FROM User u ORDER BY u.lastName, u.firstName", User.class);
             List<User> allUsers = q.getResultList();
            return allUsers;
        } finally {
            em.close();
        }
    }

    //irina
    // get for getting a specific user
    public User get(String email) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }

    public List<User> getUserByLastName(String lastName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery("SELECT u FROM User u WHERE u.lastName LIKE :keyword ORDER BY u.lastName, u.firstName", User.class);
        q.setParameter("keyword", lastName + "%");
        try {
            List<User> allUsers = q.getResultList();
            return allUsers;
        } finally {
            em.close();
        }
    }

    //agambeer
    // Insert for creating users
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    //agambeer
    // update for editing users
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    public User getByUUID(String uuid) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try{
            Query q = em.createNamedQuery("User.findByResetPasswordUuid");
            q.setParameter("resetPasswordUuid", uuid);
            return (User) q.getSingleResult();
        }finally{
            em.close();
        } 
    }

}
