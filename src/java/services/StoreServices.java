/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.StoreDB;
import java.util.List;
import models.Store;

/**
 *
 * @author 840979
 */
public class StoreServices {


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

public String insert (String streetAddress, String postalCode, String storeCity, boolean isActive, String phoneNum, String contact) throws Exception{
       StoreDB storeDB = new StoreDB();
         Store checkStore = storeDB.getByStreetAddress(streetAddress);
                if (checkStore != null){
                return "This store already exists";
}
        Store newStore = new Store (streetAddress, postalCode, storeCity, isActive, phoneNum, contact);

        storeDB.insert(newStore);

        return "Store has been created";

}

}
