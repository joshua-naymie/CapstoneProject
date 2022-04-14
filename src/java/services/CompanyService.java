/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.CompanyDB;
import java.util.List;
import models.CompanyName;

/**
 *class to perform some crud operations on CompanyName table 
 * @author srvad
 */
public class CompanyService {
    
    /**
     * method to get all existing CompanyName records
     * @return list of CompanyName objects
     * @throws Exception 
     */
    public List<CompanyName> getAll() throws Exception {
        CompanyDB comDB = new CompanyDB();
        List<CompanyName> com = comDB.getAll();
        return com;
    }
    
    /**
     * method to retrieve CompanyName record by id
     * @param companyId company id
     * @return CompanyName object matching the id
     * @throws Exception 
     */
      public CompanyName get (Short companyId)throws Exception{
        CompanyDB comDB = new CompanyDB();
        CompanyName com = comDB.get(companyId);
        return com;
    }
      
      /**
       * method to retrieve CompanyName by name 
       * @param name
       * @return CompanyName object matching name field
       * @throws Exception 
       */
    public CompanyName getByName(String name) throws Exception
    {
        CompanyDB companyDB = new CompanyDB();
        
        return companyDB.getByName(name);
    }

/**
 * method to persist new CompanyName record into CompanyName table
 * @param companyName company name
 * @return string 
 */
    public String insert (String companyName){
        CompanyDB companyDB = new CompanyDB();
        CompanyName cn = new CompanyName (companyName);
        companyDB.insert(cn);
        return "Company added"; 
}
}
