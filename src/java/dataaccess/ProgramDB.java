/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import dataaccess.DBUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import models.Program;
import models.ProgramTraining;

/**
 *data access class to perform CRUD operations on Program and ProgramTraining tables
 * @author 840979
 */
public class ProgramDB {
    
    /**
     * method to retrieve program id using program name
     * @param programName - string program name 
     * @return - short for program id 
     * @throws Exception 
     */
    public short getProgramId(String programName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
//            Program p = em.find(Program.class, programName);
            Query getprogram = em.createNamedQuery("Program.findByProgramName", Program.class);
            try {
                Program p = (Program) getprogram.setParameter("programName", programName).getSingleResult();
                return p.getProgramId();
            } catch (NoResultException e) {
                return 0;
            }

        } finally {
            em.close();
        }
    }

    /**
     * method to get a list of all Program objects that are currently in database
     * @return list of Program objects 
     * @throws Exception 
     */
    public List<Program> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {

            List<Program> allPrograms = em.createNamedQuery("Program.findAll", Program.class).getResultList();
            return allPrograms;
        } finally {
            em.close();
        }
    }
/**
 * method to retrieve Program object from Program table using program id(primary key)
 * @param programId - short passed as program id
 * @return Program object that matches passed program id 
 * @throws Exception 
 */
    public Program get(short programId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            Program p = em.find(Program.class, programId);
            return p;
        } finally {
            em.close();
        }
    }

    /**
     * method to retrieve Program object from Program table using program name
     * @param programName - passed string for program name
     * @return Program object matching passed program name 
     * @throws Exception 
     */
    public Program getByProgramName(String programName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
//            Program p = em.find(Program.class, programName);
            Query getprogram = em.createNamedQuery("Program.findByProgramName", Program.class);
            try {
                Program p = (Program) getprogram.setParameter("programName", programName).getSingleResult();
                return p;
            } catch (NoResultException e) {
                return null;
            }

        } finally {
            em.close();
        }
    }
    
    /**
     * method to persist new ProgramTraining object into ProgramTraining table 
     * @param programTraining new ProgramTraining object passed 
     * @throws Exception 
     */
    public void insertProgramTraining(ProgramTraining programTraining) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            programTraining.getUser().getProgramTrainingList().add(programTraining);

            programTraining.getProgram().getProgramTrainingList().add(programTraining);
            programTraining.getRoleId().getProgramTrainingList().add(programTraining);
            trans.begin();
            em.persist(programTraining);
            em.merge(programTraining.getProgram());
            em.merge(programTraining.getUser());
            em.merge(programTraining.getRoleId());
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    
    /**
     * method to update existing ProgramTraining object in ProgramTraining table 
     * @param programTraining object to be updated 
     * @throws Exception 
     */
    public void updateProgramTraining(ProgramTraining programTraining) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(programTraining);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
/**
 * method to persist new Program object into Program table 
 * @param program new program object passed 
 * @throws Exception 
 */
    public void insert(Program program) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(program);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
/**
 * method to update existing Program object in Program table
 * @param program Program object to be updated 
 * @throws Exception if object does not exist
 */
    public void update(Program program) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(program);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
/**
 * metho retrieve all ids of existing programs in Program table 
 * @return List of Ids from Program table 
 * @throws Exception 
 */
    public List<Short> getAllIDs() throws Exception
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        List<Short> ids = entityManager.createQuery("SELECT p.programId FROM Program p", Short.class).getResultList();

        return ids;
    }
    
    /**
     * method to retrieve all Program objects where isActive field is set to "true" 
     * @return list of all active programs 
     * @throws Exception 
     */
    public List<Program> getAllActive() throws Exception
    {
        EntityManager entityManager = DBUtil.getEMFactory().createEntityManager();
        TypedQuery<Program> query = entityManager.createNamedQuery("Program.findByIsActive", Program.class);
        query.setParameter("isActive", true);
        
        return query.getResultList();
    }
}
