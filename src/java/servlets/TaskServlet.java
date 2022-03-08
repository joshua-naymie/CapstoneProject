package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import models.*;
import services.*;

public class TaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskService taskService = new TaskService();
        List<Task> taskList = null;
        try {
            taskList = taskService.getAll();
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("tasks", taskList);

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
        getServletContext().getRequestDispatcher("/WEB-INF/task.jsp").forward(request, response);
    }

    private String buildTaskJSON(Task task, JSONBuilder builder)
    {
        Object[] taskValues = { task.getTaskId(),
                new Program(task.getProgramId().getProgramId()).getProgramName(),
                task.getStartTime(),
                task.getEndTime(),
                task.getTaskDescription() };

        return builder.buildJSON(taskValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TaskService taskService= new TaskService();
        List<Task> tasks = null;

    }
}
