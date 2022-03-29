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
import java.util.logging.Level;
import java.util.logging.Logger;

import models.*;
import models.util.*;
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
            
            StringBuilder storeData = new StringBuilder();
            storeData.append("var storeData = [");
            
            JSONKey[] storeKeys = { new JSONKey("storeId", false),
                                    new JSONKey("companyId", false),
                                    new JSONKey("name", true),
                                    new JSONKey("streetAddress", true),
                                    new JSONKey("postalCode", true),
                                    new JSONKey("city", true),
                                    new JSONKey("isActive", false),
                                    new JSONKey("phoneNum", true),
                                    new JSONKey("contactName", true) };
            
            JSONBuilder storeBuilder = new JSONBuilder(storeKeys);
            
            if(!stores.isEmpty())
            {
                int i;
                for(i=0; i<stores.size()-1; i++)
                {
                    storeData.append(buildStoreJSON(stores.get(i), storeBuilder));
                    storeData.append(',');
                }
                storeData.append(buildStoreJSON(stores.get(i), storeBuilder));
            }
            
            storeData.append("];");
            
            
            
            List<CompanyName> companies = companyService.getAll();
            
            StringBuilder companyData = new StringBuilder();
            companyData.append("var companyData = [");
            
            JSONKey[] companyKeys = { new JSONKey("id", false),
                                      new JSONKey("name", true) };
            
            JSONBuilder companyBuilder = new JSONBuilder(companyKeys);
            
            if(!companies.isEmpty())
            {
                int i;
                for(i=0; i<companies.size()-1; i++)
                {
                    companyData.append(buildCompanyJSON(companies.get(i), companyBuilder));
                    companyData.append(',');
                }
                    companyData.append(buildCompanyJSON(companies.get(i), companyBuilder));
            }
            companyData.append("];");
            
            
            request.setAttribute("storeData", storeData.toString());
            request.setAttribute("companyData", companyData.toString());
            
            getServletContext().getRequestDispatcher("/WEB-INF/store.jsp").forward(request, response);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }        
    }

    private String buildStoreJSON(Store store, JSONBuilder builder)
    {
        Object[] data = { store.getStoreId(),
                          store.getCompanyId().getCompanyId(),
                          store.getStoreName(),
                          store.getStreetAddress(),
                          store.getPostalCode(),
                          store.getStoreCity(),
                          store.getIsActive(),
                          store.getPhoneNum(),
                          store.getContact() };
        
        return builder.buildJSON(data);
    }
    
    private String buildCompanyJSON(CompanyName company, JSONBuilder builder)
    {
        Object[] data = { company.getCompanyId(),
                          company.getCompanyName() };
        
        return builder.buildJSON(data);
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
        // getting user action from JSP
        String action = request.getParameter("action");
try {
            switch (action) {
                // add new program
                case "add":
                    add(request, response);
                    break;
                // edit current store
                // change status of store  
                case "Edit":
                    edit(request, response);
                    break;
                // save edit changes
                case "update":
                    save(request, response);
                    break;
                // throw exception if the action is none of the above    
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(StoreServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
        
    }

     private void add(HttpServletRequest request, HttpServletResponse response) {

     StoreServices ss = new StoreServices();
     CompanyService cs = new CompanyService();
     
     try {
            List<Store> stores = ss.getAll();
            String storeName = request.getParameter("store-name");
            String streetAddress = request.getParameter("store-address");
            String postalCode = request.getParameter("postal-code");
            String storeCity = request.getParameter("city");
            String phoneNum = request.getParameter("");
            String contact = request.getParameter("");
            String status = request.getParameter("status");
            boolean isActive = status.equals("active");
            String companyId = request.getParameter("");
            short ci = Short.parseShort(companyId);
            CompanyName c = cs.get(ci);
            boolean isFound = false;
             for (int i = 0; i < stores.size(); i++) {
                if (stores.get(i).getStoreName().equals(storeName)) {


                    isFound = true;
                   // set usermsg "The store already exists"
                }
             
            }
        // creating the store trough store services
             if (isFound){
            String userMsg = ss.insert(streetAddress,postalCode,storeCity, storeName, isActive, phoneNum, contact, c); 

           }
                  
     

}
catch (Exception e) {
            Logger.getLogger(StoreServlet.class.getName()).log(Level.WARNING, null, e);
        }




}

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        StoreServices ss = new StoreServices();
        //CompanyService cs = new CompanyService();

  try {
            // getting the store that is to be edited
            Store editStore = ss.get(Short.parseShort(request.getParameter("storeID")));

            // setting store attribute to edit
            request.setAttribute("editStore", editStore);
            getServletContext().getRequestDispatcher("/WEB-INF/program.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }

}

    private void save(HttpServletRequest request, HttpServletResponse response) {
            StoreServices ss = new StoreServices();
            CompanyService cs = new CompanyService();

 try {
            // getting store ID
            int storeID = Integer.parseInt(request.getParameter("store-ID"));

            String storeName = request.getParameter("store-name");
            String streetAddress = request.getParameter("store-address");
            String postalCode = request.getParameter("store-postal-code");
            String storeCity = request.getParameter("store-city");
            String phoneNum = request.getParameter("store-phone");
            String contact = request.getParameter("store-contact");
            String status = request.getParameter("status");
            boolean isActive = status.equals("active");
            String companyName = request.getParameter("company-name");
//            short ci = Short.parseShort(companyId);
//            CompanyName c = cs.get(ci);

            // updating store
            String userMsg = ss.update(storeID, streetAddress, postalCode, storeCity, storeName, isActive, phoneNum, contact, companyName);

            response.sendRedirect("stores");
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }

}
    }
