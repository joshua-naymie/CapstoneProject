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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Task;
import services.TaskService;

/**
 *
 * @author srvad
 */
public class AddTaskServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
//        TaskService ts = new TaskService();
//        
//                List<Task> allTasks = null;
//        try {
//            allTasks = ts.getAll();  //get a list of all users
//        } catch (Exception ex) {
//            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        request.setAttribute("tasks", allTasks);
        
        getServletContext().getRequestDispatcher("/WEB-INF/addTask.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/addTask.jsp").forward(request, response);
    }

}
