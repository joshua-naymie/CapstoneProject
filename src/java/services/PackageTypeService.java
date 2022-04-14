/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.PackageTypeDB;
import java.util.List;
import models.PackageType;

/**
 *class to perform some crud operations on PackageType table
 * @author srvad
 */
public class PackageTypeService {
    
    /**
     * method to get all existing records in PackageType table
     * @return
     * @throws Exception 
     */
    public List<PackageType> getAll() throws Exception {

        PackageTypeDB packageTypeDB = new PackageTypeDB();
        List<PackageType> p = packageTypeDB.getAll();
        return p;

    }
/**
 * method to retrieve PackageType record using roleId
 * @param roleId role id
 * @return PackageType object matching role id
 * @throws Exception 
 */
    public PackageType get(short roleId) throws Exception {
        PackageTypeDB packageTypeDB = new PackageTypeDB();
        PackageType p = packageTypeDB.get(roleId);
        return p;

    }
}
