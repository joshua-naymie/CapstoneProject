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
import java.util.List;
import models.Program;

/**
 *
 * @author 840979
 */
public class ProgramDB {
    
    public short getProgramId(String programName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
//            Program p = em.find(Program.class, programName);
            Query getprogram = em.createNamedQuery("Program.findByNames", Program.class);
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

    public List<Program> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {

            List<Program> allPrograms = em.createNamedQuery("Program.findAll", Program.class).getResultList();
            return allPrograms;
        } finally {
            em.close();
        }
    }

    public Program get(short programId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            Program p = em.find(Program.class, programId);
            return p;
        } finally {
            em.close();
        }
    }

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
}
