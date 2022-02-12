/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import services.AccountServices;

/**
 *
 * @author Main
 */
public class UserServlet extends HttpServlet {

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
            throws ServletException, IOException {
        String id = request.getParameter("username");
        if (id != null) {
            AccountServices accService = new AccountServices();
            User editUser = new User();
            try {
                editUser = accService.get(id);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, ex);
            }
            // make the user object into json data
            StringBuilder returnData = new StringBuilder();
            String OUTPUT_FORMAT = "var editUser = {\"id\":%s, \"firstName\":%s, \"lastName\":%s, \"phoneNum\":%s, \"address\":%s,"
                    + "\"isAdmin\":%b,\"city\":%s,\"isActive\":%b, \"DOB\":%s, \"address\":%s, \"postalCode\":%s,"
                    + "\"regDate\":%s, \"teamId\":%s}";

            
            // turning DOB and registration date into strings
            String DOB = dateToString(editUser.getDateOfBirth());
            String regDate = dateToString(editUser.getRegistrationDate());
            // converting team Id to string
            //String teamId = editUser.getTeamId().toString();
            
            // appending json data to be returned into a string builder
            returnData.append(String.format(OUTPUT_FORMAT, checkNull(editUser.getUserId()), checkNull(editUser.getFirstName()),
                    checkNull(editUser.getLastName()), checkNull(editUser.getPhoneNumber()), checkNull(editUser.getHomeAddress()),
                    editUser.getIsAdmin(), checkNull(editUser.getUserCity()), editUser.getIsActive(), DOB, checkNull(editUser.getHomeAddress()),
                    checkNull(editUser.getPostalCode()), regDate, "2"));
            
            
            
            request.setAttribute("userData", returnData);
        }
        System.out.println("ID: " + id);
//        User test = (User) request.getAttribute("editUser");
//        System.out.println(test.getFirstName());
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    // checking if the string value is null so it can be appropriately returned to the
    // json file
    private String checkNull(String check) {
        if (check == null) {
            return "null";
        }
        return "\"" + check + "\"";
    }
    
    // convert a date object to string
    // adds quotes to the dates
    private String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        
        return "\"" + simpleDateFormat.format(date) + "\"";
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
//                    request.setAttribute("editview", false);
//                    request.setAttribute("editAdminView", false);
                    response.sendRedirect("Account");
                    break;
                default:
                    System.out.println("no action picked");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
//            log("Error Occured carrying out action:" + action);
        }
        // work on exporting if we have time before use case is due
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        AccountServices accService = new AccountServices();

        try {
            // converting Strings to Date variables for DOB / registration date
            String dobDate = request.getParameter("birthday");
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dobDate);

            String regDate = request.getParameter("signupdate");
            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
            // parsing team id from string to int
//            String sTeamId = request.getParameter("user_teamId");
//            int teamId = Integer.parseInt(sTeamId);

            //dummy date to be deleted
//            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-06");
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
                    // DOB
                    dateOfBirth,
                    request.getParameter("user_phone"),
                    request.getParameter("user_address"),
                    request.getParameter("user_postalcode"),
                    // registration date
                    registrationDate,
                    2);
            // test print statements to be deleted
            //System.out.println(request.getParameter("username") + request.getParameter("user_firstname"));

            request.setAttribute("users", accService.getAll());
            // Redirect back to the account page
            response.sendRedirect("Account");
            return;
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        String endURL = request.getServletPath();
        if (endURL.equals("add")) {
        }
        try {
            AccountServices accService = new AccountServices();
            // use the account services to retrieve the account info for editing
            User editUser = accService.get(request.getParameter("username"));

            try {
                request.setAttribute("users", accService.getAll());
            } catch (Exception e) {
                e.printStackTrace();
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, e);
                System.err.println("Error Occured retrieving user data");
                throw new Exception();
            }

            request.setAttribute("editUser", editUser);
            // request.setAttribute("userName", editUser.getUserId());

            try {
                getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, ex);
                throw new Exception();
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, ex);
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
