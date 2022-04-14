/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import models.util.JSONKey;
import models.util.JSONBuilder;
import models.util.CSVBuilder;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import models.*;
import services.AccountServices;

/**
 * handles user interactions on the users list webpage, /users
 * 
 */
public class AccountServlet extends HttpServlet {

    /**
     *
     * Backend code for sending up user data
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters errors
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // getting a list of all users and sending it to the jsp as json data
        AccountServices as = new AccountServices();
        List<User> allUsers = null;
        try {
            allUsers = as.getAll();  //get a list of all users
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("users", allUsers);
        
        // sending Json data of all user info to the front end
        StringBuilder returnData = new StringBuilder();
//        String OUTPUT_FORMAT = "{\"id\":%s, \"firstName\":%s, \"lastName\":%s, \"phoneNum\":%s, \"address\":%s},";
        returnData.append("var data = [");
        
        // Create keys
        JSONKey[] keys = { new JSONKey("id", true),
                           new JSONKey("firstName", true),
                           new JSONKey("lastName", true) };
        
        // Create builder with above keys
        JSONBuilder builder = new JSONBuilder(keys);
        
        // Create user JSON objects
        if(allUsers.size() > 0)
        {
            int i;
            for(i=0; i<allUsers.size()-1; i++)
            {
                returnData.append(buildUserJSON(allUsers.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildUserJSON(allUsers.get(i), builder));         
        }
        returnData.append("];");
        
        
        request.setAttribute("userData", returnData);
        getServletContext().getRequestDispatcher("/WEB-INF/userlist.jsp").forward(request, response);
    }
    
    /**
     * Creates a user JSON object
     * @param user  The User to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A User JSON as a String
     */
    private String buildUserJSON(User user, JSONBuilder builder)
    {
        Object[] userValues = { user.getEmail(),
                                user.getFirstName(),
                                user.getLastName() };
            
        return builder.buildJSON(userValues);
    }
    
    // checking if the string value is null so it can be appropriately returned to the
    // json file
    private String checkNull(String check){
        if(check == null){
            return "null";
        }
        return "\""+check+"\"";
    }

    /**
     *
     * Backend code for handling exporting csv
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters errors
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtain the action from the JSP
        String action = request.getParameter("action");

        // try catch to handle what happens based on the action obtained
        try {
            switch (action) {
                // creating a new user
                case "edit":
                    edit(request, response);
                    break;
                    
                case "export-csv":
                    exportCSV(request, response);
                    break;
                    
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);

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
//            request.setAttribute("userName", editUser.getUserId());

            try {
                response.sendRedirect("edit?username=" + editUser.getUserId());  
//                getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
                throw new Exception();
            }
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }

    }
    
    private void exportCSV(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"userlist.csv\"");

            AccountServices as = new AccountServices();
            List<User> users = as.getAll();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            CSVBuilder builder = new CSVBuilder();
            
            String[] tableHeader = { "Email",
                                     "Last Name",
                                     "First Name",
                                     "Phone Number",
                                     "Street Address",
                                     "City",
                                     "Postal Code",
                                     "Date of Birth" };
            
            builder.addRecord(tableHeader);
            
            for(User user : users)
            {
                String dateOfBirth = user.getDateOfBirth() == null
                                                            ? null
                                                            : dateFormat.format(user.getDateOfBirth());
                
                Object[] recordData = { user.getEmail(),
                                        user.getLastName(),
                                        user.getFirstName(),
                                        user.getPhoneNumber(),
                                        user.getHomeAddress(),
                                        user.getUserCity(),
                                        user.getPostalCode(),
                                        dateOfBirth };
                
                builder.addRecord(recordData);
            }
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(builder.printFile());
            writer.flush();
            
            return;
        }
        catch (Exception ex)
        {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
