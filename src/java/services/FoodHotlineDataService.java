/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.FoodHotlineDataDB;
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
    
    public void insertFoodDeliveryData(FoodDeliveryData fd){
        FoodHotlineDataDB fDB = new FoodHotlineDataDB();
        try {
            fDB.insertFoodDeliveryData(fd);
        } catch (Exception ex) {
            Logger.getLogger(AccountServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
