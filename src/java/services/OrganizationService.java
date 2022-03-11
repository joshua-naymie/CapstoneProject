/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.OrganizationDB;
import java.util.List;
import models.Organization;

/**
 *
 * @author srvad
 */
public class OrganizationService {
    
    public List<Organization> getAll() throws Exception {

        OrganizationDB OrganizationDB = new OrganizationDB();
        List<Organization> p = OrganizationDB.getAll();
        return p;

    }

    public Organization get(short roleId) throws Exception {
        OrganizationDB organizationDB = new OrganizationDB();
        Organization p = organizationDB.get(roleId);
        return p;

    }
}
