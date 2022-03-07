/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import models.Task;
import models.User;

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
