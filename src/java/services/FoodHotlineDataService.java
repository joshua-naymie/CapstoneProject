/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.FoodHotlineDataDB;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.HotlineData;

/**
 *
 * @author srvad
 */
public class FoodHotlineDataService {
    public void insertHotlineData(HotlineData hd){
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        try {
            fDB.insertHotlineData(hd);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateHotlineData(HotlineData hd){
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        try {
            fDB.updateHotlineData(hd);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertFoodDeliveryData(FoodDeliveryData fd){
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        try {
            fDB.insertFoodDeliveryData(fd);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateFoodDeliveryData(FoodDeliveryData fd){
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        try {
            fDB.updateFoodDeliveryData(fd);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<FoodDeliveryData> getAllFoodDeliveryData() throws Exception {
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        List<FoodDeliveryData> fdd = fDB.getAllFoodDeliveryData();
        return fdd;
    }
}
