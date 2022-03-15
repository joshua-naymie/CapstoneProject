package dataaccess;

import jakarta.persistence.*;
import models.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserTaskDB {
    public List<User> getChosenUsers(long taskId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            TypedQuery query1 = em.createNamedQuery("UserTask.findByTaskId", UserTask.class);
            query1.setParameter("taskId", taskId);
            List<UserTask> allUsersWithTask = query1.getResultList();

            TypedQuery query2 = em.createNamedQuery("UserTask.findByIsChosen", UserTask.class);
            query2.setParameter("isChosen", true);
            List<UserTask> allUsersChosen = query2.getResultList();

            List<UserTask> allUsersTaskChosen = null;
//allUsersWithTask.stream().filter(allUsersChosen::contains).collect(Collectors.toList());
            List<User> chosenUsers = null;
            for (UserTask userTask : allUsersTaskChosen) {
                chosenUsers.add(userTask.getUser());
            }
            return chosenUsers;
        } finally {
            em.close();
        }
    }

    public List<User> getAssignedUsers(long taskId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            TypedQuery query1 = em.createNamedQuery("UserTask.findByTaskId", UserTask.class);
            query1.setParameter("taskId", taskId);
            List<UserTask> allUsersWithTask = query1.getResultList();

            TypedQuery query2 = em.createNamedQuery("UserTask.findByIsAssigned", UserTask.class);
            query2.setParameter("isAssigned", true);
            List<UserTask> allUsersAssigned = query2.getResultList();

            List<UserTask> allUsersTaskAssigned = null;
//allUsersWithTask.stream().filter(allUsersAssigned::contains).collect(Collectors.toList());
            List<User> assignedUsers = null;
            for (UserTask userTask : allUsersTaskAssigned) {
                assignedUsers.add(userTask.getUser());
            }
            return assignedUsers;
        } finally {
            em.close();
        }
    }

    public void insert(UserTask userTask) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(userTask);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void update(UserTask userTask) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(userTask);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
