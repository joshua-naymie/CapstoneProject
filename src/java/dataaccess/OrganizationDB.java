/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import models.Organization;

/**
 *Class used to retrieve objects(records) from Organization table
 * @author srvad
 */
public class OrganizationDB {
    
/**
 * method to retrieve a list of all records/objects from Organization table
 * @return List of Organization objects currently in database 
 * @throws Exception 
 */
    public List<Organization> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try {         
            Query q = em.createQuery("SELECT o FROM Organization o WHERE o.isActive = :isActive ORDER BY o.orgName", Organization.class);
            q.setParameter("isActive", true);
            List<Organization> all = q.getResultList();
            return all;
        } finally {
            em.close();
        }
    }
/**
 * method to retrieve Organization object from Organization table using id
 * @param orgId - organization id (primary key)
 * @return Organization object matching passed id 
 * @throws Exception 
 */
    public Organization get(Integer orgId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try{
            Organization o = em.find(Organization.class, orgId);
            return o;
        }
        finally {
            em.close();
        }
    }
}
