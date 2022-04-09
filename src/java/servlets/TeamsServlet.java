/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.List;
import java.util.logging.*;
import models.*;
import models.util.*;
import services.*;

/**
 *
 * @author 861349
 */
public class TeamsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //services needed to retrieve users, teams and stores
        AccountServices as = new AccountServices();
        List<User> supervisorUsers = null;
        //stores
        StoreServices ss = new StoreServices();
        List<Store> allStores = null;
        //teams
        TeamServices ts = new TeamServices();
        List<Team> allTeams = null;
        
        // ==============================================================================
        // sending up all supervisor users to the front end
        try
        {
            // get all supervisor users
            supervisorUsers = as.getAllActiveSupervisorsOrManagers("Supervisor");
        }
        catch(Exception ex)
        {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        request.setAttribute("supervisorData", supervisorUsers);

        // sending Json data of all supervisor user info to the front end
        StringBuilder supervisorData = new StringBuilder();
        supervisorData.append("var supervisorData = [");

        // Create keys
        JSONKey[] userKeys = { new JSONKey("id", false),
                               new JSONKey("firstName", true),
                               new JSONKey("lastName", true) };

        // Create builder with above keys
        JSONBuilder builder = new JSONBuilder(userKeys);

        // Create user JSON objects
        if(supervisorUsers.size() > 0) {
            int i;
            for (i = 0; i < supervisorUsers.size() - 1; i++) {
                supervisorData.append(buildUserJSON(supervisorUsers.get(i), builder));
                supervisorData.append(',');
            }
            supervisorData.append(buildUserJSON(supervisorUsers.get(i), builder));
        }
        supervisorData.append("];");

        request.setAttribute("supervisorData", supervisorData);

        // ==============================================================================
        // sending up a list of all stores
        try {
            // get all stores
            allStores = ss.getAll();
        } catch (Exception ex) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }

        // sending Json data of all stores info to the front end
        StringBuilder storeData = new StringBuilder();
        storeData.append("var storeData = [");

        // Create keys
        JSONKey[] storeKeys = { new JSONKey("id", false),
                                new JSONKey("name", true),
                                new JSONKey("address", true) };

        // Create builder with above keys
        JSONBuilder storeBuilder = new JSONBuilder(storeKeys);

        // Create store JSON objects
        if (allStores.size() > 0) {
            int i;
            for (i = 0; i < allStores.size() - 1; i++) {
                storeData.append(buildStoreJSON(allStores.get(i), storeBuilder));
                storeData.append(',');
            }
            storeData.append(buildStoreJSON(allStores.get(i), storeBuilder));
        }
        storeData.append("];");

        request.setAttribute("storeData", storeData);
        
        // ==============================================================================
        // sending up a list of all teams
        try {
            // get all teams
            allTeams = ts.getAll();
        } catch (Exception ex) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }

        // sending Json data of all stores info to the front end
        StringBuilder teamData = new StringBuilder();
        teamData.append("var teamData = [");

        // Create keys
        JSONKey[] teamKeys = { new JSONKey("id", false),
                               new JSONKey("name", true),
                               new JSONKey("supervisorID", false),
                               new JSONKey("maxSize", false),
                               new JSONKey("programID", false),
                               new JSONKey("storeID", false) };

        // Create builder with above keys
        JSONBuilder teamBuilder = new JSONBuilder(teamKeys);

        // Create team JSON objects
        if (allTeams.size() > 0) {
            int i;
            for (i = 0; i < allTeams.size() - 1; i++) {
                teamData.append(buildTeamJSON(allTeams.get(i), teamBuilder));
                teamData.append(',');
            }
            teamData.append(buildTeamJSON(allTeams.get(i), teamBuilder));
        }
        teamData.append("];");

        request.setAttribute("teamData", teamData);
        
        // ==============================================================================
        
        StringBuilder programData = new StringBuilder();
        programData.append("var programData = [");
        
        JSONKey[] programKeys = { new JSONKey("id", false),
                                  new JSONKey("name", true) };
        
        JSONBuilder programBuilder = new JSONBuilder(programKeys);
        try
        {
            ProgramServices programService = new ProgramServices();
            
            List<Program> programs = programService.getAllActive();
            
            if(!programs.isEmpty())
            {
                int i;
                for(i=0; i<programs.size()-1; i++)
                {
                    programData.append(buildProgramJSON(programs.get(i), programBuilder));
                    programData.append(',');
                }
                programData.append(buildProgramJSON(programs.get(i), programBuilder));
            }
            
            programData.append("];");
            
            request.setAttribute("programData", programData);
        }
        catch(Exception ex)
        {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        
        
        // fill in with the jsp you create 
        getServletContext().getRequestDispatcher("/WEB-INF/teams.jsp").forward(request, response);
    }

    /**
     * Creates a supervisor user JSON object
     *
     * @param user The supervisor User to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A User JSON as a String
     */
    private String buildUserJSON(User user, JSONBuilder builder) {
        Object[] data = { user.getUserId(),
                          user.getFirstName(),
                          user.getLastName() };

        return builder.buildJSON(data);
    }
    
    /**
     * Creates a store JSON object
     *
     * @param store The store to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A store JSON as a String
     */
    private String buildStoreJSON(Store store, JSONBuilder builder) {
        Object[] data = { store.getStoreId(),
                          store.getStoreName(),
                          store.getStreetAddress() };

        return builder.buildJSON(data);
    }
    
    /**
     * Creates a team JSON object
     *
     * @param team The team to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A team JSON as a String
     */
    private String buildTeamJSON(Team team, JSONBuilder builder)
    {
        int supervisorID = team.getTeamSupervisor() == null ? -1 : team.getTeamSupervisor();

        System.out.println("NAME: " + team.getTeamName());
        Object[] data = { team.getTeamId(),
                          team.getTeamName(),
                          team.getTeamSupervisor(),
                          team.getTeamSize(),
                          team.getProgramId().getProgramId(),
                          supervisorID };


        return builder.buildJSON(data);
    }
    
    /**
     * Creates a program JSON object
     *
     * @param team The program to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A program JSON as a String
     */
    private String buildProgramJSON(Program program, JSONBuilder builder)
    {
        Object[] data = { program.getProgramId(),
                          program.getProgramName() };

        return builder.buildJSON(data);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // getting user action from JSP
        String action = request.getParameter("action");
        // switch to determine users chosen action
        try {
            switch (action) {
                // add new program
                case "add":
                    add(request, response);
                    break;
                // save edit changes
                case "update":
                    save(request, response);
                    break;
                // throw exception if the action is none of the above    
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        // team service
        TeamServices tmService = new TeamServices();
       
        // getting user entered values and insert new team
        try {
            // creating the yteam through team services
            String userMsg = tmService.insert(Short.parseShort(request.getParameter("programId")),
                    // team size
                    Short.parseShort(request.getParameter("teamSize")),
                    // supervisor Id
                    Integer.parseInt(request.getParameter("teamSupervisor")),
                    // store id (if theres no store selected send -1?)
                    Integer.parseInt(request.getParameter("storeId")),
                    //teamName
                    request.getParameter("teamName"));

            response.sendRedirect("teams");
        } catch (Exception e) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        // team service
        TeamServices tmService = new TeamServices();
       
        // getting the edited values and updating the team
        try {
            // updating the team
            String userMsg = tmService.update(Integer.parseInt(request.getParameter("teamId")),
                    // program Id
                    Short.parseShort(request.getParameter("programId")),
                    // team size
                    Short.parseShort(request.getParameter("teamSize")),
                    // supervisor Id
                    Integer.parseInt(request.getParameter("teamSupervisor")),
                    // store id (if theres no store selected send -1?)
                    Integer.parseInt(request.getParameter("storeId")),
                    //teamName
                    request.getParameter("teamName"));

            response.sendRedirect("teams");
        } catch (Exception e) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }
}
