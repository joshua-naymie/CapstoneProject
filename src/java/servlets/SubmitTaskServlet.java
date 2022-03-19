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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.HotlineData;
import models.util.JSONBuilder;
import models.util.JSONKey;
import models.PackageType;
import models.Program;
import models.Task;
import models.UserTask;
import services.TaskService;
import services.UserTaskService;

/**
 *
 * @author srvad
 */
public class SubmitTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
    HttpSession httpSession = request.getSession();
    
    int loggedInUserId = -1;
    
    try{
        loggedInUserId = (int) httpSession.getAttribute("email");
        System.out.println(loggedInUserId);
        
    }catch (Exception ex){
        Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
      
        
    TaskService ts = new TaskService();
      
    List<Task> taskList = null;
    
        try {
            taskList = ts.getAllNotApprovedTasksByUserId(loggedInUserId);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    request.setAttribute("allTasks", taskList);
    
    StringBuilder returnData = new StringBuilder();
    returnData.append("[");
    
            JSONKey[] keys = { new JSONKey("task_id", true),
                new JSONKey("program_name", true),
                new JSONKey("start_time", true),
                new JSONKey("end_time", true),
                new JSONKey("task_description", true) };

        JSONBuilder builder = new JSONBuilder(keys);

        if (taskList.size() > 0) {
            int i = 0;
            for (i = 0; i < taskList.size() - 1; i++) {
                returnData.append(buildTaskJSON(taskList.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildTaskJSON(taskList.get(i), builder));
        }
        returnData.append("];");

        log(returnData.toString());

        request.setAttribute("taskData", returnData);
        
        cancelTask(taskList.get(0), request);
        
    getServletContext().getRequestDispatcher("/WEB-INF/submitTask.jsp").forward(request, response);
    
    }
    
    private String buildTaskJSON(Task task, JSONBuilder builder)
    {
        Program program = task.getProgramId();
        Object[] taskValues = { task.getTaskId(),
                program.getProgramName(),
                task.getStartTime(),
                task.getEndTime(),
                task.getTaskDescription() };

        return builder.buildJSON(taskValues);
    }
    
    private boolean cancelTask(Task task, HttpServletRequest request){
        
            HttpSession httpSession = request.getSession();
            
            int loggedInUserId = -1;

            try{
                loggedInUserId = (int) httpSession.getAttribute("email");
                System.out.println(loggedInUserId);

            }catch (Exception ex){
                Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try{
            Date taskStart = task.getStartTime();
            
            LocalDateTime taskTime = taskStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime currTime = LocalDateTime.now();

//            int diffInNano = java.time.Duration.between(dateTime, dateTime2).getNano();
//            long diffInSeconds = java.time.Duration.between(dateTime, dateTime2).getSeconds();
//            long diffInMilli = java.time.Duration.between(dateTime, dateTime2).toMillis();
//            long diffInMinutes = java.time.Duration.between(dateTime, dateTime2).toMinutes();
            long diffInHours = java.time.Duration.between(taskTime, currTime).toHours();
           
           System.out.println("Diff " + diffInHours);
           
           int sevenDaystoHours = 168;
           
           UserTaskService us = new UserTaskService();
           
           if(diffInHours < sevenDaystoHours ){
               return false;
           }
           else {
               List<UserTask> userTasks = task.getUserTaskList();
               
               for( UserTask userTask: userTasks){
                   if(userTask.getUser().getUserId() == loggedInUserId){
                       userTask.setIsAssigned(Boolean.FALSE);
                       userTask.setIsChosen(Boolean.FALSE);
                       
                       //need to delete a usertask and update task table
                       us.update(userTask);              
                       return true;
                   }
               }
           }
            
        } catch (Exception ex){
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
