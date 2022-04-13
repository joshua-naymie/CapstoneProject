/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import models.CompanyName;

/**
 * Data access class, implements CRUD operations on Company table in database 
 * @author srvad
 */
public class CompanyDB {
    
    /**
     *  method to retrieve CompanyName object from database using companyId
     * @param companyId
     * @return CompanyName object that matches the compnyId
     * @throws Exception if object does not exist. 
     */
    public CompanyName get(Short companyId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            CompanyName c = em.find(CompanyName.class, companyId);
            return c;
        } finally {
            em.close();
        }
    }
      
    /**
     * method to retrieve a list of all CompanyName objects from database 
     * @return List containing CompanyName objects
     * @throws Exception if no data to retrieve 
     */
    public List<CompanyName> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<CompanyName> allCompanies = em.createNamedQuery("CompanyName.findAll", models.CompanyName.class).getResultList();
            return allCompanies;
        } finally {
            em.close();
        }
    }
    
    /**
     * method to retrieve a CompanyName object from database using company name field
     * @param name of the company 
     * @return CompanyName object that matches passed value for company name 
     */
    public CompanyName getByName(String name) 
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        try
        {
            TypedQuery query = entityManager.createNamedQuery("CompanyName.findByCompanyName", CompanyName.class);
            query.setParameter("companyName", name);
            
            return (CompanyName)query.getSingleResult();

        }
        catch(NoResultException e) {
        entityManager.close();
        return null;
    }
    }

    /**
     * method to insert new CompanyName object into CompanyName table in database 
     * @param cn - new CompanyName object 
     */
     public void insert (CompanyName cn){

 EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(cn);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
}
}
