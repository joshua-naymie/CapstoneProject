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
import java.util.*;
import models.User;

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
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        List<User> test = entityManager.createNamedQuery("User.findAll").getResultList();
        User user = test.get(0);
        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);

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

    }
    
    // david
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // edit user
        // change status
        // create user 
        // work on exporting if we have time before use case is due
    }

}
