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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.util.JSONBuilder;
import models.util.JSONKey;
import models.Task;
import models.UserTask;
import services.TaskService;

/**
 *
 * @author 861349
 */
public class TaskApproveDissaproveServlet extends HttpServlet {

    private static final DateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // retrieve task information based on URL parameter
        String taskId = request.getParameter("id");
        Task task = null;
        try {
            if (taskId != null) {
                TaskService ts = new TaskService();
                task = ts.get(Long.parseLong(taskId));
            }

            // sending json data
            StringBuilder taskReturnData = new StringBuilder();
            taskReturnData.append("var taskData = [");

            // creating keys for hotline
            JSONKey[] hotlineTaskKeys = {new JSONKey("taskID", false),
                new JSONKey("programName", true),
                new JSONKey("fullName", true),
                new JSONKey("startTime", true),
                new JSONKey("endTime", true),
                new JSONKey("description", true),
                new JSONKey("city", true),
                new JSONKey("teamName", true)};

            // creating keys for food delivery for community
            JSONKey[] communityFoodTaskKeys = {new JSONKey("taskID", false),
                new JSONKey("programName", true),
                new JSONKey("fullName", true),
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
                new JSONKey("programName", true),
                new JSONKey("fullName", true),
                new JSONKey("startTime", true),
                new JSONKey("endTime", true),
                new JSONKey("description", true),
                new JSONKey("city", true),
                new JSONKey("mileage", true),
                new JSONKey("organizationName", false),
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
            boolean isFood = false;

            // creating JSON objects
            if (task != null) {
                if (task.getProgramId().getProgramId() == 1 && task.getFoodDeliveryData().getFamilyCount() != 0) {
                    taskReturnData.append(buildCommunityFoodJSON(task, communityFoodBuilder));
                } else if (task.getProgramId().getProgramId() == 1 && task.getFoodDeliveryData().getOrganizationId() != null) {
                    // change when method is made
                    taskReturnData.append(buildOrgFoodJSON(task, orgFoodBuilder));
                } else if (task.getProgramId().getProgramId() == 2) {
                    taskReturnData.append(buildHotlineJSON(task, hotLineBuilder));
                } else {
                    System.out.println("wrong program id");
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }

    }

    /**
     * Creates a food JSON object for community type donations 
     *
     * @param task The food task to populate the JSON data
     * @param foodBuilder The JSONBuilder to create the JSON with
     * @return A food task JSON as a string
     */
    private String buildCommunityFoodJSON(Task task, JSONBuilder communityFoodBuilder) {

        StringBuilder allUserNames = new StringBuilder();

        for (UserTask userTask : task.getUserTaskList()) {
            allUserNames.append(userTask.getUser().getFirstName());
            allUserNames.append(" ");
            allUserNames.append(userTask.getUser().getLastName());
            allUserNames.append(", ");
        }
        // calculating hours worked
        long hoursWorkedInMilliSeconds = task.getStartTime().getTime() - task.getEndTime().getTime();
        long hoursWorked = (hoursWorkedInMilliSeconds/ (1000 * 60))% 60;
        
        // getting package type
        String packageType= task.getFoodDeliveryData().getPackageId().getPackageName();
        
        // getting package weight
        short packageWeight= task.getFoodDeliveryData().getPackageId().getWeightLb();
        
        // retrieving program values into an array
        Object[] foodTaskValues = {task.getFoodDeliveryData().getTaskFdId(),
            task.getProgramId().getProgramName(),
            allUserNames,
            jsonDateFormat.format(task.getStartTime()),
            jsonDateFormat.format(task.getEndTime()),
            task.getTaskDescription(),
            task.getTaskCity(),
            task.getFoodDeliveryData().getMileage(),
            task.getFoodDeliveryData().getFamilyCount(),
            task.getFoodDeliveryData().getFoodAmount(),
            hoursWorked,
            packageType,
            task.getTeamId().getStoreId().getStoreName()};

        return communityFoodBuilder.buildJSON(foodTaskValues);
    }
    
    /**
     * Creates a food JSON object for organization type donations
     *
     * @param task The food task to populate the JSON data
     * @param foodBuilder The JSONBuilder to create the JSON with
     * @return A food task JSON as a string
     */
    private String buildOrgFoodJSON(Task task, JSONBuilder orgFoodBuilder) {

        StringBuilder allUserNames = new StringBuilder();

        for (UserTask userTask : task.getUserTaskList()) {
            allUserNames.append(userTask.getUser().getFirstName());
            allUserNames.append(" ");
            allUserNames.append(userTask.getUser().getLastName());
            allUserNames.append(", ");
        }
        // retrieving program values into an array
        Object[] foodTaskValues = {task.getFoodDeliveryData().getTaskFdId(),
            task.getProgramId().getProgramName(),
            allUserNames,
            jsonDateFormat.format(task.getStartTime()),
            jsonDateFormat.format(task.getEndTime()),
            task.getTaskDescription(),
            task.getTaskCity(),
            task.getFoodDeliveryData().getMileage(),
            task.getFoodDeliveryData().getFamilyCount(),
            task.getTeamId().getStoreId().getStoreName()};

        return orgFoodBuilder.buildJSON(foodTaskValues);
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
            task.getProgramId().getProgramName(),
            jsonDateFormat.format(task.getStartTime()),
            "HotLine"};

        return hotLineBuilder.buildJSON(hotLineValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // approve , disapprove, cancel
    }

}
