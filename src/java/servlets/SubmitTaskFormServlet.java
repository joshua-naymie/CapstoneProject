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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.HotlineData;
import models.Organization;
import models.PackageType;
import models.Task;
import services.FoodHotlineDataService;
import services.OrganizationService;
import services.TaskService;
import services.PackageTypeService;

/**
 *
 * @author srvad
 */
public class SubmitTaskFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
//        String op = request.getParameter("operation");
//        if(op.equals("idInfo")){
//                   
//         String test = request.getParameter("test");
//         log(test);
//        }

         
        
        //String task_id = request.getParameter("task_id");
	//Long taskId = Long.parseLong(task_id);

        //log((String) request.getParameter("task_id"));
        TaskService ts = new TaskService();
        
        Long submitTaskId = -1L;
        
        try{
            submitTaskId = Long.parseLong( (String) request.getParameter("task_id") );
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        log("" + submitTaskId);
        
        submitTaskId = 1L;

        Task editTask = null;

        try {
            editTask = ts.get(submitTaskId);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Short foodDeliveryId = 1;

        String description = editTask.getTaskDescription();

        if (foodDeliveryId == editTask.getProgramId().getProgramId()) {

            request.setAttribute("foodDelivery", true);

            PackageTypeService pts = new PackageTypeService();

            List<PackageType> allPackages = null;

            try {
                allPackages = pts.getAll();
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("allPackages", allPackages);

            OrganizationService os = new OrganizationService();

            List<Organization> organizations = null;

            try {
                organizations = os.getAll();
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("organizations", organizations);

        } else {
            // log("here");
        }

        request.setAttribute("description", description);

        getServletContext().getRequestDispatcher("/WEB-INF/submitTaskForm.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = (String) request.getParameter("action");
        
        if(action != null && action.equals("Add")){
        
        try{
            
            TaskService ts = new TaskService();

            Long submitTaskId = Long.parseLong( (String) request.getParameter("task_id") );

            //Long submitTaskId = 6L;

            Task editTask = null;

            try {
                editTask = ts.get(submitTaskId);
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String programName = editTask.getProgramId().getProgramName();

            Short programId = editTask.getProgramId().getProgramId();

            Short foodDeliveryId = 1;

            String taskStart = request.getParameter("taskStart");

            Date taskStartTime = null;
            Date taskEndTime = null;

            try {
                taskStartTime = new SimpleDateFormat("hh:mm").parse(taskStart);
                log(taskStartTime.toString());

            } catch (ParseException ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String taskEnd = request.getParameter("taskEnd");

            try {
                taskEndTime = new SimpleDateFormat("hh:mm").parse(taskEnd);
                log(taskEndTime.toString());

            } catch (ParseException ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            BigDecimal totalHours = new BigDecimal(0);

            totalHours = new BigDecimal(((taskEndTime.getTime() - taskStartTime.getTime()) / (1000.0 * 60 * 60)));

            String notes = request.getParameter("notes");

            editTask.setNotes(notes);

            FoodHotlineDataService fds = new FoodHotlineDataService();

            if (programId == foodDeliveryId) {
                Short mileage = Short.valueOf(request.getParameter("mileage"));
                Short fooodAmount = Short.valueOf(request.getParameter("food_amount"));
                Short familyCount = Short.valueOf(request.getParameter("family_count"));
                Short packageId = Short.valueOf(request.getParameter("package_id"));
                Integer organizationId = Integer.valueOf(request.getParameter("organization_id"));

                FoodDeliveryData fd = new FoodDeliveryData(submitTaskId);
                fd.setMileage(mileage);
                fd.setFoodHoursWorked(totalHours);
                fd.setFoodAmount(fooodAmount);
                fd.setFamilyCount(familyCount);

                Organization ot = new Organization(organizationId);
                fd.setOrganizationId(ot);

                PackageType pt = new PackageType(packageId);
                fd.setPackageId(pt);

                fd.setStoreId(editTask.getTeamId().getStoreId());

                fds.insertFoodDeliveryData(fd);

            } else {
                HotlineData hd = new HotlineData(submitTaskId);
                hd.setHotlineHoursWorked(totalHours);
                fds.insertHotlineData(hd);
            }

            try {
                editTask.setIsSubmitted(Boolean.TRUE);
                ts.update(editTask);

            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        } catch(Exception ex){
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("userMessage", "Task could not be submitted. Please try again.");
            doGet(request, response);
            return;
//            response.sendRedirect("sbmitTaskForm");
//            return;
        }
        
        }else if(action != null && action.equals("Cancel")){
            response.sendRedirect("submitTask");
            return;
        }
        
       getServletContext().getRequestDispatcher("/WEB-INF/submitTask.jsp").forward(request, response);

    }

}
