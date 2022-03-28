/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.TeamDB;
import java.util.List;
import models.Team;



/**
 *
 * @author srvad
 */
public class TeamServices {
    public Team get (int teamId)throws Exception{
        TeamDB teamDB = new TeamDB();
        Team team = teamDB.get(teamId);
        return team;
    }
    
        public List<Team> getAll() throws Exception {
        TeamDB teamDB = new TeamDB();
        List<Team> teams = teamDB.getAll();
        return teams;

    }
    
    public List<Team> getTeamByName(String teamName) throws Exception {
        TeamDB teamDB = new TeamDB();
        List<Team> teams = teamDB.getTeamsByName(teamName);
        return teams;
    }
        
}
