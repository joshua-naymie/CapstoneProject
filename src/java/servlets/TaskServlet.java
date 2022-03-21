package servlets;

import models.util.JSONKey;
import models.util.JSONBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.*;

import models.*;
import services.*;

public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession httpSession = request.getSession();
        int loggedInUserId = (int) httpSession.getAttribute("email");

//        System.out.println(loggedInUserId);
//        httpSession.setAttribute("loggedInUserId", loggedInUserId);

        TaskService taskService = new TaskService();
        List<Task> taskList = null;
        try {
            taskList = taskService.getAll();
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("tasks", taskList);

        StringBuilder returnData = new StringBuilder();
        returnData.append("var taskDataSet = [");

        JSONKey[] taskKeys = { new JSONKey("task_id", true),
                new JSONKey("program_id", true),
                new JSONKey("program_name", true),
                new JSONKey("max_users", true),
                new JSONKey("start_time", true),
                new JSONKey("end_time", true),
                new JSONKey("available", false),
                new JSONKey("notes", true),
                new JSONKey("is_approved", false),
                new JSONKey("task_description", true),
                new JSONKey("task_city", true),
                new JSONKey("is_submitted", false),
                new JSONKey("approval_notes", true),
                new JSONKey("is_dissaproved", false),
                new JSONKey("team_id", true) };

        JSONBuilder builder = new JSONBuilder(taskKeys);

        if (taskList.size() > 0) {
            int i = 0;
            for (i = 0; i < taskList.size() - 1; i++) {
                returnData.append(buildTaskJSON(taskList.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildTaskJSON(taskList.get(i), builder));
        }
        returnData.append("];");

        request.setAttribute("taskData", returnData);
        getServletContext().getRequestDispatcher("/WEB-INF/task.jsp").forward(request, response);
    }

    private String buildTaskJSON(Task task, JSONBuilder builder) {
        Object[] taskValues = { task.getTaskId(),
                task.getProgramId().getProgramId(),
                task.getProgramId().getProgramName(),
                task.getMaxUsers(),
                task.getStartTime(),
                task.getEndTime(),
                task.getAvailable(),
                task.getNotes(),
                task.isApproved(),
                task.getTaskDescription(),
                task.getTaskCity(),
                task.isSubmitted(),
                task.getApprovalNotes(),
                task.isDissaproved(),
                task.getTeamId().getTeamId() };

        return builder.buildJSON(taskValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
        
    private boolean cancelTaskButtonShow(Task task, int loggedInUserId){
        
        try{
            Date taskStart = task.getStartTime();
            
            LocalDateTime taskTime = taskStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime currTime = LocalDateTime.now();

//            int diffInNano = java.time.Duration.between(dateTime, dateTime2).getNano();
//            long diffInSeconds = java.time.Duration.between(dateTime, dateTime2).getSeconds();
//            long diffInMilli = java.time.Duration.between(dateTime, dateTime2).toMillis();
//            long diffInMinutes = java.time.Duration.between(dateTime, dateTime2).toMinutes();
            long diffInHours = java.time.Duration.between(taskTime, currTime).toHours();
           
           //System.out.println("Diff " + diffInHours);
           
           int sevenDaystoHours = 168;
           
           if(diffInHours < sevenDaystoHours ){
               return false;
           }
           else {
                return true;
           }
            
        } catch (Exception ex){
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    protected void cancel(HttpServletRequest request, HttpServletResponse response, Task task, int loggedInUserId)
            throws ServletException, IOException {
        
        //TaskService ts = new TaskService();
        //Long task_id = Long.parseLong((String) request.getParameter("task_id"));
        //Task task = ts.get(task_id);
        
        List<UserTask> userTasks = task.getUserTaskList();
               
        for( UserTask userTask: userTasks){
            if(userTask.getUser().getUserId() == loggedInUserId){
             
                UserTaskService us = new UserTaskService();
                userTask.setIsAssigned(Boolean.FALSE);
                userTask.setIsChosen(Boolean.FALSE);
                
                try {
                    us.remove(userTask);
                } catch (Exception ex) {
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    protected void signUp(HttpServletRequest request, HttpServletResponse response, Task task, int loggedInUserId)
        throws ServletException, IOException {
        
        //TaskService ts = new TaskService();
        //Long task_id = Long.parseLong((String) request.getParameter("task_id"));
        //Task task = ts.get(task_id);
        
        UserTaskService uts = new UserTaskService();
        
        UserTask ut = new UserTask(loggedInUserId, task.getTaskId());
        
        String action = request.getParameter("action");
        
        if(action.equalsIgnoreCase(action)){
            try {
                ut.setIsChosen(Boolean.TRUE);
                uts.update(ut);
                
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         
    }
}