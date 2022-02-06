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
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        List<User> test = entityManager.createNamedQuery("User.findAll").getResultList();
        //User user = test.get(0);
        request.setAttribute("users", test);
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
        // Obtain the action from the JSP
        // get parameter name from front end
        String action = request.getParameter("action");

        // edit user
        // change status
        // create user 
        // try catch to handle what happens based on the action obtained
        // dummy names for now match the cases with the frontend
        try {
            switch (action) {
                // creating a new user
                case "Add":
                    // request.setAttribute("startView", true);
                    add(request, response);
                    break;

                // editing a current user
                case "Edit":
                    // request.setAttribute("editview", true);
                    edit(request, response);
                    break;

                // saving account status change
                case "Save":
                    save(request, response);
                    break;

                case "Cancel":
                    request.setAttribute("editview", false);
                    request.setAttribute("editAdminView", false);
                    response.sendRedirect("admin");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
        // work on exporting if we have time before use case is due
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        AccountServices accService = new AccountServices();

        try {
            // converting Strings to Date variables for DOB / registration date
//            String dobDate = request.getParameter("user_DOB");
//            Date dateOfBirth = new SimpleDateFormat("yyyy/MM/dd").parse(dobDate);

//            String regDate = request.getParameter("user_registration");
//            Date registrationDate = new SimpleDateFormat("yyyy/MM/dd").parse(regDate);
            // parsing team id from string to int
//            String sTeamId = request.getParameter("user_teamId");
//            int teamId = Integer.parseInt(sTeamId);
            //dummy date
            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-06");

            // inserting the new user
            // need to match the parameter names with the front end
            accService.insert(request.getParameter("username"),
                    //is admin
                    false,
                    request.getParameter("user_city"),
                    request.getParameter("user_firstname"),
                    request.getParameter("user_lastname"),
                    // is active
                    true,
                    request.getParameter("user_password"),
                    null,
                    request.getParameter("user_phone"),
                    request.getParameter("user_address"),
                    request.getParameter("user_postalcode"),
                    registrationDate,
                    2);
            
            request.setAttribute("users", accService.getAll());

            getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, e);
        }

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            AccountServices accService = new AccountServices();
            // use the account services to retrieve the account info for editing
            User editUser = accService.get(request.getParameter("username"));

            try {
                request.setAttribute("users", accService.getAll());
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, e);
                System.err.println("Error Occured retrieving user data");
                throw new Exception();
            }

            request.setAttribute("editUser", editUser);
            request.setAttribute("userName", editUser.getUserId());

            try {
                getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
                throw new Exception();
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            AccountServices accService = new AccountServices();

            // insert parameters into account services to save user
            // change parameters to match front end
            accService.update(request.getParameter("saveusername"),
                    //is admin
                    false,
                    // request.getParameter("user_city"),
                    "Calgary",
                    request.getParameter("saveuser_firstname"),
                    request.getParameter("saveuser_lastname"),
                    // is active
                    true,
                    // request.getParameter("saveuser_password"),
                    "password",
                    null,
                    request.getParameter("user_phone"),
                    request.getParameter("user_address"),
                    request.getParameter("user_postalcode"),
                    null,
                    0);

            request.setAttribute("users", accService.getAll());
            getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
