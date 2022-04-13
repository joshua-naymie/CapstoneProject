/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.List;
import models.Program;
import models.Role;


/**
 *class for CRUD operations on Role table 
 * @author 840979
 */
public class RoleDB {

    /**
     * method to retrieve all existing records (objects) in Role table 
     * @return List of Role objects
     * @throws Exception 
     */
public List<models.Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<models.Role> allRoles = em.createNamedQuery("Role.findAll", models.Role.class).getResultList();
            return allRoles;
        } finally {
            em.close();
        }
    }
/**
 * method to get Role object using role id(primary key) 
 * @param roleId role id 
 * @return Role object that matches the role id 
 * @throws Exception 
 */
    public Role get(short roleId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
    try{

    Role role = em.find(Role.class, roleId);
    return role;
}
    finally {
    em.close();
}
    }
/**
 * method to get Role object from Role table using role name 
 * @param roleName string role name
 * @return Role object that matches role name
 * @throws Exception 
 */
     public Role getByRoleName(String roleName) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {

              Query getrole = em.createNamedQuery("Role.findByRoleName", Program.class);
              try{
              Role r = (Role) getrole.setParameter("roleName", roleName).getSingleResult();
              return r;
              } catch (NoResultException e){
                  return null;
              }
            
        } finally {
            em.close();
        }
    }
    
/**
 * method to persist new Role object into Role table 
 * @param role new role object to be inserted 
 * @throws Exception 
 */
public void insert(Role role) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(role);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
/**
 *  method to update an existing Role object in Role table 
 * @param role Role object to be updated 
 * @throws Exception 
 */
public void update(Role role) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(role);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
