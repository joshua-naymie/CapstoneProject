package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import models.*;
import services.*;

public class TaskApproveDissaproveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getting all tasks that need approving
        TaskService ts = new TaskService();
        List<Task> needApproval = null;
        try {
            needApproval = ts.getAllNotApprovedTasks();  //get a list of all tasks that need approval
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        // forward to jsp
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
