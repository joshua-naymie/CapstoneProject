package servlets;

import models.util.JSONBuilder;
import models.util.JSONKey;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

import models.*;
import services.*;

public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // logged in user
        HttpSession httpSession = request.getSession();
        String user_id = (String) httpSession.getAttribute("email");
        System.out.println(user_id);
        User loggedInUser = new User();
        if (user_id != null && user_id.matches("[0-9]+")) {
            loggedInUser = new User(Integer.parseInt(user_id));
        }
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
                JSONKey[] taskKeys = {new JSONKey("task_id", true),
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
                        new JSONKey("store_name", true)};

                JSONBuilder taskBuilder = new JSONBuilder(taskKeys);

                returnData.append("var editTask = ");

                Date startDate = editTask.getStartTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(startDate);
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                String startTime = simpleDateFormat.format(startDate);
                Date endDate = editTask.getEndTime();
                String endTime = simpleDateFormat.format(endDate);

                Object[] taskData = {editTask.getTaskId(),
                        editTask.getProgramId().getProgramId(),
                        editTask.getProgramId().getProgramName(),
                        editTask.getMaxUsers(),
                        date,
                        startTime,
                        endTime,
                        editTask.getAvailable(),
                        editTask.getNotes(),
                        editTask.getIsApproved(),
                        editTask.getApprovingManager(),
                        editTask.getTaskDescription(),
                        editTask.getTaskCity(),
                        editTask.getIsSubmitted(),
                        editTask.getApprovalNotes(),
                        editTask.getIsDissaproved(),
                        editTask.getTeamId().getTeamId(),
                        editTask.getTeamId().getStoreId().getCompanyId().getCompanyId(),
                        editTask.getTeamId().getStoreId().getCompanyId().getCompanyName(),
                        editTask.getTeamId().getStoreId().getStoreId(),
                        editTask.getTeamId().getStoreId().getStoreName()};

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
                    List<User> teamUserList = team.getUserList();
                    teamUserList.removeAll(chosenUsers);
                    List<User> canBeAssigned = teamUserList;

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

                Object[] taskData = {editTask.getTaskId(),
                        editTask.getProgramId().getProgramId(),
                        editTask.getProgramId().getProgramName(),
                        editTask.getMaxUsers(),
                        date,
                        startTime,
                        endTime,
                        editTask.getAvailable(),
                        editTask.getNotes(),
                        editTask.getIsApproved(),
                        editTask.getTaskDescription(),
                        editTask.getTaskCity(),
                        editTask.getIsSubmitted(),
                        editTask.getApprovalNotes(),
                        editTask.getIsDissaproved(),
                        editTask.getTeamId().getTeamId()};

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

            try {
//                UserTaskService userTaskService = new UserTaskService();
//                List<User> chosenUsers = userTaskService.getChosenUsers(editTask.getTaskId());
//                Team team = new Team(editTask.getTeamId().getTeamId());
////                List<User> canBeAssigned = team.getUserList().stream().filter(chosenUsers::contains).collect(Collectors.toList());
//                canBeAssigned.remove(loggedInUser);
//
//                request.setAttribute("chosenUsers", chosenUsers);
//                request.setAttribute("canBeAssigned", canBeAssigned);
            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("user_id", user_id);

            System.out.println("ID: " + task_id);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // logged in user
        HttpSession httpSession = request.getSession();
        String user_id = (String) httpSession.getAttribute("email");
        System.out.println(user_id);
        User loggedInUser = new User(Integer.parseInt(user_id));

        try {
            TaskService taskService = new TaskService();
            long taskId = Long.parseLong(request.getParameter("task_id"));
            Task task = taskService.get(taskId);

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

            // Insert and update UserTask
            UserTaskService userTaskService = new UserTaskService();

            String userIdList = request.getParameter("selected_user_id_list");
            String[] list_of_ids = userIdList.split("&");
            List<Integer> listOfAssinedUserIds = null;
            for (String userAndId : list_of_ids) {
                String[] thisUserId = userAndId.split("=");
                listOfAssinedUserIds.add(Integer.parseInt(thisUserId[1]));
            }
            for (int userId : listOfAssinedUserIds) {
                UserTask userTask = new UserTask(userId, taskId);
                userTask.setIsAssigned(true);
                if (userTaskService.getAll().contains(userTask)) {
                    userTaskService.update(userTask);
                } else {
                    userTaskService.insert(userTask);
                }
            }

            response.sendRedirect("tasks");
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
        }
    }
}
