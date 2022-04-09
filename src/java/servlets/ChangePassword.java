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
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;
import services.AccountServices;

/**
 * Validate the current password entered by the user and let the user
 * enter a new password and save it.
 * 
 * @author srvad
 */
public class ChangePassword extends HttpServlet {

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
        
        //get the logged in user from the session
        AccountServices as = new AccountServices();
        
        HttpSession httpSession = request.getSession();
        int loggedInUserId = -1;
        String userName = "";
        
        if (httpSession.getAttribute("email") != null) {
            loggedInUserId = (int) httpSession.getAttribute("email");
            System.out.println(loggedInUserId);

        }

        //loggedInUserId = 4;
        //get full name of user
        try{
            userName = as.getByID(loggedInUserId).getEmail();
            
            String fullName = as.getByID(loggedInUserId).getFirstName() + " " + as.getByID(loggedInUserId).getLastName();
            
            request.setAttribute("fullName", fullName);
            
        } catch (Exception ex) {
                Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //when ajax call of operation is made and the password entered by user is correct
        //show them the fields to type in their new password
        String op = request.getParameter("operation");

        if (op != null && op.equals("password")) {
                    
            String password = request.getParameter("cPassword");

            if(password!=null){
                User user = as.login(userName, password);
                
                boolean isValid = false;
                
                if(user!=null && user.getIsActive()){
                    isValid = true;
   
                }
                
                    //JSON object to be returned isValid to check if current password is valid
                    StringBuilder programJSON = new StringBuilder();
                    
                    programJSON.append('[');

                            programJSON.append('{');
                            programJSON.append("\"valid\":" + "\"" + isValid + "\",");

                    if (programJSON.length() > 2) {
                        programJSON.setLength(programJSON.length() - 1);
                    }
                    programJSON.append("}");
                    programJSON.append(']');

                    response.setContentType("text/html");
                    response.getWriter().write(programJSON.toString());
                    
                    log(programJSON.toString());
                    return;
            }

        }

        //forward to user account page
        getServletContext().getRequestDispatcher("/WEB-INF/userAccount.jsp").forward(request, response);
        
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
            throws ServletException, IOException {
        
        //get the current logged in user
        AccountServices as = new AccountServices();
        
        HttpSession httpSession = request.getSession();
        int loggedInUserId = -1;
        String userName = "";
        
        if (httpSession.getAttribute("email") != null) {
            loggedInUserId = (int) httpSession.getAttribute("email");
            System.out.println(loggedInUserId);

        }

        //loggedInUserId = 4;
        
        User user = null;
        
        try {
            user = as.getByID(loggedInUserId);
        } catch (Exception ex) {
            Logger.getLogger(ChangePassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //String oldPassword = request.getParameter("cPassword");
        
        //get the new password entered by the user and update it
        String password = request.getParameter("confirmPassUser");
        
        if(password != null) as.changePasswordAccountPage(user, password);
        
        response.sendRedirect("tasks");
        
    }

}
