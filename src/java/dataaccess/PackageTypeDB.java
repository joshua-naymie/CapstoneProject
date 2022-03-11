/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import java.util.List;
import models.PackageType;

/**
 *
 * @author srvad
 */
public class PackageTypeDB {
        public List<PackageType> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        
        try {         
            List<PackageType> o = em.createNamedQuery("PackageType.findAll", PackageType.class).getResultList();
            return o;
        } finally {
            em.close();
        }
    }

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
