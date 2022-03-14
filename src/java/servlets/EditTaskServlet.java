package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

import models.*;
import services.*;

public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // logged in user
        String user_id = request.getParameter("user_id");
        User loggedInUser = new User(Integer.parseInt(user_id));

        String task_id = request.getParameter("task_id");
        if (task_id != null) {
            TaskService taskService = new TaskService();
            Task editTask = new Task();
            try {
                editTask = taskService.get(Long.parseLong(task_id));
            } catch (Exception ex) {
                Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
            }

            // make the task object into json data
            StringBuilder returnData = new StringBuilder();

            if (editTask.getProgramId().getProgramName().equals("Food Delivery")) {
                JSONKey[] taskKeys = { new JSONKey("task_id", true),
                        new JSONKey("program_id", true),
                        new JSONKey("program_name", true),
                        new JSONKey("max_users", true),
                        new JSONKey("date", true),
                        new JSONKey("start_time", true),
                        new JSONKey("end_time", true),
                        new JSONKey("available", false),
                        new JSONKey("notes", true),
                        new JSONKey("is_approved", false),
                        new JSONKey("approving_manager", true),
                        new JSONKey("task_description", true),
                        new JSONKey("task_city", true),
                        new JSONKey("is_submitted", false),
                        new JSONKey("approval_notes", true),
                        new JSONKey("is_dissaproved", false),
                        new JSONKey("team_id", true),
                        new JSONKey("company_id", true),
                        new JSONKey("company_name", true),
                        new JSONKey("store_id", true),
                        new JSONKey("store_name", true) };

                JSONBuilder taskBuilder = new JSONBuilder(taskKeys);

                returnData.append("var editTask = ");

                Date startDate = editTask.getStartTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(startDate);
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                String startTime = simpleDateFormat.format(startDate);
                Date endDate = editTask.getEndTime();
                String endTime = simpleDateFormat.format(endDate);

                Object[] taskData = { editTask.getTaskId(),
                        editTask.getProgramId().getProgramId(),
                        editTask.getProgramId().getProgramName(),
                        editTask.getMaxUsers(),
                        date,
                        startTime,
                        endTime,
                        editTask.getAvailable(),
                        editTask.getNotes(),
                        editTask.isApproved(),
                        editTask.getApprovingManager(),
                        editTask.getTaskDescription(),
                        editTask.getTaskCity(),
                        editTask.isSubmitted(),
                        editTask.getApprovalNotes(),
                        editTask.isDissaproved(),
                        editTask.getTeamId().getTeamId(),
                        editTask.getTeamId().getStoreId().getCompanyId().getCompanyId(),
                        editTask.getTeamId().getStoreId().getCompanyId().getCompanyName(),
                        editTask.getTeamId().getStoreId().getStoreId(),
                        editTask.getTeamId().getStoreId().getStoreName() };

                returnData.append(taskBuilder.buildJSON(taskData));
                returnData.append(";");

                try {
                    StoreServices storeServices = new StoreServices();
                    List<Store> stores = storeServices.getAll();
                    CompanyService companyService = new CompanyService();
                    List<CompanyName> companyNames = companyService.getAll();

                    UserTaskService userTaskService = new UserTaskService();
                    List<User> chosenUsers = userTaskService.getChosenUsers(editTask.getTaskId());
                    Team team = new Team(editTask.getTeamId().getTeamId());
                    List<User> canBeAssigned = team.getUserList().stream().filter(chosenUsers::contains).collect(Collectors.toList());
                    canBeAssigned.remove(loggedInUser);

                    request.setAttribute("chosenUsers", chosenUsers);
                    request.setAttribute("canBeAssigned", canBeAssigned);
                    request.setAttribute("stores", stores);
                    request.setAttribute("companies", companyNames);
                } catch (Exception ex) {
                    Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
                }
            } else {
                JSONKey[] taskKeys = { new JSONKey("task_id", true),
                        new JSONKey("program_id", true),
                        new JSONKey("program_name", true),
                        new JSONKey("max_users", true),
                        new JSONKey("date", true),
                        new JSONKey("start_time", true),
                        new JSONKey("end_time", true),
                        new JSONKey("available", false),
                        new JSONKey("notes", true),
                        new JSONKey("is_approved", false),
                        new JSONKey("task_description", true),
                        new JSONKey("task_city", true),
                        new JSONKey("is_submitted", false),
                        new JSONKey("approval_notes", true),
                        new JSONKey("is_dissaproved", false),
                        new JSONKey("team_id", true) };

                JSONBuilder taskBuilder = new JSONBuilder(taskKeys);

                returnData.append("var editTask = ");

                Date startDate = editTask.getStartTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(startDate);
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                String startTime = simpleDateFormat.format(startDate);
                Date endDate = editTask.getEndTime();
                String endTime = simpleDateFormat.format(endDate);

                Object[] taskData = { editTask.getTaskId(),
                        editTask.getProgramId().getProgramId(),
                        editTask.getProgramId().getProgramName(),
                        editTask.getMaxUsers(),
                        date,
                        startTime,
                        endTime,
                        editTask.getAvailable(),
                        editTask.getNotes(),
                        editTask.isApproved(),
                        editTask.getTaskDescription(),
                        editTask.getTaskCity(),
                        editTask.isSubmitted(),
                        editTask.getApprovalNotes(),
                        editTask.isDissaproved(),
                        editTask.getTeamId().getTeamId() };

                returnData.append(taskBuilder.buildJSON(taskData));
                returnData.append(";");
            }

            request.setAttribute("taskData", returnData);
	
	ProgramServices ps = new ProgramServices();
	List<Program> allPrograms = null;

		try {
		    allPrograms = ps.getAll();
		} catch (Exception ex) {
		    Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
		}

	//     for(Program p: allPrograms) {
	//         System.out.println(p.getProgramName());
	//         System.out.println(p.getProgramId());
	//     }
	    request.setAttribute("allPrograms", allPrograms);
        }
        System.out.println("ID: " + task_id);

        getServletContext().getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            TaskService taskService = new TaskService();
            Task task = taskService.get(Long.parseLong(request.getParameter("task_id")));

            task.setProgramId(new Program(Short.parseShort(request.getParameter("program_id"))));
            short maxUsers = Short.parseShort(request.getParameter("max_users"));
            task.setMaxUsers(maxUsers);

            String date = request.getParameter("date");
            String startTime = request.getParameter("start_time");
            String endTime = request.getParameter("end_time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            task.setStartTime(simpleDateFormat.parse(date + startTime));
            task.setEndTime(simpleDateFormat.parse(date + endTime));

            task.setNotes(request.getParameter("notes"));
            task.setTaskDescription(request.getParameter("task_description"));
            task.setTaskCity(request.getParameter("task_city"));

            Team team = new Team(Integer.parseInt(request.getParameter("team_id")));
            task.setTeamId(team);

            if (task.getProgramId().getProgramName().equals("Food Delivery")) {
                task.setApprovingManager(request.getParameter("approving_manager"));
            }
            
            taskService.update(task);

            UserTaskService userTaskService = new UserTaskService();

            // Insert and update UserTask

            response.sendRedirect("tasks");
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
        }
    }
}