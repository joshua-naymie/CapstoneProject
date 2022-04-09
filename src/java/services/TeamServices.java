/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dataaccess.TeamDB;
import java.util.List;
import models.Program;
import models.Store;
import models.Team;

/**
 *
 * @author srvad
 */
public class TeamServices {

    public Team get(int teamId) throws Exception {
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

    public String insert(short programId, short teamSize, int teamSup, int storeId, String teamName) throws Exception {
        try {
            // Getting program
            ProgramServices proService = new ProgramServices();
            Program teamsProgram = proService.get(programId);

            // Getting store
            StoreServices storeService = new StoreServices();
            Store teamsStore = storeService.get(storeId);

            // Creating team object and inserting it
            TeamDB teamDB = new TeamDB();

            // checking if store was selected for creation
            if (storeId != -1) {
                Team newTeamWithStore = new Team(teamsProgram, teamSize, teamSup, teamsStore, teamName);
                teamDB.insert(newTeamWithStore);
            } else {
                Team newTeamWithoutStore = new Team(teamsProgram, teamSize, teamSup, teamName);
                teamDB.insert(newTeamWithoutStore);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Team " + teamName
                + " ran into an error on creation";
        }
        return "Team " + teamName + " has been created";
    }

    public String update(int teamId, short programId, short teamSize, int teamSup, int storeId, String teamName) throws Exception {
        try {
            // Getting program
            ProgramServices proService = new ProgramServices();
            Program teamsProgram = proService.get(programId);

            // Getting store
            StoreServices storeService = new StoreServices();
            Store teamsStore = storeService.get(storeId);

            // Retrieve existing team
            TeamDB teamDB = new TeamDB();
            Team currentTeam = get(teamId);

            if (storeId != -1) {
                currentTeam.setProgramId(teamsProgram);

                currentTeam.setTeamSize(teamSize);
                currentTeam.setTeamSupervisor(teamSup);
                currentTeam.setStoreId(teamsStore);
                currentTeam.setTeamName(teamName);
            } else {
                currentTeam.setProgramId(teamsProgram);
                currentTeam.setTeamSize(teamSize);
                currentTeam.setTeamSupervisor(teamSup);
                currentTeam.setTeamName(teamName);
            }
            
            teamDB.update(currentTeam);

        } catch (Exception e) {
            e.printStackTrace();
            return "Team " + teamName
                + " ran into an error updating";
        }
        return "Team " + teamName
                + " has been updated";
    }
}
