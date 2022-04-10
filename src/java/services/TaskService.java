/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import models.*;

import javax.persistence.criteria.CriteriaBuilder;

/**
 *
 * @author srvad
 */
public class TaskService {

    /**
     * disapprove task and change boolean attributes in db
     *
     * @param taskId the task to be disapproved
     */
    public void disapproveTask(long taskId) {
        TaskDB taskDB = new TaskDB();
        taskDB.disapproveTask(taskId);
    }

    /**
     * approve a task and change boolean attributes in db
     *
     * @param taskId the task to be approved
     */
    public void approveTask(long taskId) {
        TaskDB taskDB = new TaskDB();
        taskDB.approveTask(taskId);
    }

    public List<Task> getAll() throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> tasks = taskDB.getAll();
        return tasks;
    }

    public List<Task> getAllTasksByGroupId(Long groupId) throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> tasks = taskDB.getAllTasksByGroupId(groupId);
        return tasks;
    }

    public Task get(long id) throws Exception {
        TaskDB taskDB = new TaskDB();
        Task task = taskDB.get(id);
        return task;
    }

    //------------------------------
    /**
     * Gets a user's Task history
     *
     * @param id The ID of the user
     * @return The list of Tasks that falls within the specified filters
     * @throws Exception
     */
    public List<Task> getHistory(long id) throws Exception {
        TaskDB taskDB = new TaskDB();
        return taskDB.getHistoryByUserId(id, null, null, null);
    }

    //------------------------------
    /**
     * Gets a user's Task history filtered by date range
     *
     * @param id The ID of the user
     * @param startDate The start of the date range. Defaults to current time if
     * null/empty. Format = yyyy-MM-dd
     * @param endDate The end of the date range. Can be null/empty. Format =
     * yyyy-MM-dd
     * @return The list of Tasks that falls within the specified filters
     * @throws Exception
     */
    public List<Task> getHistoryByDates(long id, String startDate, String endDate) throws Exception {
        TaskDB taskDB = new TaskDB();
        return taskDB.getHistoryByUserId(id,
                parseDateTime(startDate),
                parseDateTime(endDate),
                null);
    }

    //------------------------------
    /**
     * Gets a user's Task history filtered by date range and program(s)
     *
     * @param id The ID of the user
     * @param startDate The start of the date range. Defaults to current time if
     * null/empty. Format = yyyy-MM-dd
     * @param endDate The end of the date range. Can be null/empty. Format =
     * yyyy-MM-dd
     * @param programs The list of program id's to filter by. Can be null/empty
     * Format '1,3,5'
     * @return The list of Tasks that falls within the specified filters
     * @throws Exception
     */
    public List<Task> getHistoryByDatesPrograms(long id, String startDate, String endDate, String programs) throws Exception {
        TaskDB taskDB = new TaskDB();
        return taskDB.getHistoryByUserId(id,
                parseDateTime(startDate),
                parseDateTime(endDate),
                parsePrograms(programs));
    }

    //------------------------------
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses the date from a string
     *
     * @param date The String to parse into a LocalDateTime. Format = yyyy-MM-dd
     * @return The LocalDateTime parsed from the String. Null not able to parse.
     */
    private LocalDateTime parseDateTime(String date) {

        if (date != null && !date.isBlank()) {
            try {
                return LocalDateTime.parse(date, dateFormatter);
            } catch (DateTimeParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    //------------------------------
    /**
     * Parses the programs from a CSV String into a Short[].
     *
     * @param programs The comma separated value representation of a list of
     * program id's
     * @return A Short[] of all the parsed program id's
     */
    private Short[] parsePrograms(String programs) {
        if (programs == null || programs.isBlank()) {
            return null;
        }

        String[] temp = programs.split(",");
        Short[] parsedPrograms = new Short[temp.length];

        try {
            for (int i = 0; i < temp.length; i++) {
                parsedPrograms[i] = Short.parseShort(temp[i]);
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return parsedPrograms;
    }

    public List<Task> getSubmittedToManager(int id) throws Exception {
        TaskDB taskDB = new TaskDB();
        return taskDB.getSubmittedToManager(id);
    }

    public List<Task> getAllNotApprovedTasksByUserId(int userId) throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> tasks = taskDB.getAllNotApprovedTasksByUserId(userId);
        return tasks;
    }

    public List<Integer> getSupervisors() throws Exception {
        TeamDB teamDB = new TeamDB();
        List<Team> teamList = teamDB.getAll();
        List<Integer> supervisors = new ArrayList<>();
        for (Team team : teamList) {
            supervisors.add(team.getTeamSupervisor());
        }
        return supervisors;
    }

    public List<User> getCanBeAssignedUsersFoodDelivery(long groupId) throws Exception {
        TaskDB taskDB = new TaskDB();
        Task task = taskDB.get(groupId);
        Team team = task.getTeamId();

        UserDB userDB = new UserDB();
        List<User> allUsers = userDB.getAll();
        List<User> canBeAssignedUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (user.getTeamId().equals(team)) {
                canBeAssignedUsers.add(user);
            }
        }
        for (User user : allUsers) {
            if (user.getProgramList().contains(team.getProgramId()) && !canBeAssignedUsers.contains(user)) {
                canBeAssignedUsers.add(user);
            }
        }
        return canBeAssignedUsers;
    }

    public List<User> getCanBeApprovingManagersHotline(long taskId) throws Exception {
        List<User> canBeApprovingManager = new ArrayList<>();
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.getByRoleName("Coordinator");

        ProgramTrainingDB programTrainingDB = new ProgramTrainingDB();
        List<ProgramTraining> programTrainingList = programTrainingDB.getAll();

        for (ProgramTraining programTraining : programTrainingList) {
            if (programTraining.getRoleId().getRoleId() == role.getRoleId()) {
                canBeApprovingManager.add(programTraining.getUser());
            }
        }

        return canBeApprovingManager;
    }

    public List<User> getCanBeAssignedUsersHotline(long taskId) throws Exception {
        TaskDB taskDB = new TaskDB();
        Task task = taskDB.get(taskId);
        Team team = task.getTeamId();

        UserDB userDB = new UserDB();
        List<User> allUsers = userDB.getAll();
        List<User> canBeAssignedUsers = new ArrayList<>();
        for (User user : allUsers) {
            if (user.getTeamId().equals(team)) {
                canBeAssignedUsers.add(user);
            }
        }
        return canBeAssignedUsers;
    }

    public List<Task> getAllTasksInGroup(long groupId) throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> sameGroupTasks = new ArrayList<>();
        for (Task task : taskDB.getAll()) {
            if (task.getGroupId() == groupId) {
                sameGroupTasks.add(task);
            }
        }
        return sameGroupTasks;
    }

//    public Long getNextTaskId() throws Exception{
//        TaskDB taskDB = new TaskDB();
//        Long taskId = taskDB.getNextTaskId();
//        return taskId;
//    }
    public Long insert(Task task) throws Exception {
        TaskDB taskDB = new TaskDB();
        return taskDB.insert(task);
    }

    public void update(Task task) throws Exception {
        TaskDB taskDB = new TaskDB();
        taskDB.update(task);
    }

    public void delete(Task task) throws Exception {
        TaskDB taskDB = new TaskDB();
        taskDB.delete(task);
    }

    public List<models.Task> getHotlineApprovedByUser(int userId) throws Exception {
        TaskDB taskDB = new TaskDB();
        List<models.Task> tasks = taskDB.getHotlineApprovedByUser(userId);
        return tasks;
    }

}
