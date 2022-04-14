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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.util.JSONBuilder;
import models.util.JSONKey;
import models.Task;
import services.TaskService;

/**
 * approving / disapproving of submitted tasks
 * @author 861349
 */
public class TaskApproveDissaproveServlet extends HttpServlet {

    private static final DateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
    
    /**
     *
     * Backend code for sending up the data of the chosen task to approve or disapprove
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters errors
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // retrieve task information based on URL parameter
        String taskId = request.getParameter("id");
//        System.out.println("Id: " + taskId);
        Task task = null;
        try {
            // uncomment after frontend connection
            if (taskId != null) {
                TaskService ts = new TaskService();
                //task = ts.get((long) 7);
                task = ts.get(Long.parseLong(taskId));
                //System.out.println(task.getTaskId());
            }

            // sending json data
            StringBuilder taskReturnData = new StringBuilder();
            taskReturnData.append("var taskData = ");

            // creating keys for hotline
            JSONKey[] hotlineTaskKeys = {new JSONKey("taskID", false),
                new JSONKey("programID", false),
                new JSONKey("programName", true),
                new JSONKey("firstName", true),
                new JSONKey("lastName", true),
                new JSONKey("startTime", true),
                new JSONKey("endTime", true),
                new JSONKey("description", true),
                new JSONKey("city", true),
                new JSONKey("teamName", true),
                new JSONKey("hoursWorked", false)};

            // creating keys for food delivery for community
            JSONKey[] communityFoodTaskKeys = {new JSONKey("taskID", false),
                new JSONKey("programID", false),
                new JSONKey("programName", true),
                new JSONKey("firstName", true),
                new JSONKey("lastName", true),
                new JSONKey("startTime", true),
                new JSONKey("endTime", true),
                new JSONKey("description", true),
                new JSONKey("city", true),
                new JSONKey("mileage", true),
                new JSONKey("familyCount", false),
                new JSONKey("foodAmount", false),
                new JSONKey("hoursWorked", false),
                new JSONKey("packageType", true),
                new JSONKey("teamName", true)};

            // creating keys for food delivery for organization
            JSONKey[] orgFoodTaskKeys = {new JSONKey("taskID", false),
                new JSONKey("programID", false),
                new JSONKey("programName", true),
                new JSONKey("firstName", true),
                new JSONKey("lastName", true),
                new JSONKey("startTime", true),
                new JSONKey("endTime", true),
                new JSONKey("description", true),
                new JSONKey("city", true),
                new JSONKey("mileage", true),
                new JSONKey("organizationName", true),
                new JSONKey("foodAmount", false),
                new JSONKey("hoursWorked", false),
                new JSONKey("packageType", true),
                new JSONKey("teamName", true)};

            // builder for hotline
            JSONBuilder hotLineBuilder = new JSONBuilder(hotlineTaskKeys);

            // builder for food delivery for community
            JSONBuilder communityFoodBuilder = new JSONBuilder(communityFoodTaskKeys);

            // builder for food delivery for organizations
            JSONBuilder orgFoodBuilder = new JSONBuilder(orgFoodTaskKeys);

            // boolean for checking if 
            boolean isCommunity = false;

            // creating JSON objects
            if (task != null) {
                if (task.getProgramId().getProgramId() == 1 && task.getFoodDeliveryData().getFamilyCount() != null && task.getFoodDeliveryData().getFamilyCount() > 0) {
//                    System.out.println("community");
                    isCommunity = true;
                    taskReturnData.append(buildFoodJSON(task, communityFoodBuilder, isCommunity));
                } else if (task.getProgramId().getProgramId() == 1 && task.getFoodDeliveryData().getOrganizationId() != null) {
//                    System.out.println("organization");
                    taskReturnData.append(buildFoodJSON(task, orgFoodBuilder, isCommunity));
//                    System.out.println("taskReturnData" + taskReturnData.toString());
                } else if (task.getProgramId().getProgramId() == 2) {
                    taskReturnData.append(buildHotlineJSON(task, hotLineBuilder));
                } else {
                    System.out.println("wrong program id");
                }
            }
            taskReturnData.append(";");

            // setting user data attribute for the front end to use
            request.setAttribute("taskData", taskReturnData);
        } catch (Exception ex) {
            Logger.getLogger(TaskApproveDissaproveServlet.class.getName()).log(Level.WARNING, null, ex);
        }

        // forward to jsp
        getServletContext().getRequestDispatcher("/WEB-INF/loadtaskinfo.jsp").forward(request, response);

    }

    /**
     * Creates a food JSON object for community type donations
     *
     * @param task The food task to populate the JSON data
     * @param foodBuilder The JSONBuilder to create the JSON with
     * @return A food task JSON as a string
     */
    private String buildFoodJSON(Task task, JSONBuilder communityFoodBuilder, boolean isCommunity) {

        // calculating hours worked
//        long hoursWorkedInMilliSeconds = task.getStartTime().getTime() - task.getEndTime().getTime();
//        long hoursWorked = (hoursWorkedInMilliSeconds / (1000 * 60)) % 60;
        // getting package type
        String packageType = task.getFoodDeliveryData().getPackageId().getPackageName();

        // getting package weight
        short packageWeight = task.getFoodDeliveryData().getPackageId().getWeightLb();

        // set end time to be how many hours was worked
        Date taskStart = task.getStartTime();
        Date taskEnd = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(taskStart);
//        System.out.println("int value time: " + task.getFoodDeliveryData().getFoodHoursWorked().intValue());
        cal.add(Calendar.HOUR, task.getFoodDeliveryData().getFoodHoursWorked().intValue());
//        System.out.println("new time: " + cal.getTime());
        taskEnd = cal.getTime();
        
        // retrieving program values into an array
        if (isCommunity) {

            Object[] comFoodTaskValues = {task.getFoodDeliveryData().getTaskFdId(),
                task.getProgramId().getProgramId(),
                task.getProgramId().getProgramName(),
                task.getUserId().getFirstName(),
                task.getUserId().getLastName(),
                jsonDateFormat.format(task.getStartTime()),
                jsonDateFormat.format(taskEnd),
                task.getTaskDescription(),
                task.getTaskCity(),
                task.getFoodDeliveryData().getMileage(),
                task.getFoodDeliveryData().getFamilyCount(),
                task.getFoodDeliveryData().getFoodAmount(),
                task.getFoodDeliveryData().getFoodHoursWorked().doubleValue(),
                packageType,
                task.getFoodDeliveryData().getStoreId().getStoreName()};
            return communityFoodBuilder.buildJSON(comFoodTaskValues);
        } else {
            Object[] orgFoodTaskValues = {task.getFoodDeliveryData().getTaskFdId(),
                task.getProgramId().getProgramId(),
                task.getProgramId().getProgramName(),
                task.getUserId().getFirstName(),
                task.getUserId().getLastName(),
                jsonDateFormat.format(task.getStartTime()),
                jsonDateFormat.format(taskEnd),
                task.getTaskDescription(),
                task.getTaskCity(),
                task.getFoodDeliveryData().getMileage(),
                task.getFoodDeliveryData().getOrganizationId().getOrgName(),
                task.getFoodDeliveryData().getFoodAmount(),
                task.getFoodDeliveryData().getFoodHoursWorked().doubleValue(),
                packageType,
                task.getFoodDeliveryData().getStoreId().getStoreName()};

            return communityFoodBuilder.buildJSON(orgFoodTaskValues);
        }
    }

