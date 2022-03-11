/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.PackageTypeDB;
import java.util.List;
import models.PackageType;

/**
 *
 * @author srvad
 */
public class PackageTypeService {
    public List<PackageType> getAll() throws Exception {

        PackageTypeDB packageTypeDB = new PackageTypeDB();
        List<PackageType> p = packageTypeDB.getAll();
        return p;

    }

    public PackageType get(short roleId) throws Exception {
        PackageTypeDB packageTypeDB = new PackageTypeDB();
        PackageType p = packageTypeDB.get(roleId);
        return p;

    }
}
