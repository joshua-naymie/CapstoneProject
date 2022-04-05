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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //uncomment later
//        HttpSession httpSession = request.getSession();
//        String user_id = httpSession.getAttribute("email") + "";
//        System.out.println(user_id);
//
//        int loggedInUserId = Integer.parseInt(user_id);
        
        //delete later
//        int loggedInUserId = 4;

        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("email") != null) {
            int user_id = (int) httpSession.getAttribute("email");
            System.out.println(user_id);
            User loggedInUser = new User(user_id);
            if (user_id != null && user_id.matches("[0-9]+")) {
//            loggedInUser = new User(Integer.parseInt(user_id));
                TaskService taskService = new TaskService();
                List<Integer> supervisors = taskService.getSupervisors();
                if (loggedInUser.getIsAdmin() || supervisors.contains(loggedInUser.getUserId())) {
                    httpSession.setAttribute("show_edit");
                }
            }
        }

        AccountServices as = new AccountServices();
        
//        User loggedInUser = new User();
//        try {
//            loggedInUser = as.getByID(loggedInUserId);
//        } catch (Exception ex) {
//            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
       
        boolean show_edit = false;
        if(loggedInUser.getIsAdmin()) show_edit = true;
        
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
		new JSONKey("group_id", true),
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
                new JSONKey("team_id", true),
                new JSONKey("show_signup", true), //pass boolean for signup or cancel show_signup_cancel
                new JSONKey("can_cancel", true), //boolaen for can cancel can_cancel
                new JSONKey("show_edit", true),//boolean to show edit button or not show_edit
		new JSONKey("spots_taken", true),
		new JSONKey("user_name", true)
    
        };

        JSONBuilder builder = new JSONBuilder(taskKeys);

        if (taskList.size() > 0) {
            int i = 0;
            for (i = 0; i < taskList.size() - 1; i++) {
                returnData.append(buildTaskJSON(taskList.get(i), builder, loggedInUserId, show_edit));
                returnData.append(',');
            }
            returnData.append(buildTaskJSON(taskList.get(i), builder, loggedInUserId, show_edit));
        }
        returnData.append("];");

        request.setAttribute("taskData", returnData);
        getServletContext().getRequestDispatcher("/WEB-INF/task.jsp").forward(request, response);
    }

    private String buildTaskJSON(Task task, JSONBuilder builder, int loggedInUserId, boolean show_edit) {
        
        Object[] taskValues = { task.getTaskId(),
                task.getProgramId().getProgramId(),
                task.getProgramId().getProgramName(),
		task.getGroupId(),
                task.getMaxUsers(),
                task.getStartTime(),
                task.getEndTime(),
                task.getAvailable(),
                task.getNotes(),
                task.getIsApproved(),
                task.getTaskDescription(),
                task.getTaskCity(),
                task.getIsSubmitted(),
                task.getApprovalNotes(),
                task.getIsDissaproved(),
                task.getTeamId().getTeamId(),
                signUpButtonShow(task, loggedInUserId),
                cancelTaskButtonShow(task, loggedInUserId),
                show_edit,
		task.getSpotsTaken(),
		getUserName(task.getUserId())};
        
        //log(signUpButtonShow(task, loggedInUserId) + "");

        return builder.buildJSON(taskValues);
    }

   /**
     * 
     * Backend for when user tries to Cancel or Sign Up for a task
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

        String action = (String) request.getParameter("action");
    
        //get the logged in user saved in the session
        HttpSession httpSession = request.getSession();
        
        TaskService ts = new TaskService();

          //uncomment later
//        int loggedInUserId = -1;
//
//        try{
//            loggedInUserId = (int) httpSession.getAttribute("email");
//            System.out.println(loggedInUserId);
//
//        }catch (Exception ex){
//            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        //delete later
        int loggedInUserId = 4;
        
        
        //get task_id for a specific task in the tasks page (a row is a task)
        Long taskId = -1L;
        
        try{
            taskId = Long.parseLong( (String) request.getParameter("task_id") );
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Task task = null;
        
        try {
            task = ts.get(taskId);

            //if user clicks singup do the following
            if(action != null && action.equals("SignUp")){
                signUp(request, response, task, loggedInUserId);
                
            //if user clicks cancel do the following
            }else if(action != null && action.equals("Cancel")){
                cancel(request, response, task, loggedInUserId);
                
            }
        
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //reload the page in case anything falis
        doGet(request,response);
        
    }
       
    /**
     * Controls whether the Cancel button should be visible for a task for the logged in user or not
     * 
     * @param task the task object
     * @param loggedInUserId id of the logged in user
     * @return true if user is allowed to see the cancel button for a task else false
     */
    private boolean cancelTaskButtonShow(Task task, int loggedInUserId){
        
        TaskService ts = new TaskService();
        AccountServices as = new AccountServices();
        
        //get all tasks with the same group id as the current task
        List <Task> tasks = null;
        
        try {
           tasks = ts.getAllTasksByGroupId(task.getGroupId());
            
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Task matchedTask = null;
        
        //loop through all tasks in a group
        for(Task oneTask : tasks){
            
            //find if a user is already singed up for a task in that group
            if(oneTask.getUserId() != null && oneTask.getUserId().getUserId() == loggedInUserId){
                matchedTask = oneTask;
                //log(matchedTask.getUserId() + matchedTask.getTaskDescription() + "--");
                break;
            }
        }
        
        //if user has singed up for a task in the current group
        if(matchedTask != null){
            task = matchedTask;
            
            try{
            Date taskStart = task.getStartTime();
            
            //find the difference between the task start time and the current time
            LocalDateTime taskTime = taskStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime currTime = LocalDateTime.now();
            
            long diffInHours = java.time.Duration.between(currTime, taskTime).toHours();
           
            //log("Diff " + diffInHours);
           
            int sevenDaystoHours = 168;

            //can only cancel if start time is at least 7 days away
            if(diffInHours < sevenDaystoHours ){
                return false;
            }
            else {
                 return true;
            }

            } catch (Exception ex){
                Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }
    
    /**
     * Controls whether the Sign Up button should be visible for a task for the logged in user or not
     * 
     * @param task the task object
     * @param loggedInUserId id of the logged in user
     * @return true if user is allowed to see the sign up button for a task else false
     */
    private boolean signUpButtonShow(Task task, int loggedInUserId){
                
        TaskService ts = new TaskService();
        AccountServices as = new AccountServices();
        
        //get all tasks for a group that the current task belongs to
        List <Task> tasks = null;
        
        try {
           tasks = ts.getAllTasksByGroupId(task.getGroupId());
           //log(tasks.size() + " ");
            
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Task matchedTask = null;
        
        for(Task oneTask : tasks){
            
            //if user has already singed up for a task in the current group break
            if(oneTask.getUserId() != null && oneTask.getUserId().getUserId() == loggedInUserId){
                matchedTask = oneTask;
                break;
            }
        }
        
        //check if spots are all filled or not for that task group
        if(task.getMaxUsers() == task.getSpotsTaken()){
            return false;
        }
        
        //if user already signed up dont show the sign up button again
        if(matchedTask != null){
            //log(matchedTask.getTaskDescription());
            return false;
        }
        
        //only show sign up button if the task hasn't started yet
        Date taskStart = task.getStartTime();
            
            LocalDateTime taskTime = taskStart.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime currTime = LocalDateTime.now();

            long diffInHours = java.time.Duration.between(currTime, taskTime).toHours();
            
            //log(diffInHours + " ");
            
            if(diffInHours <= 0 ){
                return false;
            }
        
        return true;
    }

    /**
     * Steps taken when cancelling a task
     * 
    * @param task a task object
     * @param loggedInUserId id of logged in user
     * @param request Request object created by the web container 
     * for each request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    protected void cancel(HttpServletRequest request, HttpServletResponse response, Task task, int loggedInUserId)
            throws ServletException, IOException {

        TaskService ts = new TaskService();
        AccountServices as = new AccountServices();
        
        //get all tasks in the current group
        List <Task> tasks = null;
        
        try {
           tasks = ts.getAllTasksByGroupId(task.getGroupId());
            
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(Task oneTask : tasks){
            
            //check if user is signed up for a task in the current group task
            if(oneTask.getUserId() != null && oneTask.getUserId().getUserId() == loggedInUserId){
                try {
                    
                    //once the user cancels a spot should be made free
                    //and all tasks in the current group must reflect that free spot
                    for(Task singleTask : tasks){
                        singleTask.setSpotsTaken( (short) (task.getSpotsTaken() - ( (short) 1 )));
                        ts.update(singleTask);
                    }
                    
                    //set assigned to false for the task, delete the userid for the task and update it
                    oneTask.setAssigned(Boolean.FALSE);
                    //task.setSpotsTaken( (short) (task.getSpotsTaken() - ( (short) 1 )));
                    oneTask.setUserId(null);
                    ts.update(oneTask);
                    
                } catch (Exception ex) {  
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }

    }
    
    /**
     * Steps taken when signing up for a task
     * 
     * @param task a task object
     * @param loggedInUserId id of logged in user
     * @param request Request object created by the web container 
     * for each request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    protected void signUp(HttpServletRequest request, HttpServletResponse response, Task task, int loggedInUserId)
        throws ServletException, IOException {
        
        TaskService ts = new TaskService();
        AccountServices as = new AccountServices();
        
        //get all tasks for the current group
        List <Task> tasks = null;
        
        try {
           tasks = ts.getAllTasksByGroupId(task.getGroupId());
            
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        boolean addTask = true;
        
        for(Task oneTask : tasks){
            
            //if user has already signed up for a task in the current group dont sign them up again
            if(oneTask.getUserId() != null && oneTask.getUserId().getUserId() == loggedInUserId){
                addTask = false;
                break;
            }
        }
        
        //if the logged in user isn't signed up do the following
        if(addTask){
            
             for(Task oneTask : tasks){
                 
                 //find a task with a free spot in the current group
                 if(oneTask.getUserId() == null){            
                    try {
                        
                        //add 1 to the spots taken for the current task group
                        for(Task singleTask : tasks){
                            singleTask.setSpotsTaken( (short) (task.getSpotsTaken() + ( (short) 1 )));
                            ts.update(singleTask);
                        }
                        
                        //set the logged in user for that task and update to database
                        User user = as.getByID(loggedInUserId);
                        oneTask.setUserId(user);
                        ts.update(oneTask);

                    } catch (Exception ex) {
                        Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                 }

             }

        }

        //}
         
    }
    
    protected String getUserName (User user) {
       
	if (user != null) {
		return user.getFirstName() + " " + user.getLastName();
	}
	
	return "";
	
    }
    
}
