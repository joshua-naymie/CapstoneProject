/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.CompanyDB;
import java.util.List;
import models.CompanyName;

/**
 *
 * @author srvad
 */
public class CompanyService {
    public List<CompanyName> getAll() throws Exception {
        CompanyDB comDB = new CompanyDB();
        List<CompanyName> com = comDB.getAll();
        return com;
    }
    
      public CompanyName get (Short companyId)throws Exception{
        CompanyDB comDB = new CompanyDB();
        CompanyName com = comDB.get(companyId);
        return com;
    }
      
    public CompanyName getByName(String name) throws Exception
    {
        CompanyDB companyDB = new CompanyDB();
        
        return companyDB.getByName(name);
    }
}
