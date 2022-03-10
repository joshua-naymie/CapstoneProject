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
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.HotlineData;
import models.PackageType;
import models.Task;
import services.TaskService;

/**
 *
 * @author srvad
 */
public class SubmitTaskFormServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        TaskService ts = new TaskService();
        
        Long submitTaskId = 4L;
        
        Task editTask = null;

        try {
            editTask = ts.get(submitTaskId);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    Short foodDeliveryId = 1;
        
    String description = editTask.getTaskDescription();
    
    if(editTask.getProgramId().getProgramId() == foodDeliveryId){ 
        request.setAttribute("foodDelivery", true);
        
    }

    request.setAttribute("description", description);

    
        
    getServletContext().getRequestDispatcher("/WEB-INF/submitTaskForm.jsp").forward(request, response);
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                
        TaskService ts = new TaskService();
        
        Long submitTaskId = 4L;
        
        Task editTask = null;

        try {
            editTask = ts.get(submitTaskId);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String programName = editTask.getProgramId().getProgramName();
        
        Short programId = editTask.getProgramId().getProgramId();
        
        Short foodDeliveryId = 1;
        
        BigDecimal totalHours = BigDecimal.valueOf(Double.valueOf(request.getParameter("hours_worked")));
        String notes = request.getParameter("notes");
        
        editTask.setNotes(notes);

        if(programId == foodDeliveryId){
            Short mileage = Short.valueOf(request.getParameter("mileage"));
            Short fooodAmount = Short.valueOf(request.getParameter("food_amount"));
            Short familyCount = Short.valueOf(request.getParameter("family_count"));
            Short packageId = Short.valueOf(request.getParameter("package_id"));
            
            FoodDeliveryData fd = new FoodDeliveryData(submitTaskId);
            fd.setMileage(mileage);
            fd.setFoodHoursWorked(totalHours);
            fd.setFoodAmount(fooodAmount);
            fd.setFamilyCount(familyCount);
            
            //organization
            
            PackageType pt = new PackageType(packageId);
            fd.setPackageId(pt);
            
        } else {
            HotlineData hd = new HotlineData(submitTaskId);
            hd.setHotlineHoursWorked(totalHours);  
        }
        
                    
        try {
            ts.update(editTask);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
