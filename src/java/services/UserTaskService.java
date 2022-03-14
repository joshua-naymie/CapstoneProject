package services;

import dataaccess.UserTaskDB;
import models.*;

import java.util.List;

public class UserTaskService {
    public List<User> getChosenUsers(long taskId) throws Exception {
        UserTaskDB userTaskDB = new UserTaskDB();
        return userTaskDB.getChosenUsers(taskId);
    }

    public List<User> getAssignedUsers(long taskId) throws Exception {
        UserTaskDB userTaskDB = new UserTaskDB();
        return userTaskDB.getAssignedUsers(taskId);
    }

    public void insert(UserTask userTask) throws Exception {
        UserTaskDB userTaskDB = new UserTaskDB();
        userTaskDB.insert(userTask);
    }

    public void update(UserTask userTask) throws Exception {
        UserTaskDB userTaskDB = new UserTaskDB();
        userTaskDB.update(userTask);
    }
}
