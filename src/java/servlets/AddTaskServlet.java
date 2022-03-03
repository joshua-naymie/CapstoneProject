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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.CompanyName;
import models.FoodDeliveryData;
import models.Program;
import models.Store;
import models.Task;
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
    
        AccountServices as = new AccountServices();
        List<User> allSupervisors = null;
    
        try {
            allSupervisors = as.getAllActiveSupervisors();
            
        } catch (Exception ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       //for( User u: allSupervisors) log(u.toString());
       
    request.setAttribute("allSupervisors", allSupervisors);
    
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
            String programAdd = (String) request.getParameter("programAdd");
            
            String[] parts = programAdd.split(";");
            
            String programAddName = parts[0];
            Short pogramAddId = Short.valueOf(parts[1]);
            
            String description = (String) request.getParameter("description");
            String cityAdd = (String) request.getParameter("cityAdd");
            
//            Date taskDateStart = new Date();
//            try {
//                taskDateStart = new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-30");
//            } catch (ParseException ex) {
//                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            String regDate = request.getParameter("taskDate");
            
            Date taskDateStart = new Date();
            Date taskDateEnd = new Date();
            try {
            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);

            
//            Date taskDateEnd = new Date();
//            try {
//                taskDateEnd = new SimpleDateFormat("yyyy-MM-dd").parse("2022-08-31");
//            } catch (ParseException ex) {
//                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }

            String taskStart = request.getParameter("taskStart");
            
            Date taskStartTime = new SimpleDateFormat("hh:mm:ss").parse(taskStart);
                
            taskDateStart = new Date(registrationDate.getTime() + taskStartTime.getTime());
            
            String taskEnd = request.getParameter("taskEnd");
            
            Date taskEndTime = new SimpleDateFormat("hh:mm:ss").parse(taskStart);
                
            taskDateEnd = new Date(registrationDate.getTime() + taskEndTime.getTime());

            } catch (ParseException ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            log(taskDateStart.toString());
            
            
            Short spotsAdd = Short.parseShort((String) request.getParameter("spotsAdd"));
            Long supervisorId = Long.parseLong((String) request.getParameter("supervisorAdd"));
            
            //log(programAddName);
            //log(programAdd);
            
            Task addTask = new Task(0L, taskDateStart, taskDateEnd, true, false, "Jane Doe", cityAdd);
                addTask.setTaskDescription(description);
                
                addTask.setMaxUsers(spotsAdd);
            try {
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

            if(programAddName.equals("Food Delivery")){
//                Integer storeId = Integer.parseInt ((String) request.getParameter("storeAdd"));
//                FoodDeliveryData fd = new FoodDeliveryData();
//                fd.setStoreId(new Store(storeId));
//                addTask.setFoodDeliveryData(fd);
            }else{
                log("missed");
            }
            
                            
            try {
                ts.insert(addTask);
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        response.sendRedirect("addTask");
        //getServletContext().getRequestDispatcher("/WEB-INF/addTaskTest.jsp").forward(request, response);
    }

}
