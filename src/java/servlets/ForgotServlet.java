/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.AccountServices;

/**
 * Backend for Forgot Password feature
 * @author srvad
 */
public class ForgotServlet extends HttpServlet {

    /**
     * Forward the user to the forgot page where they enter their email or
     * forward them to the restNewPassword page based on the existence of UUID.
     * 
     * @param request Request object created by the web container 
     * for each request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("uuid") == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
        } else {
            String uuidH = (String) request.getParameter("uuid");
            request.setAttribute("uuid", uuidH);
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }

    }

    /**
     * 
     * 
     * 
     * @param request Request object created by the web container 
     * for each request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //initialization for session, uuid, account service and user
        AccountServices as = new AccountServices();
        String path = getServletContext().getRealPath("/WEB-INF");
        User user = new User();
        String uuidC = "";
        HttpSession session = request.getSession();

        //in resetNewPassword page get the password typed by the user
        //and update the password to the database
        if (request.getParameter("uuid") != "") {
            uuidC = request.getParameter("uuid");
            log(uuidC);
            String password = request.getParameter("confirmPassUser");
            if(password != null) as.changePassword(uuidC, password);
            log(password);
            response.sendRedirect("login");
            return;
        }

        String url = request.getRequestURL().toString();

        //generate a random uuid and add it to the url as a parameter
        String uuid = UUID.randomUUID().toString();
        session.setAttribute("uuid", uuid);

        String link = url + "?uuid=" + uuid;

        
        //get the user based on the email provided
        String username = request.getParameter("fEmail");

        try {
            user = as.get(username);
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);

        }

        //if user doesnt exist for the given email, clear uuid and tell user to retry
        if (user == null) {
            request.setAttribute("emailCheck", true);
            session.setAttribute("uuid", null);
            request.setAttribute("userMessage", "Please enter a valid email"); // CODE ADDED BY TARA FOR INPUT VALIDATION - PLEASE REVIEW
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            return;
        }

        //if user is found for that email and is a in active user, tell user to retry
        if (!user.getIsActive()) {
            session.setAttribute("uuid", null);
            request.setAttribute("userMessage", "Please enter a valid email");
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            return;
        }

        //request.setAttribute("emailCheck", true);
        //request.setAttribute("userMessage", "Sending...");
        //getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        //response.sendRedirect("forgot?userMessage=Sending...");
        
        // set the randomly generated uuid as the uuid in the database for that user
        user.setResetPasswordUuid(uuid);

        //update the user
        try {
            as.updateNoCheck(user);
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //get email entered by user
        String fEmail = "";
        fEmail = request.getParameter("fEmail");

        try {
            
            //forward to account service where email will be sent with link to reset
            if (as.resetPassword(fEmail, path, link)) {
                request.setAttribute("emailConfirm", true);
                
                //return to login page if email is sent
                response.sendRedirect("login");
                return;
            } else {
                
                //return to forgot page if email can't be sent
                request.setAttribute("emailCheck", true);
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
