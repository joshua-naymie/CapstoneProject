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
import models.User;
import services.AccountServices;
import services.TaskService;

/**
 * Backend for the Submit Task page
 * 
 * @author srvad
 */
public class SubmitTaskServlet extends HttpServlet {

   /**
     * Get information to show on the Submit Task Page
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
       
    // get the loggined in user from the sesstion
    HttpSession httpSession = request.getSession();
    
    AccountServices as = new AccountServices();
    
    int loggedInUserId = -1;
    
    User user = null;
    
    try{
        loggedInUserId = (int) httpSession.getAttribute("email");
        System.out.println(loggedInUserId);
        
        user = as.getByID(loggedInUserId);
        
    }catch (Exception ex){
        Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
    }
      
    //delete later
    //loggedInUserId = 4;
        
    
    //get all tasks that have not been approved for that user
    TaskService ts = new TaskService();
      
    List<Task> taskList = null;
    
        try {
            taskList = ts.getAllNotApprovedTasksByUser(user);
            
            for (Task task: taskList){
                log(task.getTaskDescription() + task.getUserId());
            }
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    request.setAttribute("allTasks", taskList);
    
    //build a JSOn object for all the tasks and send it to the front end
    StringBuilder returnData = new StringBuilder();
    returnData.append("[");
    
        //each column in the task data needs to be transformed to a key value pair  
        JSONKey[] keys = { new JSONKey("task_id", true),
                new JSONKey("program_name", true),
                new JSONKey("start_time", true),
                new JSONKey("end_time", true),
                new JSONKey("task_description", true) };

        JSONBuilder builder = new JSONBuilder(keys);

        if (taskList!=null && taskList.size() > 0) {
            int i = 0;
            for (i = 0; i < taskList.size() - 1; i++) {
                returnData.append(buildTaskJSON(taskList.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildTaskJSON(taskList.get(i), builder));
        }
        returnData.append("];");

        request.setAttribute("taskData", returnData);
        
        //forward to submit task page
        getServletContext().getRequestDispatcher("/WEB-INF/submitTask.jsp").forward(request, response);
    
    }
    
    /**
     * 
     * @param task a task object
     * @param builder builder for JSON
     * @return JSON object with task information
     */
    private String buildTaskJSON(Task task, JSONBuilder builder)
    {
        Program program = task.getProgramId();
        
        //set the values for a task to their respective keys
        Object[] taskValues = { task.getTaskId(),
                program.getProgramName(),
                task.getStartTime(),
                task.getEndTime(),
                task.getTaskDescription() };

        return builder.buildJSON(taskValues);
    }

    

}
