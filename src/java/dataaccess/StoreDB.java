/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;
import models.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 *
 * @author 840979
 */
public class StoreDB {


public List<Store> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<Store> allStores = em.createNamedQuery("Store.findAll", models.Store.class).getResultList();
            return allStores;
        } finally {
            em.close();
        }
    }

    public Store get(int storeId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            Store s = em.find(Store.class, storeId);
            return s;
        } finally {
            em.close();
        }
    }

    public Store getByStreetAddress(String streetAddress) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            Store s = em.find(Store.class, streetAddress);
            return s;
        } finally {
            em.close();
        }
    }
   
    public void insert(Store store) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(store);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }


     public void update(Store store) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(store);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}


