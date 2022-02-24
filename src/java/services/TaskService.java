/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.TaskDB;
import dataaccess.UserDB;
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
}
