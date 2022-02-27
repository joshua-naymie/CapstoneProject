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
import models.Role;


/**
 *
 * @author 840979
 */
public class RoleDB {

public List<models.Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<models.Role> allRoles = em.createNamedQuery("Role.findAll", models.Role.class).getResultList();
            return allRoles;
        } finally {
            em.close();
        }
    }

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
