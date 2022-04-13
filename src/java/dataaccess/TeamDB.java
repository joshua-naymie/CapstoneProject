/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import models.Store;
import models.Team;

/**
 *class to perform some of the CRUD operations on Team table 
 * @author srvad
 */
public class TeamDB {
/**
 * method to retrieve Team record using team id (primary key) 
 * @param teamId team id 
 * @return Team object that matches team id
 * @throws Exception 
 */
    public Team get(int teamId) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            Team t = em.find(Team.class, teamId);
            return t;
        } finally {
            em.close();
        }
    }
/**
 * method to retrieve existing records from Team table 
 * @return List of Team objects 
 * @throws Exception 
 */
    public List<Team> getAll() throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        try {
            List<Team> allTeams = em.createNamedQuery("Team.findAll", Team.class).getResultList();
            return allTeams;
        } finally {
            em.close();
        }
    }

    /**
     * method to retrieve Team records by team name
     * @param teamName team name
     * @return List of Team objects that match team name 
     * @throws Exception 
     */
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
/**
 * method to persist new Team object into Team table 
 * @param newTeam - new Team object to be inserted 
 * @throws Exception 
 */
    public void insert(Team newTeam) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            // started a new transaction
            trans.begin();
            // telling the entity manager to add the team
            em.persist(newTeam);
            // update the team
            em.merge(newTeam);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
    /**
     * method to update existing Team record in Team table 
     * @param updateTeam - Team object to be updated 
     * @throws Exception 
     */
    public void update(Team updateTeam) throws Exception {
        EntityManager em = DBUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
            // started a new transaction
            trans.begin();
            // update the team
            em.merge(updateTeam);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
