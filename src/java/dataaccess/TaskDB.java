/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.*;
import java.util.List;
import models.*;

/**
 *
 * @author srvad
 */
public class TaskDB {
     public List<Task> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {         

            List<Task> allTasks = em.createNamedQuery("Task.findAll", Task.class).getResultList();
            return allTasks;
        } finally {
            em.close();
        }
    }
    
    /**
     * Gets all Tasks belonging to a specific User
     * @param userId The ID of the User
     * @return A list of tasks belonging to the User
     */
    public List<Task> getHistoryByUserId(long userId) throws Exception
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        
        try
        {
            TypedQuery query = entityManager.createNamedQuery("Task.findHistoryByUserId", Task.class);
            query.setParameter("userId", userId);
            
            return query.getResultList();
        }
        finally
        {
            entityManager.close();
        }
    }
    
    public List<Task> getSubmittedToManager(String managerId) throws Exception
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        
        try
        {
            TypedQuery query = entityManager.createNamedQuery("Task.findSubmittedToManger", Task.class);
            query.setParameter("approvingManager", managerId);
            
            return query.getResultList();
        }
        finally
        {
            entityManager.close();
        }
    }
     
    public List<Task> getAllNotApprovedTasks() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {   

            Query getTask = em.createNamedQuery("Task.findByIsApproved", Task.class);
            List<Task> allTasks = getTask.setParameter("isApproved", false).getResultList();
            return allTasks;
        } finally {
            em.close();
        }
    }

    public Task get(long id) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<Task> tasks = getAll();
            for (Task task : tasks) {
                if (task.getTaskId() == id) {
                    return task;
                }
            }
            return null;
        } finally {
            em.close();
        }
    }

     
    public void insert(Task task) throws Exception{
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(task);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void update(Task task) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(task);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}