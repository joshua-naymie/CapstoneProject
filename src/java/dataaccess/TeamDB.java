/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import models.Team;

/**
 *
 * @author srvad
 */
public class TeamDB {
     public Team get(int teamId) throws Exception {
      EntityManager em = DBUtil.getEMFactory().createEntityManager();
      try {
            Team t = em.find(Team.class, teamId);
            return t;
        } finally {
            em.close();
        }
    }
}
