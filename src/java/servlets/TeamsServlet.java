/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Store;
import models.Team;
import models.User;
import models.util.JSONBuilder;
import models.util.JSONKey;
import services.AccountServices;
import services.StoreServices;
import services.TeamServices;

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
        try {
            // get all supervisor users
            supervisorUsers = as.getAllActiveSupervisorsOrManagers("Supervisor");
        } catch (Exception ex) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        request.setAttribute("supervisorUsers", supervisorUsers);

        // sending Json data of all supervisor user info to the front end
        StringBuilder supervisorData = new StringBuilder();
        supervisorData.append("var data = [");

        // Create keys
        JSONKey[] userKeys = {new JSONKey("userID", false),
            new JSONKey("firstName", true),
            new JSONKey("lastName", true)};

        // Create builder with above keys
        JSONBuilder builder = new JSONBuilder(userKeys);

        // Create user JSON objects
        if (supervisorUsers.size() > 0) {
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
        request.setAttribute("allStores", allStores);

        // sending Json data of all stores info to the front end
        StringBuilder allStoresData = new StringBuilder();
        allStoresData.append("var data = [");

        // Create keys
        JSONKey[] storeKeys = {new JSONKey("storeID", false),
            new JSONKey("storeName", true),
            new JSONKey("storeAddress", true)};

        // Create builder with above keys
        JSONBuilder storeBuilder = new JSONBuilder(storeKeys);

        // Create store JSON objects
        if (allStores.size() > 0) {
            int i;
            for (i = 0; i < allStores.size() - 1; i++) {
                allStoresData.append(buildStoreJSON(allStores.get(i), storeBuilder));
                allStoresData.append(',');
            }
            allStoresData.append(buildStoreJSON(allStores.get(i), storeBuilder));
        }
        allStoresData.append("];");

        request.setAttribute("allStoresData", allStoresData);
        
        // ==============================================================================
        // sending up a list of all teams
        try {
            // get all teams
            allTeams = ts.getAll();
        } catch (Exception ex) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        request.setAttribute("allTeams", allTeams);

        // sending Json data of all stores info to the front end
        StringBuilder allTeamsData = new StringBuilder();
        allTeamsData.append("var data = [");

        // Create keys
        JSONKey[] teamKeys = {new JSONKey("teamID", false),
            new JSONKey("teamName", true),
            new JSONKey("teamSupervisor", true)};

        // Create builder with above keys
        JSONBuilder teamBuilder = new JSONBuilder(teamKeys);

        // Create team JSON objects
        if (allTeams.size() > 0) {
            int i;
            for (i = 0; i < allTeams.size() - 1; i++) {
                allTeamsData.append(buildTeamJSON(allTeams.get(i), teamBuilder));
                allTeamsData.append(',');
            }
            allTeamsData.append(buildTeamJSON(allTeams.get(i), teamBuilder));
        }
        allTeamsData.append("];");

        request.setAttribute("allTeamsData", allTeamsData);
        
        // fill in with the jsp you create 
        //getServletContext().getRequestDispatcher("/WEB-INF/userlist.jsp").forward(request, response);
    }

    /**
     * Creates a supervisor user JSON object
     *
     * @param user The supervisor User to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A User JSON as a String
     */
    private String buildUserJSON(User user, JSONBuilder builder) {
        Object[] userValues = {user.getUserId(),
            user.getFirstName(),
            user.getLastName()};

        return builder.buildJSON(userValues);
    }
    
    /**
     * Creates a store JSON object
     *
     * @param store The store to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A store JSON as a String
     */
    private String buildStoreJSON(Store store, JSONBuilder builder) {
        Object[] userValues = {store.getStoreId(),
            store.getStoreName(),
            store.getStreetAddress()};

        return builder.buildJSON(userValues);
    }
    
    /**
     * Creates a team JSON object
     *
     * @param team The team to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A team JSON as a String
     */
    private String buildTeamJSON(Team team, JSONBuilder builder) {
        
        // retrieving supervisor name
        AccountServices as = new AccountServices();
        int supervisorID = team.getTeamSupervisor();
        User supervisorUser = null;
        try{
            supervisorUser = as.getByID(supervisorID);
        }catch (Exception ex) {
            Logger.getLogger(TeamsServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        
        Object[] userValues = {team.getTeamId(),
            team.getTeamName(),
            supervisorUser.getFirstName() + " " + supervisorUser.getLastName()};

        return builder.buildJSON(userValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    

}
