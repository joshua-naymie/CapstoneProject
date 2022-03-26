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

        TaskService ts = new TaskService();
        
        Long submitTaskId = -1L;
        
        try{
            submitTaskId = Long.parseLong( (String) request.getParameter("task_id") );
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("submitTaskFormId", submitTaskId);

        log("" + submitTaskId);
        
        //submitTaskId = 1L;

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
        
        if(action != null && action.equals("Submit Task")){
        
        try{
            
            TaskService ts = new TaskService();
            
                HttpSession httpSession = request.getSession();
    
                Long submitTaskId = -1L;

                try{
                    submitTaskId =  (Long) httpSession.getAttribute("submitTaskFormId") ;

                }catch (Exception ex){
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                httpSession.invalidate();

            //Long submitTaskId = Long.parseLong( (String) request.getParameter("task_id") );

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

//            String taskStart = request.getParameter("taskStart");
//
//            Date taskStartTime = null;
//            Date taskEndTime = null;
//
//            try {
//                taskStartTime = new SimpleDateFormat("hh:mm").parse(taskStart);
//                log(taskStartTime.toString());
//
//            } catch (ParseException ex) {
//                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            String taskEnd = request.getParameter("taskEnd");
//
//            try {
//                taskEndTime = new SimpleDateFormat("hh:mm").parse(taskEnd);
//                log(taskEndTime.toString());
//
//            } catch (ParseException ex) {
//                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            BigDecimal totalHours = new BigDecimal(0);
//
//            totalHours = new BigDecimal(((taskEndTime.getTime() - taskStartTime.getTime()) / (1000.0 * 60 * 60)));

            String total = request.getParameter("totalHours");
             
            BigDecimal totalHours = new BigDecimal(total);

            String notes = request.getParameter("notes");

            editTask.setNotes(notes);

            FoodHotlineDataService fds = new FoodHotlineDataService();

            if (programId == foodDeliveryId) {
                Short mileage = Short.valueOf(request.getParameter("mileage"));
                Short fooodAmount = Short.valueOf(request.getParameter("food_amount"));

                Short packageId = Short.valueOf(request.getParameter("package_id"));

                String deliveryType = request.getParameter("deliveryType");
                
                
                String family = request.getParameter("family_count");
                
                String org = request.getParameter("organization_id");
                
                Short familyCount = -1;
                Integer organizationId = -1;

                FoodDeliveryData fd = new FoodDeliveryData(submitTaskId);
                fd.setTaskFdId(submitTaskId);
                fd.setMileage(mileage);
                fd.setFoodHoursWorked(totalHours);
                fd.setFoodAmount(fooodAmount);
                
                if(family !=""){
                    familyCount = Short.valueOf(family);
                    fd.setFamilyCount(familyCount);

                }

                if(org != ""){
                    organizationId = Integer.valueOf(org);
                    Organization ot = new Organization(organizationId);
                    fd.setOrganizationId(ot);
                }

                PackageType pt = new PackageType(packageId);
                fd.setPackageId(pt);

                fd.setStoreId(editTask.getTeamId().getStoreId());

                fds.insertFoodDeliveryData(fd);

            } else {
                HotlineData hd = new HotlineData(submitTaskId);
                hd.setTaskHotlineId(submitTaskId);
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
        
        
        
        }
        else if(action != null && action.equals("Cancel")){
            response.sendRedirect("submitTask");
            return;
        }
        
        response.sendRedirect("submitTask");

    }

}
