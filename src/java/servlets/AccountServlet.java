/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import dataaccess.DBUtil;
import jakarta.persistence.EntityManager;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import models.User;
import services.AccountServices;

/**
 *
 * @author DWEI
 */
public class AccountServlet extends HttpServlet {

    // saurav
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Initial testing
//        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
//        List<User> test = entityManager.createNamedQuery("User.findAll").getResultList();
        //User user = test.get(0);
//        request.setAttribute("users", test);
//        getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);

        // Retrieve user Data
        // directing the page to the appropriate Jsp based on what the user clicks (edit, change status, create)
//        switch (request.getServletPath()) {
//            case "/login":
//                request.setAttribute(PAGE_STATE, "\"login\"");
//                break;
//            case "/signup":
//                request.setAttribute(PAGE_STATE, "\"signup\"");
//                break;
//            default:
//                request.setAttribute(PAGE_STATE, "WRONG");
//                break;
//        }
        // loading the jsp
        // actual code
        AccountServices as = new AccountServices();
        List<User> allUsers = null;
        try {
            allUsers = as.getAll();  //get a list of all users
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", allUsers);
        
        
        // Json to be tested
        StringBuilder returnData = new StringBuilder();
        String OUTPUT_FORMAT = "{\"id\":%s, \"firstName\":%s, \"lastName\":%s, \"phoneNum\":%s, \"address\":%s},";
        returnData.append("[");
        for (User u : allUsers) {
            returnData.append(String.format(OUTPUT_FORMAT, checkNull(u.getUserId()), checkNull(u.getFirstName()), 
                                            checkNull(u.getLastName()), checkNull(u.getPhoneNumber()), checkNull(u.getHomeAddress())));
        }
        returnData.deleteCharAt(returnData.length() - 1);
        returnData.append("]");
        //response.setContentType("text/html");
        //response.getWriter().write(returnData.toString());
        request.setAttribute("userData", returnData);
        getServletContext().getRequestDispatcher("/WEB-INF/userlist.jsp").forward(request, response);
    }
    
    // checking if the string value is null so it can be appropriately returned to the
    // json file
    private String checkNull(String check){
        if(check == null){
            return "null";
        }
        return "\""+check+"\"";
    }

    // david
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
}