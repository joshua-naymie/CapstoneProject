package dataaccess;

import jakarta.persistence.*;
import java.util.List;
import models.*;

/**
 * class for retrieving ProgramTraining objects from ProgramTraining table 
 * @author 840979
 */
public class ProgramTrainingDB {
/**
 * method to get all existing ProgramTraining records from ProgramTraining table 
 * @return List of ProgramTraining objects 
 * @throws Exception 
 */
    public List<ProgramTraining> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<ProgramTraining> programTrainingList = em.createNamedQuery("ProgramTraining.findAll", ProgramTraining.class).getResultList();
            return programTrainingList;
        } finally {
            em.close();
        }
    }
    /**
     * method to retrieve ProgramTraining object using user id and program id
     * @param userId user id 
     * @param programId program id 
     * @return ProgramTraining object that matches both user id and program id
     * @throws Exception 
     */
    public ProgramTraining getProgramTraining(int userId, short programId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        Query q = em.createQuery("SELECT p FROM ProgramTraining p WHERE p.programTrainingPK.userId = :userId AND p.programTrainingPK.programId = :programId", ProgramTraining.class);
        q.setParameter("userId", userId);
        q.setParameter("programId", programId);
        try {
            ProgramTraining foundUser = (ProgramTraining) q.getSingleResult();
            return foundUser;
        } finally {
            em.close();
        }
    }
    
}
