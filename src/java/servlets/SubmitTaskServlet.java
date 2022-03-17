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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.HotlineData;
import models.util.JSONBuilder;
import models.util.JSONKey;
import models.PackageType;
import models.Program;
import models.Task;
import services.TaskService;

/**
 *
 * @author srvad
 */
public class SubmitTaskServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    TaskService ts = new TaskService();
    
    List<Task> taskList = null;
    
        try {
            taskList = ts.getAllNotApprovedTasks();
        } catch (Exception ex) {
            Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    request.setAttribute("allTasks", taskList);
    
    StringBuilder returnData = new StringBuilder();
    returnData.append("[");
    
            JSONKey[] keys = { new JSONKey("task_id", true),
                new JSONKey("program_name", true),
                new JSONKey("start_time", true),
                new JSONKey("end_time", true),
                new JSONKey("task_description", true) };

        JSONBuilder builder = new JSONBuilder(keys);

        if (taskList.size() > 0) {
            int i = 0;
            for (i = 0; i < taskList.size() - 1; i++) {
                returnData.append(buildTaskJSON(taskList.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildTaskJSON(taskList.get(i), builder));
        }
        returnData.append("];");

        request.setAttribute("taskData", returnData);
        
    getServletContext().getRequestDispatcher("/WEB-INF/submitTask.jsp").forward(request, response);
    
    }
    
    private String buildTaskJSON(Task task, JSONBuilder builder)
    {
        Program program = task.getProgramId();
        Object[] taskValues = { task.getTaskId(),
                program.getProgramName(),
                task.getStartTime(),
                task.getEndTime(),
                task.getTaskDescription() };

        return builder.buildJSON(taskValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
