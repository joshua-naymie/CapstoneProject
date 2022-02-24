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

public String insert (String streetAddress, String postalCode, String storeCity, boolean isActive, String phoneNum, String contact){
        StoreDB storeDB = new StoreDB();
        Store newStore = new Store ();
return "";
}

}
