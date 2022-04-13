/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;
import models.Store;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlets.StoreServlet;

/**
 *class to perform CRUD operations on Store table
 * @author 840979
 */
public class StoreDB {
    
    /**
     * method to get all existing stores in Store table 
     * @return List of Store objects 
     * @throws Exception 
     */
public List<Store> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<Store> allStores = em.createNamedQuery("Store.findAll", models.Store.class).getResultList();
            return allStores;
        } finally {
            em.close();
        }
    }

/**
 * method to get list of stores from Store table using company Id 
 * @param companyId company id
 * @return list of Store object that match company id
 */
    public List<Store> getAllByCompany (short companyId){
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery ("SELECT s FROM Store S WHERE s.companyId = :company", Store.class);
        q.setParameter("company", companyId);
        try
        {
            List<Store> result = q.getResultList();
            return result;
        }
        finally
        {
            em.close();
        }
    }

/**
 * method to retrieve all stores that match store name specified 
 * @param storeName store name
 * @return List of Store objects that match store name
 * @throws Exception 
 */
    public List<Store> getStoresByName(String storeName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            Query getFoundStores = em.createNamedQuery("Store.findByStoreName", models.Store.class);
            List<Store> foundStores = getFoundStores.setParameter("storeName", "%" + storeName + "%").getResultList();
            return foundStores;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
/**
 * method to retrieve a Store object using store id (primary key) from Store table
 * @param storeId store id
 * @return Store object that matches store id 
 * @throws Exception 
 */
    public Store get(int storeId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            Store s = em.find(Store.class, storeId);
            return s;
        } finally {
            em.close();
        }
    }
/**
 * method to get a Store object from Store table using street address
 * @param streetAddress street address 
 * @return Store object that matches field StreetAddress 
 */
    public Store getByStreetAddress(String streetAddress)  {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
          Query getStoreByAddress= em.createNamedQuery("Store.findByStreetAddress", models.Store.class);
          Store s = (Store)getStoreByAddress.setParameter("streetAddress", streetAddress).getSingleResult();
            em.close();
            return s;
            
        } catch(NoResultException e) {
        em.close();
        return null;
         
    }
         
    }
   /**
    * persist new Store object into Store table 
    * @param store Store object to be inserted
    * @throws Exception 
    */
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
/**
 * method to update existing Store object in store table
 * @param store Store object to be updated
 * @throws Exception 
 */
     public void update(Store store) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            System.out.println("POSTAL: " + store.getPostalCode());
            System.out.println("CITY: " + store.getStoreCity());
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
