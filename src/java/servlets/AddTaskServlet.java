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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CompanyName;
import models.FoodDeliveryData;
import models.Program;
import models.Store;
import models.Task;
import models.Team;
import models.User;
import services.AccountServices;
import services.CompanyService;
import services.ProgramServices;
import services.StoreServices;
import services.TaskService;
import services.TeamServices;

/**
 * This servlet is the backend for the Add Task Page.
 * @author srvad
 */
public class AddTaskServlet extends HttpServlet {

    /**
     * Get all information required for the Add Task page
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
        
        // get all programs from the program table
        ProgramServices ps = new ProgramServices();

        List<Program> allPrograms = null;

            try {
                allPrograms = ps.getAll();

                //only select the first two programs for add task functionality:
                //food delivery and hotline
                allPrograms.subList(2, allPrograms.size()).clear();
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        //set the 2 programs for the program dropdown
        request.setAttribute("allPrograms", allPrograms);


        //get all companies listed in the company_name table and pass it to company
        //dropdown through an attribute
        CompanyService cs = new CompanyService();

            List<CompanyName> allCompanies = null;

            try {
                allCompanies = cs.getAll();
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        request.setAttribute("allCompanies", allCompanies);


        //forward to the addTask jsp
        getServletContext().getRequestDispatcher("/WEB-INF/addTask.jsp").forward(request, response);
        
    }
    

    /**
     * Get information to create a task based on the form -inputs of the user
     * in the Add Task page. Either hotline or food delivery task is created.
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
        
        //create all service object to access the database
        TaskService ts = new TaskService();
        ProgramServices ps = new ProgramServices();
        TeamServices tes = new TeamServices();
        AccountServices as = new AccountServices();
        
        //get the action field for the form
        String action = (String) request.getParameter("action");
        
        int supervisorId = -1;

        // if the user clicks Add button do the following:
        if(action != null && action.equals("Add")){
            
            Task addTask = new Task();
            Short pogramAddId = -1;
            
            try{
                
                //get values of the task date, start and end time
                String taskDate = request.getParameter("taskDate");
                String taskStart = request.getParameter("taskStart");
                String taskEnd = request.getParameter("taskEnd");
                
                //combine the date and time fields into a single date object
                //for start and end times
                Date sTime = null;
                Date eTime = null;

                if(taskStart !=null){

                    LocalDateTime st = LocalDateTime.now();
                    LocalDate datePart = LocalDate.parse(taskDate);
                    LocalTime timePart = LocalTime.parse(taskStart);
                    st = LocalDateTime.of(datePart, timePart);
                    
                    ZonedDateTime zdt = st.atZone(ZoneId.systemDefault());
                    sTime = Date.from(zdt.toInstant());
                }
                
                if(!taskEnd.equals("")){
                    LocalDateTime et = LocalDateTime.now();
                    LocalDate datePart1 = LocalDate.parse(taskDate);
                    LocalTime timePart1 = LocalTime.parse(taskEnd);
                    et = LocalDateTime.of(datePart1, timePart1);

                    ZonedDateTime zdt1 = et.atZone(ZoneId.systemDefault());
                    eTime = Date.from(zdt1.toInstant());
                }
                    
                //get program id from user selection in dropdown
                String programAdd = (String) request.getParameter("programAdd");

                String[] parts = programAdd.split(";");

                String programAddName = parts[0];
                pogramAddId = Short.valueOf(parts[1]);

                
                //get description, city, spots and supervisor fields from the user
                String description = (String) request.getParameter("description");
                String cityAdd = (String) request.getParameter("cityAdd");
                
            Short spotsAdd = Short.parseShort((String) request.getParameter("spotsAdd"));
            supervisorId = Integer.parseInt((String) request.getParameter("supervisorAdd"));
            
            
            //create a task based on user inputs
            addTask = new Task(0L, -1L, sTime, true, false, supervisorId, (short) 0, cityAdd);
                        
            addTask.setTaskDescription(description);

            addTask.setMaxUsers(spotsAdd);
            
            // if user enters end time, get the value and add it to task
            if(eTime != null) addTask.setEndTime(eTime);

                
            } catch (Exception ex) {
                
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                
            }
                
            //set team information
                try {
                    addTask.setTeamId(tes.get(1));
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //set the program for the new task
                try {
                    addTask.setProgramId(ps.get(pogramAddId));
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            // harcoded values: food delivery is always first in the program table
            
            //hotline is always second in the program table
            
            //everyone in hotline is grouped into one team and its the first team
            //in the team table
            Short foodDeliveryId = 1;
            Short hotlineId = 2;
            int hotlineTeamId = 1;

            //if the user select program is food delivery do the following:
            if(pogramAddId == foodDeliveryId){
                
                //get store id based on user selection
                Integer storeId = Integer.parseInt ((String) request.getParameter("storeAdd"));
                
                //get all teams in the team tables
                List<Team> teams = null;
                        
                try {
                     teams = tes.getAll();
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                boolean teamCheck = false;

                Team teamAdd = null;

                //loop all teams
                for(Team t: teams){
                    int teamStoreId = -1;
                    
                    if(t.getStoreId() != null)  teamStoreId = t.getStoreId().getStoreId();
                    
                    //find a matching team based on the store the user selects
                    if( storeId == teamStoreId ) {
                        teamCheck = true;
                        teamAdd = t;
                    }
                    if(teamCheck) break;
                }
                
                if(!teamCheck){
                    //teamAdd = new Team()
                }
            
                //set the team based on the user selection
                addTask.setTeamId(teamAdd);

                
                //if user selects hotline do the following:
            }else if(pogramAddId == hotlineId){
                
                //for a task set the team to be hotline (1st team in team table)
                Team hTeam;
                try {
                    hTeam = tes.get(hotlineTeamId);
                    addTask.setTeamId(hTeam);
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //hotline will always have 2 users per task and city is not needed
                addTask.setMaxUsers((short) 2);
                addTask.setTaskCity(null);
            } 
            
            try {
                
                //when a task is first created these 3 booleans should be false
                addTask.setIsApproved(false);
                addTask.setIsSubmitted(false);
                addTask.setIsDissaproved(false);
                
                Long groupId = ts.insert(addTask);
                Task task = ts.get(groupId);
                
                //newly created task needs a group id equal to that task id
                task.setGroupId(groupId);
                ts.update(task);
                
                //remaining tasks to create based on spots user selected
                //since 1 is already created
                int extraTasks = task.getMaxUsers() - 1;
                
                if(extraTasks != 0){
                    
                    //set group id for the new tasks left to be created
                    addTask.setGroupId(groupId);
                    
                    if(pogramAddId == hotlineId){            
                        
                        //if hotline is selected one task for manager with coordinator 
                        //being the approver is automatically created
                        int coordinatorId = Integer.parseInt((String) request.getParameter("coordinatorAdd"));
                        addTask.setApprovingManager(coordinatorId);
                        User user = as.getByID(supervisorId);
                        addTask.setUserId(user);
                        addTask.setAssigned(Boolean.TRUE);
                        
                        List <Task> tasks = null;

                        try {
                           tasks = ts.getAllTasksByGroupId(task.getGroupId());

                        } catch (Exception ex) {
                            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //for hotline since one task is auto-assigned, spot taken 
                        //for all tasks in that group needs to be auto-incremented
                        for(Task singleTask : tasks){
                            singleTask.setSpotsTaken( (short) (task.getSpotsTaken() + ( (short) 1 )));
                            ts.update(singleTask);
                        }
                    }
                    
                    //insert as many tasks as required based on spots - 1
                    for(int i = 0; i <extraTasks; i++){
                        ts.insert(addTask);
                    }
                }

                request.setAttribute("userMessage", "Task posted.");
                doGet(request, response);
                return;
                
            //catch any errors if task can't be posted and show user a message
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("userMessage", "Task could not posted. Please try again.");
                doGet(request, response);
                return;
            }
            
            // if user clicks cancel do not make any changes
        } else if(action != null && action.equals("Cancel")){
            request.setAttribute("userMessage", "Task was not posted.");
            doGet(request, response);
            return;
        }
        response.sendRedirect("addTask");
    }

}