    /**
     * Creates a hotline JSON object
     *
     * @param task The hotline task to populate the JSON data
     * @param hotLineBuilder The JSONBuilder to create the JSON with
     * @return A hotline task JSON as a string
     */
    private String buildHotlineJSON(Task task, JSONBuilder hotLineBuilder) {

        // retrieving program values into an array
        Object[] hotLineValues = {task.getHotlineData().getTaskHotlineId(),
            task.getProgramId().getProgramId(),
            task.getProgramId().getProgramName(),
            task.getUserId().getFirstName(),
            task.getUserId().getLastName(),
            jsonDateFormat.format(task.getStartTime()),
            jsonDateFormat.format(task.getEndTime()),
            task.getTaskDescription(),
            task.getTaskCity(),
            "HotLine",
            task.getHotlineData().getHotlineHoursWorked().doubleValue()};

        return hotLineBuilder.buildJSON(hotLineValues);
    }
    
    /**
     *
     * Backend code for handling approve / disapprove of the task or clicking of the cancel button
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters errors
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // obtain action from the JSP
        // approve , disapprove, cancel
        String action = request.getParameter("action");

        // try catch with a switch to do the action based on what was clicked
        try {
            switch (action) {
                // approve the submitted task
                case "Approve":
                    approve(request, response);
                    break;

                // dissapprove the submitted task
                case "Disapprove":
                    disapprove(request, response);
                    break;

                // cancel and go back to list of tasks
                case "Cancel":
                    response.sendRedirect("approve");
                    break;
                default:
                    System.out.println("no action picked");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(TaskApproveDissaproveServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }

    }

    private void approve(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get the task based on task id
            TaskService ts = new TaskService();

            // get task Id to update approval
            String taskId = request.getParameter("id");
            System.out.println("Task id: " + taskId);

            // get approval notes to update
            String approvalNotes = request.getParameter("approvalText");

            //call on db to set this task to approved
            ts.approveTask(Long.parseLong(taskId), approvalNotes);

            response.sendRedirect("approve");
        } catch (Exception ex) {
            Logger.getLogger(TaskApproveDissaproveServlet.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    private void disapprove(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Get the task based on task id
            TaskService ts = new TaskService();

            // get task Id to update approval
            String taskId = request.getParameter("id");

            // get approval notes to update
            String approvalNotes = request.getParameter("approvalText");
//          System.out.println("approval notes: " + approvalNotes);

            //call on db to set this task to approved
            ts.disapproveTask(Long.parseLong(taskId), approvalNotes);

            response.sendRedirect("approve");
        } catch (Exception ex) {
            Logger.getLogger(TaskApproveDissaproveServlet.class.getName()).log(Level.WARNING, null, ex);
        }
    }

}
