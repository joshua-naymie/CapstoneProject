/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import java.util.List;
import models.PackageType;

/**
 * data access class used to retrieve PackageType objects from PackageType table
 * @author srvad
 */
public class PackageTypeDB {
    
  /**
   * method to retrieve all PackageType objects that are currently in database
   * @return List of all PackageType objects 
   * @throws Exception 
   */
        public List<PackageType> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try {         
            List<PackageType> o = em.createNamedQuery("PackageType.findAll", PackageType.class).getResultList();
            return o;
        } finally {
            em.close();
        }
    }
/**
 * method to get an object from PackageType table using role id 
 * @param roleId 
 * @return PackageType object that matches role id 
 * @throws Exception 
 */
    public PackageType get(short roleId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try{
            PackageType o = em.find(PackageType.class, roleId);
            return o;
        }
        finally {
            em.close();
        }
    }
}
