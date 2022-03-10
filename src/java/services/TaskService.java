/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.TaskDB;
import java.util.List;
import models.Task;

/**
 *
 * @author srvad
 */
public class TaskService {
    public List<Task> getAll() throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> tasks = taskDB.getAll();
        return tasks;
    }

    public Task get(long id) throws Exception {
        TaskDB taskDB = new TaskDB();
        Task task = taskDB.get(id);
        return task;
    }
    
    /**
     * Gets 
     * @param id
     * @return
     * @throws Exception 
     */
    public List<Task> getUserHistory(long id) throws Exception
    {
        TaskDB taskDB = new TaskDB();
        return taskDB.getHistoryByUserId(id);
    }
    
    public List<Task> getSubmittedToManager(String id) throws Exception
    {
        TaskDB taskDB = new TaskDB();
        return taskDB.getSubmittedToManager(id);
    }

    public List<Task> getAllNotApprovedTasks() throws Exception {
        TaskDB taskDB = new TaskDB();
        List<Task> tasks = taskDB.getAllNotApprovedTasks();
        return tasks;
    }
    
    public void insert(Task task) throws Exception {
        TaskDB taskDB  = new TaskDB();
        taskDB.insert(task);
    }

    public void update(Task task) throws Exception {
        TaskDB taskDB  = new TaskDB();
        taskDB.update(task);
    }
}
