/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
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
            Query getFoundStores = em.createNamedQuery("Store.findByStoreName", models.Store.class);
            List<Store> foundStores = getFoundStores.setParameter("storeName", teamName).getResultList();

            List<Team> allTeams = null;
            for (Store store : foundStores) {
                for (Team team : store.getTeamList()) {
                    allTeams.add(team);
                }
            }
            
            return allTeams;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
