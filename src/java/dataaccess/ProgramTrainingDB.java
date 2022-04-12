package dataaccess;

import jakarta.persistence.*;
import java.util.List;
import models.*;

public class ProgramTrainingDB {

    public List<ProgramTraining> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<ProgramTraining> programTrainingList = em.createNamedQuery("ProgramTraining.findAll", ProgramTraining.class).getResultList();
            return programTrainingList;
        } finally {
            em.close();
        }
    }
    
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
