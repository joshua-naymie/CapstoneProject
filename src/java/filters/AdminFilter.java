/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.*;

import dataaccess.UserDB;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

/**
 *
 * @author agamb
 */
public class AdminFilter implements Filter {
    
     @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpSession session = httpRequest.getSession();
        
        String email = (String)session.getAttribute("email");
        
        if (email != null) {
            UserDB userDB = new UserDB();
            User user;
            try {
                user = userDB.get(email);
                if (!user.getIsAdmin()) {
                    HttpServletResponse httpResponse = (HttpServletResponse)response;
                    httpResponse.sendRedirect("login");
                    return;
            }
            } catch (Exception ex) {
                Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        chain.doFilter(request, response);
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
    
}
