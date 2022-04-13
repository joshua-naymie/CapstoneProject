/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import models.FoodDeliveryData;
import models.HotlineData;

/**
 *Data access class, implements CRUD operations on HotlineData and 
 * FoodDeliveryDate tables in database
 * @author srvad
 */
public class FoodHotlineDataDB {

    /**
     * method to update HotlineData object with database 
     * @param hd - new HotlineData object
     * @throws Exception 
     */
    public void insertHotlineData(HotlineData hd) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(hd);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
        /**
     * method to update HotlineData object with database 
     * @param hd - new HotlineData object
     * @throws Exception 
     */
    public void updateHotlineData(HotlineData hd) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(hd);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

/**
 * method to persist new FoodDeliveryData object into FoodDeliveryData table
 * @param fd - new FoodDeliveryData object
 * @throws Exception 
 */
    public void insertFoodDeliveryData(FoodDeliveryData fd) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(fd);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    /**
 * method to persist new FoodDeliveryData object into FoodDeliveryData table
 * @param fd - new FoodDeliveryData object
 * @throws Exception 
 */
    public void updateFoodDeliveryData(FoodDeliveryData fd) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(fd);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    

/**
 * method to get a list of all objects currently in FoodDeliveryData table 
 * @return List of FoodDeliveryData objects
 * @throws Exception 
 */
    public List<FoodDeliveryData> getAllFoodDeliveryData() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<FoodDeliveryData> allFoodData = em.createNamedQuery("FoodDeliveryData.findAll", models.FoodDeliveryData.class).getResultList();
            return allFoodData;
        } finally {
            em.close();
        }
    }
}
