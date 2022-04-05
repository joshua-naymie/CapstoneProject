/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.Store;
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

    public List<Team> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<Team> allTeams = em.createNamedQuery("Team.findAll", Team.class).getResultList();
            return allTeams;
        } finally {
            em.close();
        }
    }

    public List<Team> getTeamsByName(String teamName) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
             Query getFoundTeams = em.createQuery("SELECT t FROM Team t WHERE t.teamName LIKE :teamName", Team.class);
             // testing
//             System.out.println("got here");
//             System.out.println("%" + teamName + "%");
             getFoundTeams.setParameter("teamName", "%" + teamName + "%");
             // testing
//             System.out.println("got here 2");
             List<Team> foundTeams = getFoundTeams.getResultList();
             
            return foundTeams;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
