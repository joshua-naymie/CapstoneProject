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
        
        String action = (String) request.getParameter("action");

        if(action != null && action.equals("Add")){
            Task addTask = new Task();
            Short pogramAddId = -1;
            
            try{
                
                String taskDate = request.getParameter("taskDate");
            
            //Date taskDateStart = new Date();
            //Date taskDateEnd = new Date();
            LocalDateTime st = LocalDateTime.now();
            LocalDateTime et = LocalDateTime.now();
            
            //Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
            //String registrationDate = regDate;
            //            Date taskDateEnd = new Date();
//            try {
//                taskDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-31");
//            } catch (ParseException ex) {
//                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }

                String taskStart = request.getParameter("taskStart");

                //Date taskStartTime = new SimpleDateFormat("hh:mm").parse(taskStart);
                //taskDateStart = new Date(registrationDate.getTime() + taskStartTime.getTime());

                String taskEnd = request.getParameter("taskEnd");
                //Date taskEndTime = new SimpleDateFormat("hh:mm").parse(taskEnd);
                //String taskEndTime = taskEnd;
                //log(taskEndTime.toString());

                LocalDate datePart = LocalDate.parse(taskDate);
                LocalTime timePart = LocalTime.parse(taskEnd);
                et = LocalDateTime.of(datePart, timePart);
                
                LocalDate datePart1 = LocalDate.parse(taskDate);
                LocalTime timePart1 = LocalTime.parse(taskStart);
                st = LocalDateTime.of(datePart1, timePart1);
                //log(st.toString());

//            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
//            SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
//
//            String dateAsString = dateFormatter.format(date);
//            String timeAsString = timeFormatter.format(date);

//taskDateEnd = new Date(registrationDate.getTime() + taskEndTime.getTime());
            
            //log(taskDateStart.toString());
            //log(taskDateEnd.toString());
                    
            String programAdd = (String) request.getParameter("programAdd");
            
            String[] parts = programAdd.split(";");
            
            String programAddName = parts[0];
            pogramAddId = Short.valueOf(parts[1]);
            
            String description = (String) request.getParameter("description");
            String cityAdd = (String) request.getParameter("cityAdd");
            
                                    
            
//            Date taskDateStart = new Date();
//            try {
//                taskDateStart = new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-30");
//            } catch (ParseException ex) {
//                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
            //log(programAddName);
            //log(programAdd);
            
            ZonedDateTime zdt = st.atZone(ZoneId.systemDefault());
            Date sTime = Date.from(zdt.toInstant());
            
            ZonedDateTime zdt1 = et.atZone(ZoneId.systemDefault());
            Date eTime = Date.from(zdt1.toInstant());
            
            
            //log(sTime.toString());        
            
            Short spotsAdd = Short.parseShort((String) request.getParameter("spotsAdd"));
            Long supervisorId = Long.parseLong((String) request.getParameter("supervisorAdd"));
            
            
            addTask = new Task(0L, sTime, eTime, true, false, "Jane Doe", cityAdd);
                addTask.setTaskDescription(description);

                addTask.setMaxUsers(spotsAdd);
//            
//                
//            } catch(Exception ex){
//                
//            }
//
//            try {
                String fullName = as.getByID(supervisorId).getFirstName() + " " + as.getByID(supervisorId).getLastName();
                addTask.setApprovingManager(fullName);
                
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

            if(pogramAddId == foodDeliveryId){
                Integer storeId = Integer.parseInt ((String) request.getParameter("storeAdd"));
//                FoodDeliveryData fd = new FoodDeliveryData();
//                fd.setStoreId(new Store(storeId));
//                addTask.setFoodDeliveryData(fd);

                List<Team> team = null;
                        
                try {
                     team = tes.getAll();
                } catch (Exception ex) {
                    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                boolean teamCheck = false;

                Team teamAdd = null;

                for(Team t: team){
                    if( storeId == (t.getStoreId().getStoreId()) ) {
                        teamCheck = true;
                        teamAdd = t;
                    }
                    if(teamCheck) break;
                }
                
                if(!teamCheck){
                    //teamAdd = new Team()
                }
            
                addTask.setTeamId(teamAdd);

            }else{
                log("missed");
            }
            
                            
            try {
                addTask.setIsApproved(false);
                addTask.setIsSubmitted(false);
                addTask.setIsApproved(false);
                ts.insert(addTask);
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        response.sendRedirect("addTask");
        //getServletContext().getRequestDispatcher("/WEB-INF/addTaskTest.jsp").forward(request, response);
    }

}
