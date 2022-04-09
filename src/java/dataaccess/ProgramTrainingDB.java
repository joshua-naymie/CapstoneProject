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
}
