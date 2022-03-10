package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.util.*;
import java.util.logging.*;

import models.*;
import services.*;

public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("task_id");
        if (id != null) {
            TaskService taskService = new TaskService();
            Task editTask = new Task();
            try {
                editTask = taskService.get(Long.parseLong(id));
            } catch (Exception ex) {
                Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
            }

            JSONKey[] taskKeys = { new JSONKey("id", true),
                    new JSONKey("program_name", true),
                    new JSONKey("max_users", true),
                    new JSONKey("start_time", true),
                    new JSONKey("end_time", true),
                    new JSONKey("available", true),
                    new JSONKey("notes", false),
                    new JSONKey("task_description", true),
                    new JSONKey("task_city", true) };

            JSONBuilder taskBuilder = new JSONBuilder(taskKeys);
            // make the task object into json data
            StringBuilder returnData = new StringBuilder();

            returnData.append("");

            Object[] taskData = { editTask.getTaskId(),
                    editTask.getProgramId().getProgramName(),
                    editTask.getMaxUsers(),
                    editTask.getStartTime(),
                    editTask.getEndTime(),
                    editTask.getAvailable(),
                    editTask.getNotes(),
                    editTask.getTaskDescription(),
                    editTask.getTaskCity() };

            returnData.append(taskBuilder.buildJSON(taskData));
            returnData.append(";");

            request.setAttribute("taskData", returnData);
        }
        System.out.println("ID: " + id);

        getServletContext().getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}