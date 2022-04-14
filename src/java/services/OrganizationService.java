/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.OrganizationDB;
import java.util.List;
import models.Organization;

/**
 *class to perform some crud operations on Organization table 
 * @author srvad
 */
public class OrganizationService {
   /**
    * method to get all existing records from Organization table 
    * @return List of Organization objects 
    * @throws Exception 
    */ 
    public List<Organization> getAll() throws Exception {

        OrganizationDB OrganizationDB = new OrganizationDB();
        List<Organization> p = OrganizationDB.getAll();
        return p;

    }

    /**
     * method to retrieve a specific record from Organization table using primary key
     * @param orgId id
     * @return Organization object matching the id 
     * @throws Exception 
     */
    public Organization get(Integer orgId) throws Exception {
        OrganizationDB organizationDB = new OrganizationDB();
        Organization p = organizationDB.get(orgId);
        return p;

    }
}
