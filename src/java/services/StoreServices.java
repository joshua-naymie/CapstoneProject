/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.StoreDB;
import java.util.List;
import models.CompanyName;
import models.Store;

/**
 *class to perform some crud operations on Store table
 * @author 840979
 */
public class StoreServices {

    
    /**
     * method to retrieve all existing records in Store table filtered by company id
     * @param companyId
     * @return list of Store objects
     */
public List<Store> getAllByCompany (short companyId){
        StoreDB storeDB = new StoreDB();
        List<Store> stores = storeDB.getAllByCompany(companyId);
        return stores;

}

/**
 * method to retrieve all existing records in Store table
 * @return list of Store objects 
 * @throws Exception 
 */
public List<Store> getAll() throws Exception {
        StoreDB storeDB = new StoreDB();
        List<Store> stores = storeDB.getAll();
        return stores;
    }
    

/**
 * method to retrieve specific Store record using primary key
 * @param storeId store id
 * @return Store object
 * @throws Exception 
 */
  public Store get (int storeId)throws Exception{
        StoreDB storeDB = new StoreDB();
        Store store = storeDB.get(storeId);
        return store;
    }
  
/**
 * method to persist new record into Store table
 * @param streetAddress
 * @param postalCode
 * @param storeCity
 * @param storeName
 * @param isActive
 * @param phoneNum
 * @param contact
 * @param companyName
 * @return string msg
 * @throws Exception 
 */
public String insert (String streetAddress, String postalCode, String storeCity,String storeName, boolean isActive, String phoneNum, String contact, String companyName) throws Exception{
       StoreDB storeDB = new StoreDB();
      
            if (storeDB.getByStreetAddress(streetAddress)!= null){
               return "This store already exists";
}  else{
      CompanyService companyService = new CompanyService();
      CompanyName cn = companyService.getByName(companyName);
//System.out.println("----====" + companyService.getByName(companyName).getCompanyName() + "====----");
       if ( cn == null){
        companyService.insert(companyName);
        cn = companyService.getByName(companyName);
}
              
       //boolean isActive, String phoneNum,String postalCode, String storeCity, String storeName ,String streetAddress,CompanyName companyId
       Store newStore = new Store ( 0,contact,isActive,phoneNum, postalCode,storeCity,storeName,streetAddress,  cn );
       System.out.println("----====Postal" + postalCode + "====----");
        storeDB.insert(newStore);
        return "Store has been created";
}
}


/**
 * method to update existing Store record
 * @param storeId
 * @param streetAddress
 * @param postalCode
 * @param storeCity
 * @param storeName
 * @param isActive
 * @param phoneNum
 * @param contact
 * @param companyName
 * @return string msg
 * @throws Exception 
 */
public String update (int storeId, String streetAddress, String postalCode, String storeCity, String storeName, boolean isActive, String phoneNum, String contact, String companyName) throws Exception{
       StoreDB storeDB = new StoreDB();
       CompanyService companyService = new CompanyService();
       System.out.println("----====" + companyService.getByName(companyName).getCompanyName() + "====----");
        CompanyName cn = companyService.getByName(companyName);
       if ( cn == null){
        companyService.insert(companyName);
}
       Store toUpdate = storeDB.get(storeId);
       if (toUpdate == null ){
       return "Store does not exist";
       
}
       toUpdate.setContact(contact);
       toUpdate.setPhoneNum(phoneNum);
       toUpdate.setPostalCode(postalCode);
       toUpdate.setIsActive(isActive);
       toUpdate.setStoreCity(storeCity);
       toUpdate.setStoreName(storeName);
       toUpdate.setStreetAddress(streetAddress);
       toUpdate.setCompanyId(cn);
       
       storeDB.update(toUpdate);
        return "Store "+ storeName + " has been added.";
}

/**
 * method to retrieve Store records by store name
 * @param storeName
 * @return List of Store objects matching store name
 * @throws Exception 
 */
public List<Store> getStoresByName(String storeName) throws Exception {
       
        StoreDB storeDB = new StoreDB();
        List<Store> stores = storeDB.getStoresByName(storeName);
        return stores;
    }

}
