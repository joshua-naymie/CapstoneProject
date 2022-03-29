/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import models.CompanyName;

/**
 *
 * @author srvad
 */
public class CompanyDB {
    
    public CompanyName get(Short companyId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            CompanyName c = em.find(CompanyName.class, companyId);
            return c;
        } finally {
            em.close();
        }
    }
      
    public List<CompanyName> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<CompanyName> allCompanies = em.createNamedQuery("CompanyName.findAll", models.CompanyName.class).getResultList();
            return allCompanies;
        } finally {
            em.close();
        }
    }
    
    public CompanyName getByName(String name) throws Exception
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        try
        {
            TypedQuery query = entityManager.createNamedQuery("CompanyName.findByCompanyName", CompanyName.class);
            query.setParameter("companyName", name);
            return (CompanyName)query.getSingleResult();
        }
        finally
        {
            entityManager.close();
        }
    }
}
