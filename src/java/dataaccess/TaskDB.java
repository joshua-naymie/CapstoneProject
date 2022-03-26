/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import models.*;
import org.eclipse.persistence.sessions.Session;

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
    public List<Task> getHistoryByUserId(long userId, LocalDateTime startDate, LocalDateTime endDate,
                                         Short[] programs) throws Exception
    {
        StringBuilder queryBuilder = new StringBuilder();
        startDate = startDate == null ? LocalDateTime.now() : startDate;
        
        queryBuilder.append("SELECT t FROM Task t, UserTask ut");
        queryBuilder.append(" WHERE t.taskId = ut.userTaskPK.taskId");
        queryBuilder.append(" AND ut.userTaskPK.userId = :userId ");
        queryBuilder.append(" AND t.startTime <= ");
        queryBuilder.append(startDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        
        if(endDate != null)
        {
            queryBuilder.append(" AND t.endTime >= ");
            queryBuilder.append(endDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
        }
        
        if(programs != null && programs.length > 0)
        {
            queryBuilder.append(" AND t.programId IN (");
            int i;
            for(i=0; i<programs.length-1; i++)
            {
                queryBuilder.append(programs[i]);
                queryBuilder.append(',');
            }
            queryBuilder.append(programs[i]);
            
            queryBuilder.append(')');
        }
        
        return  DBUtil
                .getEMFactory()
                .createEntityManager()
                .createQuery(queryBuilder.toString(), Task.class)
                .setParameter("userId", userId)
                .getResultList();
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
     
    public List<Task> getAllNotApprovedTasksByUserId(int userId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();

        try {   
            Query q = em.createQuery("SELECT t FROM Task t, User u "
            + "WHERE u.userId = :userId "
            + "AND t.isApproved = FALSE AND t.isSubmitted = FALSE AND t.assigned = TRUE");

            q.setParameter("userId", userId);
            
            List<Task> allTasks = q.getResultList();
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
    
//    public Long getNextTaskId() throws Exception{
//      
//        EntityManager em = DBUtil.getEMFactory().createEntityManager();
//        
////        Long taskId = em.unwrap(Session.class).getNextSequenceNumberValue(Task.class).longValue();
////        
////        return taskId;
//
////        try {   
////            
////            Query q = em.createQuery("SELECT AUTO_INCREMENT\n" +
////"FROM information_schema.tables\n" +
////"WHERE table_name = :databaseTable\n" +
////"AND table_schema = DATABASE( )");
////            
////            q.setParameter("databaseTable", "task");
////            
////            Long taskId = (Long) q.getSingleResult();
////            return taskId;
////
////        } finally {
////            em.close();
////        }
//
//        try {   
//            
//            Query q = em.createQuery("SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = :databaseName AND TABLE_NAME = :databaseTable");
//            
//            q.setParameter("databaseName", "ecssendb");
//            q.setParameter("databaseTable", "task");
//            
//            Long taskId = (Long) q.getSingleResult();
//            return taskId;
//
//        } finally {
//            em.close();
//        }
//    }

     
    public Long insert(Task task) throws Exception{
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        Long taskId = -1L;
        
        try {
            trans.begin();
            em.persist(task);
            trans.commit();  
            taskId = task.getTaskId();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            //em.flush();
            em.close();
            return taskId;  
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