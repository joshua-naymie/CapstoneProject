package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;

import models.*;
import models.util.JSONBuilder;
import models.util.JSONKey;
import services.*;

public class SubmittedTasksServlet extends HttpServlet {

    private static final DateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");

    private static final String HISTORY_JSP_DIR = "/WEB-INF/submittedTasks.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // getting all tasks that need approving
        TaskService ts = new TaskService();
        List<Task> needApproval = null;

        try {
            needApproval = ts.getSubmittedToManager("Manager Jane");  //get a list of all tasks that need approval
            //System.out.println("check needapproval size: " + needApproval.size());
            // sending json data
            StringBuilder taskReturnData = new StringBuilder();
            taskReturnData.append("var taskData = [");

            // creating keys for hotline
            JSONKey[] hotlineKeys = {new JSONKey("task_hotline_id", false),
                new JSONKey("ID", false),
                new JSONKey("programName", true),
                new JSONKey("startTime", true),
                new JSONKey("teamName", true)};

            // creating key for food delivery
            JSONKey[] foodDeliveryKeys = {new JSONKey("task_fd_id", false),
                new JSONKey("ID", false),
                new JSONKey("programName", true),
                new JSONKey("startTime", true),
                new JSONKey("userList", true),
                new JSONKey("storeName", true)};

            // builder for hotline
            JSONBuilder hotLineBuilder = new JSONBuilder(hotlineKeys);

            // builder for food delivery
            JSONBuilder foodBuilder = new JSONBuilder(foodDeliveryKeys);
            
            // Create task JSON objects
            if (needApproval.size() > 0) {
                int i;
                for (i = 0; i < needApproval.size() - 1; i++) {
                    if (needApproval.get(i).getProgramId().getProgramId() == 1) {
                        taskReturnData.append(buildFoodJSON(needApproval.get(i), foodBuilder));
                    } else if (needApproval.get(i).getProgramId().getProgramId() == 2) {
                        taskReturnData.append(buildHotlineJSON(needApproval.get(i), hotLineBuilder));
                    } else {
                        System.out.println("wrong program id");
                    }
                    taskReturnData.append(',');
                }
                if (needApproval.get(i).getProgramId().getProgramId() == 1) {
                    taskReturnData.append(buildFoodJSON(needApproval.get(i), foodBuilder));
                } else if (needApproval.get(i).getProgramId().getProgramId() == 2) {
                    taskReturnData.append(buildHotlineJSON(needApproval.get(i), hotLineBuilder));
                }
            }
            taskReturnData.append("];");

            // setting user data attribute for the front end to use
            request.setAttribute("taskData", taskReturnData);
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }

        // forward to jsp
        getServletContext().getRequestDispatcher(HISTORY_JSP_DIR).forward(request, response);

    }

    /**
     * Creates a food JSON object
     *
     * @param task The food task to populate the JSON data
     * @param foodBuilder The JSONBuilder to create the JSON with
     * @return A food task JSON as a string
     */
    private String buildFoodJSON(Task task, JSONBuilder foodBuilder) {

        StringBuilder allUserNames = new StringBuilder();

//        for (UserTask userTask : task.getUserTaskList()) {
//            allUserNames.append(userTask.getUser().getFirstName());
//            allUserNames.append(" ");
//            allUserNames.append(userTask.getUser().getLastName());
//            allUserNames.append(", ");
//        }
        // retrieving program values into an array
        Object[] foodTaskValues = {task.getFoodDeliveryData().getTaskFdId(),
            task.getFoodDeliveryData().getTaskFdId(),
            task.getProgramId().getProgramName(),
            jsonDateFormat.format(task.getStartTime()),
            allUserNames,
            task.getTeamId().getStoreId().getStoreName()};

        return foodBuilder.buildJSON(foodTaskValues);
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
            task.getHotlineData().getTaskHotlineId(),
            task.getProgramId().getProgramName(),
            jsonDateFormat.format(task.getStartTime()),
            "HotLine"};

        return hotLineBuilder.buildJSON(hotLineValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
