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
 *
 * @author srvad
 */
public class FoodHotlineDataDB {

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
