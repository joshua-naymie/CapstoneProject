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
 *
 * @author 840979
 */
public class StoreServices {

public List<Store> getAllByCompany (short companyId){
        StoreDB storeDB = new StoreDB();
        List<Store> stores = storeDB.getAllByCompany(companyId);
        return stores;

}
public List<Store> getAll() throws Exception {
        StoreDB storeDB = new StoreDB();
        List<Store> stores = storeDB.getAll();
        return stores;
    }
    


  public Store get (int storeId)throws Exception{
        StoreDB storeDB = new StoreDB();
        Store store = storeDB.get(storeId);
        return store;
    }
  

public String insert (String streetAddress, String postalCode, String storeCity,String storeName, boolean isActive, String phoneNum, String contact, String companyName) throws Exception{
       StoreDB storeDB = new StoreDB();
         Store checkStore = storeDB.getByStreetAddress(streetAddress);
      CompanyService companyService = new CompanyService();
      CompanyName cn = companyService.getByName(companyName);
       if ( cn == null){
        companyService.insert(companyName);
        cn = companyService.getByName(companyName);
}
               if (checkStore != null){
               return "This store already exists";
}  
       
       Store newStore = new Store ( streetAddress, postalCode, storeCity, storeName, isActive, cn);
        storeDB.insert(newStore);
        return "Store has been created";
}

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
       
       storeDB.update(toUpdate);
        return "Store "+ storeName + " has been added.";
}

}
