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
//        String user_id = (String) httpSession.getAttribute("email");
//        System.out.println(user_id);
//        User loggedInUser = new User();
//        if (user_id != null && user_id.matches("[0-9]+")) {
//            loggedInUser = new User(Integer.parseInt(user_id));
//        }
        String task_id = request.getParameter("task_id");
        if (task_id != null) {
            TaskService taskService = new TaskService();
            Task editTask = new Task();
            try {
                editTask = taskService.get(Long.parseLong(task_id));
                // make the task object into json data
                StringBuilder returnData = new StringBuilder();

                if (editTask.getProgramId().getProgramName().equals("Food Delivery")) {
                    JSONKey[] taskKeys = {
                            new JSONKey("task_id", true),
                            new JSONKey("program_id", true),
                            new JSONKey("program_name", true),
                            new JSONKey("max_users", true),
                            new JSONKey("group_id", true),
                            new JSONKey("spots_taken", true),
                            new JSONKey("is_assigned", false),
                            new JSONKey("date", true),
                            new JSONKey("start_time", true),
                            new JSONKey("end_time", true),
                            new JSONKey("available", false),
                            new JSONKey("task_description", true),
                            new JSONKey("task_city", true),
                            new JSONKey("team_id", true),
                            new JSONKey("team_name", true),
                            new JSONKey("company_id", true),
                            new JSONKey("company_name", true),
                            new JSONKey("store_id", true),
                            new JSONKey("store_name", true)
                    };

                    JSONBuilder taskBuilder = new JSONBuilder(taskKeys);

                    returnData.append("var editTask = ");

                    Date startDate = editTask.getStartTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = simpleDateFormat.format(startDate);
                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    String startTime = simpleDateFormat.format(startDate);

                    Date endDate = null;
                    String endTime = null;
                    if (editTask.getEndTime() != null) {
                        endDate = editTask.getEndTime();
                        endTime = simpleDateFormat.format(endDate);
                    }

                    User approvingManager = new User(editTask.getApprovingManager());
                    List<User> canBeApprovingManagers = new ArrayList<>();
                    TeamServices teamServices = new TeamServices();
                    for (Team team : teamServices.getAll()) {
                        User user = new User(team.getTeamSupervisor());
                        canBeApprovingManagers.add(user);
                    }
                    ProgramServices programServices = new ProgramServices();
                    for (Program program : programServices.getAll()) {
                        canBeApprovingManagers.add(program.getUserId());
                    }

                    short maxUsers = -1;
                    if (editTask.getMaxUsers() != null) {
                         maxUsers = editTask.getMaxUsers();
                    }
                    String taskDescription = null;
                    if (editTask.getTaskDescription() != null) {
                        taskDescription = editTask.getTaskDescription();
                    }
                    String taskCity = null;
                    if (editTask.getTaskCity() != null) {
                        taskCity = editTask.getTaskCity();
                    }
                    int storeId = -1;
                    String storeName = null;
                    short companyId = -1;
                    String companyName = null;
                    if (editTask.getTeamId().getStoreId() != null) {
                        storeId = editTask.getTeamId().getStoreId().getStoreId();
                        storeName = editTask.getTeamId().getStoreId().getStoreName();
                        if (editTask.getTeamId().getStoreId().getCompanyId() != null) {
                            companyId = editTask.getTeamId().getStoreId().getCompanyId().getCompanyId();
                            companyName = editTask.getTeamId().getStoreId().getCompanyId().getCompanyName();
                        }
                    }
                    List<User> assignedUsers = new ArrayList<>();
                    List<User> canBeAssignedUsers = taskService.getCanBeAssignedUsersFoodDelivery(editTask.getGroupId());
                    if (editTask.getSpotsTaken() > 0) {
                        for (Task task : taskService.getAllTasksInGroup(editTask.getGroupId())) {
                            if (task.getUserId() != null) {
                                assignedUsers.add(task.getUserId());
                            }
                        }
                        for (User user : assignedUsers) {
                            canBeAssignedUsers.remove(user);
                        }
                    }

for(User user: canBeAssignedUsers) {System.out.println(user.getFirstName());}

                    request.setAttribute("approving_manager", approvingManager);
                    request.setAttribute("can_be_approving_managers", canBeApprovingManagers);
                    request.setAttribute("assigned_users", assignedUsers);
                    request.setAttribute("can_be_assigned_users", canBeAssignedUsers.stream().distinct().collect(Collectors.toList()));

                    Object[] taskData = {
                            editTask.getTaskId(),
                            editTask.getProgramId().getProgramId(),
                            editTask.getProgramId().getProgramName(),
                            maxUsers,
                            editTask.getGroupId(),
                            editTask.getSpotsTaken(),
                            editTask.getAssigned(),
                            date,
                            startTime,
                            endTime,
                            editTask.getAvailable(),
                            taskDescription,
                            taskCity,
                            editTask.getTeamId().getTeamId(),
                            editTask.getTeamId().getTeamName(),
                            companyId,
                            companyName,
                            storeId,
                            storeName
                    };

                    returnData.append(taskBuilder.buildJSON(taskData));
                    returnData.append(";");
                } else {
                    JSONKey[] taskKeys = { new JSONKey("task_id", true),
                            new JSONKey("program_id", true),
                            new JSONKey("program_name", true),
                            new JSONKey("date", true),
                            new JSONKey("start_time", true),
                            new JSONKey("end_time", true),
                            new JSONKey("available", false),
                            new JSONKey("task_description", true),
                            new JSONKey("team_id", true),
                            new JSONKey("team_name", true),
                    };

                    JSONBuilder taskBuilder = new JSONBuilder(taskKeys);

                    returnData.append("var editTask = ");

                    Date startDate = editTask.getStartTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String date = simpleDateFormat.format(startDate);
                    simpleDateFormat = new SimpleDateFormat("HH:mm");
                    String startTime = simpleDateFormat.format(startDate);

                    Date endDate = null;
                    String endTime = null;
                    if (editTask.getEndTime() != null) {
                        endDate = editTask.getEndTime();
                        endTime = simpleDateFormat.format(endDate);
                    }
                    String taskDescription = null;
                    if (editTask.getTaskDescription() != null) {
                        taskDescription = editTask.getTaskDescription();
                    }
                    List<User> canBeApprovingManagers = new ArrayList<>();

                    ProgramServices programServices = new ProgramServices();
                    for (Program program : programServices.getAll()) {
                        canBeApprovingManagers.add(program.getUserId());
                    }
                    canBeApprovingManagers.addAll(taskService.getCanBeApprovingManagersHotline(editTask.getTaskId()));

                    request.setAttribute("approving_manager", editTask.getApprovingManager());
                    request.setAttribute("can_be_approving_managers", canBeApprovingManagers);
                    request.setAttribute("assigned_user", editTask.getUserId());
                    request.setAttribute("can_be_assigned_users", taskService.getCanBeAssignedUsersHotline(editTask.getTaskId()));

                    Object[] taskData = {
                            editTask.getTaskId(),
                            editTask.getProgramId().getProgramId(),
                            editTask.getProgramId().getProgramName(),
                            date,
                            startTime,
                            endTime,
                            editTask.getAvailable(),
                            taskDescription,
                            editTask.getTeamId().getTeamId(),
                            editTask.getTeamId().getTeamName() };

                    returnData.append(taskBuilder.buildJSON(taskData));
                    returnData.append(";");
                }
                request.setAttribute("taskData", returnData);
            } catch (Exception ex) {
                Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
            }

            System.out.println("ID: " + task_id);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // logged in user
        HttpSession httpSession = request.getSession();
//        String user_id = (String) httpSession.getAttribute("email");
//        System.out.println(user_id);
//        User loggedInUser = new User(Integer.parseInt(user_id));

        try {
            TaskService taskService = new TaskService();
            long taskId = Long.parseLong(request.getParameter("task_id"));
            Task task = taskService.get(taskId);
            long groupId = task.getGroupId();

            String date = request.getParameter("date");
            String startTime = request.getParameter("start_time");
            String endTime = request.getParameter("end_time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String userIdList = request.getParameter("selected_user_id_list");
            List<User> assignedUsers = new ArrayList<>();
            System.out.println(userIdList);
            if (userIdList != null) {
                String[] list_of_ids = userIdList.split(",");
            
                for (String id : list_of_ids) {
                    User user = new User(Integer.parseInt(id));
                    assignedUsers.add(user);
                }
            }
            

            if (task.getProgramId().getProgramName().equals("Food Delivery")) {
                for (Task task1 : taskService.getAllTasksInGroup(groupId)) {
                    taskService.delete(task1);
                }
//                Team team = new Team(Integer.parseInt(request.getParameter("team_id")));

                short maxUsers = Short.parseShort(request.getParameter("max_users"));

                Task task1 = new Task(
                        0L,
                        -1L,
                        simpleDateFormat.parse(date + startTime),
                        true,
                        false,
                        Integer.parseInt(request.getParameter("approving_manager_id")),
                        Short.parseShort(request.getParameter("spots_taken")),
                        request.getParameter("task_city")
                );
                task1.setProgramId(task.getProgramId());
                task1.setMaxUsers(maxUsers);
                task1.setEndTime(simpleDateFormat.parse(date + endTime));
                task1.setTeamId(task.getTeamId());
                task1.setAssigned(Boolean.parseBoolean(request.getParameter("assigned")));
                task1.setTaskDescription(request.getParameter("task_description"));
                task1.setNotes(task.getNotes());
                task1.setIsSubmitted(false);
                task1.setIsDissaproved(false);
                task1.setUserId(assignedUsers.get(0));

                taskService.insert(task1);

                if (maxUsers > 1) {
                    for (int i = 1; i < maxUsers; i++) {
                        Task task2 = new Task(
                                0L,
                                task1.getGroupId(),
                                task1.getStartTime(),
                                task1.getAvailable(),
                                false,
                                task1.getApprovingManager(),
                                task1.getSpotsTaken(),
                                task1.getTaskCity()
                        );
                        task2.setProgramId(task1.getProgramId());
                        task2.setMaxUsers(task1.getMaxUsers());
                        task2.setEndTime(task1.getEndTime());
                        task2.setTeamId(task1.getTeamId());
                        task2.setAssigned(task1.getAssigned());
                        task2.setTaskDescription(task1.getTaskDescription());
                        task2.setNotes(task1.getNotes());
                        task2.setIsSubmitted(false);
                        task2.setIsDissaproved(false);
                        task2.setUserId(assignedUsers.get(i));

                        taskService.insert(task2);
                    }
                }
            } else {
                task.setStartTime(simpleDateFormat.parse(date + startTime));
                task.setEndTime(simpleDateFormat.parse(date + endTime));

//                task.setAvailable(Boolean.parseBoolean(request.getParameter("available")));
                task.setTaskDescription(request.getParameter("task_description"));

//                Team team = new Team(Integer.parseInt(request.getParameter("team_id")));
                task.setTeamId(task.getTeamId());

                task.setApprovingManager(Integer.parseInt(request.getParameter("approving_manager_id")));

                task.setUserId(assignedUsers.get(0));

                taskService.update(task);
            }
        } catch (Exception ex) {
            Logger.getLogger(TaskServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/editTask.jsp").forward(request, response);
    }
}
