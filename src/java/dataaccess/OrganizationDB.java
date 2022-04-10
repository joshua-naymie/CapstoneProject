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
 *
 * @author srvad
 */
public class OrganizationDB {
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
