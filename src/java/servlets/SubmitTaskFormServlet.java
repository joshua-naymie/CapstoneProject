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
 * Backend code for Submit Task Form Page
 *
 * @author srvad
 */
public class SubmitTaskFormServlet extends HttpServlet {

    /**
     * Display required information in the submitTaskForm page
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it
     * encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //get the task_id coming from the Submit button in the Submit Task page
        TaskService ts = new TaskService();

        Long submitTaskId = -1L;

        try {
            submitTaskId = Long.parseLong((String) request.getParameter("task_id"));
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        //create a session to store the task_id for the task to be submitted
        HttpSession session = request.getSession();
        session.setAttribute("submitTaskFormId", submitTaskId);

        //get the task for the specified task_id
        Task editTask = null;

        try {
            editTask = ts.get(submitTaskId);
        } catch (Exception ex) {
            Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        Short foodDeliveryId = 1;

        //get description for the current task
        String description = editTask.getTaskDescription();

        String oldSubmissionInfo = "";

        if (editTask.getApprovalNotes() != null) {
            oldSubmissionInfo += "Notes: " + editTask.getApprovalNotes() + "\n";
        }

        //if the selected task was food delivery get information for extra fields
        if (foodDeliveryId == editTask.getProgramId().getProgramId()) {

            if (editTask.getFoodDeliveryData() != null) {
                FoodDeliveryData fd = editTask.getFoodDeliveryData();
                oldSubmissionInfo += "Hours: " + fd.getFoodHoursWorked() + "\n";
                if (fd.getFamilyCount() != null) {
                    oldSubmissionInfo += "Family Count: " + fd.getFamilyCount() + "\n";
                }
                oldSubmissionInfo += "Mileage: " + fd.getMileage() + "\n";
                if (fd.getOrganizationId() != null) {
                    oldSubmissionInfo += "Organization Name: " + fd.getOrganizationId().getOrgName() + "\n";
                }
                oldSubmissionInfo += "Pakage Type: " + fd.getPackageId().getPackageName() + "\n";
                oldSubmissionInfo += "Package Count: " + fd.getFoodAmount() + "\n";
            }

            request.setAttribute("foodDelivery", true);

            //get all the package types available in the package_type table
            PackageTypeService pts = new PackageTypeService();

            List<PackageType> allPackages = null;

            try {
                allPackages = pts.getAll();
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("allPackages", allPackages);

            //get all the organizations available in the organization table
            OrganizationService os = new OrganizationService();

            List<Organization> organizations = null;

            try {
                organizations = os.getAll();
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskFormServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("organizations", organizations);

        } else {
            if (editTask.getHotlineData() != null) {
                HotlineData hd = editTask.getHotlineData();
                oldSubmissionInfo += "Hours: " + hd.getHotlineHoursWorked() + "\n";
            }
        }
        
        System.out.println("old submission info: " + oldSubmissionInfo);
        request.setAttribute("submissionInfo", oldSubmissionInfo);

        request.setAttribute("description", description);

        // forward to submit Task form page
        getServletContext().getRequestDispatcher("/WEB-INF/submitTaskForm.jsp").forward(request, response);

    }

    /**
     * Get information from when the user submits a task or cancels their
     * submission
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it
     * encounters difficulty
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = (String) request.getParameter("action");

        //if user clicks the submit task button, do the following:
        if (action != null && action.equals("Submit Task")) {

            try {

                TaskService ts = new TaskService();

                HttpSession httpSession = request.getSession();

                //get the current task_id from the session
                Long submitTaskId = -1L;

                try {
                    submitTaskId = (Long) httpSession.getAttribute("submitTaskFormId");

                } catch (Exception ex) {
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //end the session after getting the task_id
                // httpSession.invalidate();
                //get the task from the task_id
                Task editTask = null;

                try {
                    editTask = ts.get(submitTaskId);
                    //log(editTask.getTaskDescription() + editTask.getIsSubmitted());
                } catch (Exception ex) {
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //get the necessary information from the fields typed by the user
                String programName = editTask.getProgramId().getProgramName();

                Short programId = editTask.getProgramId().getProgramId();

                Short foodDeliveryId = 1;

                String total = request.getParameter("totalHours");

                BigDecimal totalHours = new BigDecimal(total);

                String notes = request.getParameter("notes");

                editTask.setNotes(notes);

                FoodHotlineDataService fds = new FoodHotlineDataService();

                //if program is food delivery get extra fields from user input
                if (programId == foodDeliveryId) {
                    Short mileage = Short.valueOf(request.getParameter("mileage"));
                    Short fooodAmount = Short.valueOf(request.getParameter("food_amount"));

                    Short packageId = Short.valueOf(request.getParameter("package_id"));

                    String deliveryType = request.getParameter("deliveryType");

                    String family = request.getParameter("family_count");

                    String org = request.getParameter("organization_id");

                    Short familyCount = -1;
                    Integer organizationId = -1;

                    //create food delivery data for a task and assign values
                    FoodDeliveryData fd = new FoodDeliveryData(submitTaskId);
                    fd.setTaskFdId(submitTaskId);
                    fd.setMileage(mileage);
                    fd.setFoodHoursWorked(totalHours);
                    fd.setFoodAmount(fooodAmount);

                    //if family is selected in "delivered to" field do the following
                    if (family != "") {
                        familyCount = Short.valueOf(family);
                        fd.setFamilyCount(familyCount);

                    }

                    OrganizationService os = new OrganizationService();
                    //id organization is selected do the following
                    if (org != "") {
                        organizationId = Integer.valueOf(org);
                        Organization ot = os.get(organizationId);
                        fd.setOrganizationId(ot);
                    }

                    //set package and store id and insert the food delivery data
                    PackageType pt = new PackageType(packageId);
                    fd.setPackageId(pt);

                    fd.setStoreId(editTask.getTeamId().getStoreId());

                    if (editTask.getFoodDeliveryData() != null) {
                        fds.insertFoodDeliveryData(fd);
                    } else {
                        fds.updateFoodDeliveryData(fd);
                    }

                } else {

                    //else create a hotline data and insert it into the database
                    HotlineData hd = new HotlineData(submitTaskId);
                    hd.setTaskHotlineId(submitTaskId);
                    hd.setHotlineHoursWorked(totalHours);

                    if (editTask.getHotlineData() != null) {
                        fds.insertHotlineData(hd);
                    } else {
                        fds.updateHotlineData(hd);
                    }

                }

                try {

                    //once the user submits, this field becomes true
                    editTask.setIsSubmitted(Boolean.TRUE);
                    editTask.setAvailable(false);
                    ts.update(editTask);

                } catch (Exception ex) {
                    Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                //if there was an error submitting task, show the message to the user
            } catch (Exception ex) {
                Logger.getLogger(SubmitTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("userMessage", "Task could not be submitted. Please try again.");
                doGet(request, response);
                return;

            }

        } //if the user cancels redirect to the submit task page
        else if (action != null && action.equals("Cancel")) {
            response.sendRedirect("submitTask");
            return;
        }

        response.sendRedirect("submitTask");

    }

}
