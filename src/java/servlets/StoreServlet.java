/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import models.util.JSONBuilder;
import models.util.JSONKey;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.List;

import models.*;
import services.*;

/**
 *
 * @author Main
 */
public class StoreServlet extends HttpServlet
{

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        CompanyService companyService = new CompanyService();
        StoreServices storeService = new StoreServices();
        
        try
        {
            List<Store> stores = storeService.getAll();
            
            JSONKey[] storeKeys = {
                                    new JSONKey("storeId", false),
                                    new JSONKey("companyId", false),
                                    new JSONKey("storeName", true),
                                    new JSONKey("streetAddress", true),
                                    new JSONKey("postalCode", true),
                                    new JSONKey("city", true),
                                    new JSONKey("isActive", false),
                                    new JSONKey("phoneNum", true),
                                    new JSONKey("contactName", true)
                                  };
            
            JSONBuilder storeBuilder = new JSONBuilder(storeKeys);
            
            JSONKey[] companyKeys = {
                                        new JSONKey("id", false),
                                        new JSONKey("name", true)
                                    };
            
            JSONBuilder companyBuilder = new JSONBuilder(companyKeys);
            
            if(stores.size() > 0)
            {
                int i;
                for(i=0; i<stores.size()-1; i++)
                {
                    
                }
            }
        }
        catch(Exception e)
        {
            
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        
    }
}