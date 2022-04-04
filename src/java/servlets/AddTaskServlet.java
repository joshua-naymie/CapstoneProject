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

    ProgramServices ps = new ProgramServices();
    
    List<Program> allPrograms = null;
    
        try {
            allPrograms = ps.getAll();
            allPrograms.subList(2, allPrograms.size()).clear();
        } catch (Exception ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    
//     for(Program p: allPrograms) {
//         System.out.println(p.getProgramName());
//         System.out.println(p.getProgramId());
//     }
    request.setAttribute("allPrograms", allPrograms);
    
    CompanyService cs = new CompanyService();
        List<CompanyName> allCompanies = null;
    
        try {
            allCompanies = cs.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    request.setAttribute("allCompanies", allCompanies);
        
       //for( User u: allSupervisors) log(u.toString());
       
    //request.setAttribute("allSupervisors", allSupervisors);
    
    StoreServices ss = new StoreServices();
//    String companyAdd = (String) request.getParameter("companyAdd");
//    String[] parts = companyAdd.split(";");
//            
//    String companyAddName = parts[0];
//    Short companyAddId = Short.valueOf(parts[1]);
    //Short companyAddId = 1234;
    //log("here");
    String company = "";
    
    try{
        company = request.getParameter("company");
        //log(company);
        String[] parts = company.split(";");
        String companyAddName = parts[0];
        Short companyAddId = Short.valueOf(parts[1]);

//    List<Store> allStores = null;
//    
//        try {
//            allStores = cs.get(companyAddId).getStoreList();
//        } catch (Exception ex) {
//            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    request.setAttribute("allStores", allStores);  
        
    }catch (Exception ex){
        
    }

    getServletContext().getRequestDispatcher("/WEB-INF/addTaskTest.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        TaskService ts = new TaskService();
        ProgramServices ps = new ProgramServices();
        TeamServices tes = new TeamServices();
        AccountServices as = new AccountServices();
           
//        Long nextTaskId = -1L;
//        try {
//            nextTaskId = ts.getNextTaskId();
//        } catch (Exception ex) {
//            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        log(nextTaskId + "");
        
        String action = (String) request.getParameter("action");
        
        int supervisorId = -1;

        if(action != null && action.equals("Add")){
            Task addTask = new Task();
            Short pogramAddId = -1;
            
            try{
                
                String taskDate = request.getParameter("taskDate");
                String taskStart = request.getParameter("taskStart");
                String taskEnd = request.getParameter("taskEnd");
                
                Date sTime = null;
                Date eTime = null;
                
                if(taskStart !=null){

                    LocalDateTime st = LocalDateTime.now();
                    LocalDate datePart = LocalDate.parse(taskDate);
                    LocalTime timePart = LocalTime.parse(taskStart);
                    st = LocalDateTime.of(datePart, timePart);
                    
                    ZonedDateTime zdt = st.atZone(ZoneId.systemDefault());
                    sTime = Date.from(zdt.toInstant());
                    log(sTime.toString());
                }
                
                if(!taskEnd.equals("")){
                    LocalDateTime et = LocalDateTime.now();
                    LocalDate datePart1 = LocalDate.parse(taskDate);
                    LocalTime timePart1 = LocalTime.parse(taskEnd);
                    et = LocalDateTime.of(datePart1, timePart1);

                    ZonedDateTime zdt1 = et.atZone(ZoneId.systemDefault());
                    eTime = Date.from(zdt1.toInstant());
                }
                    
                String programAdd = (String) request.getParameter("programAdd");

                String[] parts = programAdd.split(";");

                String programAddName = parts[0];
                pogramAddId = Short.valueOf(parts[1]);

                String description = (String) request.getParameter("description");
                String cityAdd = (String) request.getParameter("cityAdd");
                
            Short spotsAdd = Short.parseShort((String) request.getParameter("spotsAdd"));
            supervisorId = Integer.parseInt((String) request.getParameter("supervisorAdd"));
            
            addTask = new Task(0L, -1L, sTime, true, false, supervisorId, (short) 0, cityAdd);
            
            if(eTime != null) addTask.setEndTime(eTime);
            
                addTask.setTaskDescription(description);

                addTask.setMaxUsers(spotsAdd);
//            
//                
//            } catch(Exception ex){
//                
//            }
//
//            try {
                //String fullName = as.getByID(supervisorId).getFirstName() + " " + as.getByID(supervisorId).getLastName();
                //addTask.setApprovingManager(fullName);
                
            } catch (Exception ex) {
                
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                
            }
                
                try {
                    addTask.setTeamId(tes.get(1));
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    addTask.setProgramId(ps.get(pogramAddId));
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            Short foodDeliveryId = 1;
            Short hotlineId = 2;
            int hotlineTeamId = 1;

            if(pogramAddId == foodDeliveryId){
                Integer storeId = Integer.parseInt ((String) request.getParameter("storeAdd"));
//                FoodDeliveryData fd = new FoodDeliveryData();
//                fd.setStoreId(new Store(storeId));
//                addTask.setFoodDeliveryData(fd);

                List<Team> teams = null;
                        
                try {
                     teams = tes.getAll();
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                boolean teamCheck = false;

                Team teamAdd = null;

                for(Team t: teams){
                    int teamStoreId = -1;
                    
                    if(t.getStoreId() != null)  teamStoreId = t.getStoreId().getStoreId();
                    
                    if( storeId == teamStoreId ) {
                        teamCheck = true;
                        teamAdd = t;
                    }
                    if(teamCheck) break;
                }
                
                if(!teamCheck){
                    //teamAdd = new Team()
                }
            
                addTask.setTeamId(teamAdd);

            }else if(pogramAddId == hotlineId){
                Team hTeam;
                try {
                    hTeam = tes.get(hotlineTeamId);
                    addTask.setTeamId(hTeam);
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                addTask.setMaxUsers((short) 2);
                addTask.setTaskCity(null);
            } 
            
            try {
                addTask.setIsApproved(false);
                addTask.setIsSubmitted(false);
                addTask.setIsDissaproved(false);
                
                Long groupId = ts.insert(addTask);
                Task task = ts.get(groupId);
                
                task.setGroupId(groupId);
                ts.update(task);
                
                int extraTasks = task.getMaxUsers() - 1;
                
                if(extraTasks != 0){
                    addTask.setGroupId(groupId);
                    
                    if(pogramAddId == hotlineId){            
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
                        
                        for(Task singleTask : tasks){
                            singleTask.setSpotsTaken( (short) (task.getSpotsTaken() + ( (short) 1 )));
                            ts.update(singleTask);
                        }
                    }
                    
                    for(int i = 0; i <extraTasks; i++){
                        ts.insert(addTask);
                    }
                }

                request.setAttribute("userMessage", "Task posted.");
                doGet(request, response);
                return;
                
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("userMessage", "Task could not posted. Please try again.");
                doGet(request, response);
                return;
            }
            
        } else if(action != null && action.equals("Cancel")){
            request.setAttribute("userMessage", "Task was not posted.");
            doGet(request, response);
            return;
        }
        response.sendRedirect("addTask");
        //getServletContext().getRequestDispatcher("/WEB-INF/addTaskTest.jsp").forward(request, response);
    }

}
