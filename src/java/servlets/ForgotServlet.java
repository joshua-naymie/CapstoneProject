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
 *
 */
public class ForgotServlet extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AccountServices as = new AccountServices();
        String path = getServletContext().getRealPath("/WEB-INF");
        //UserService us = new UserService();
        User user = new User();
        String uuidC = "";
        HttpSession session = request.getSession();

        //String urlHere = request.getRequestURL().toString();
        if (request.getParameter("uuid") != "") {
            uuidC = request.getParameter("uuid");
            String password = request.getParameter("nPassword");
            as.changePassword(uuidC, password);
            response.sendRedirect("login");
            return;
        }

        String url = request.getRequestURL().toString();

        String uuid = UUID.randomUUID().toString();
        session.setAttribute("uuid", uuid);

        String link = url + "?uuid=" + uuid;

        String username = request.getParameter("fEmail");

        try {
            user = as.get(username);
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);

        }

        if (user == null) {
            request.setAttribute("emailCheck", true);
            session.setAttribute("uuid", null);
            request.setAttribute("userMessage", "Please enter a valid email"); // CODE ADDED BY TARA FOR INPUT VALIDATION - PLEASE REVIEW
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            return;
        }

//CODE ADDED BY TARA FOR INPUT VALIDATION - PLEASE REVIEW
        if (!user.getIsActive()) {
            session.setAttribute("uuid", null);
            request.setAttribute("userMessage", "Please enter a valid email");
            getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            return;
        }
// END OF CODE ADDED BY TARA FOR INPUT VALIDATION

        //request.setAttribute("emailCheck", true);
        //request.setAttribute("userMessage", "Sending...");
        //getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        //response.sendRedirect("forgot?userMessage=Sending...");
        user.setResetPasswordUuid(uuid);

        try {
            as.updateNoCheck(user);
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String fEmail = "";
        fEmail = request.getParameter("fEmail");

        try {

            if (as.resetPassword(fEmail, path, link)) {
                request.setAttribute("emailConfirm", true);
                response.sendRedirect("login");
                return;
            } else {
                request.setAttribute("emailCheck", true);
                getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            Logger.getLogger(ForgotServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
