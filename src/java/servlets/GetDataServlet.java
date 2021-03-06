package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.*;
import models.Store;
import models.Task;
import models.User;
import services.AccountServices;
import services.CompanyService;
import services.ProgramServices;
import services.StoreServices;
import services.TaskService;
import services.TeamServices;

/**
 *
 * Methods for ajax call to search for users, teams, stores, programs etc.
 */
public class GetDataServlet extends HttpServlet {
    
    /**
     *
     * Backend code for handling user inputs and returning the right results pulled from DB
     *
     * @param request Request object created by the web container for each
     * request of the client
     * @param response HTTP Response sent by a server to the client
     * @throws ServletException a general exception a servlet can throw when it encounters errors
     * @throws IOException Occurs when an IO operation fails
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CompanyService cs = new CompanyService();
        StoreServices ss = new StoreServices();
        TeamServices tms = new TeamServices();
        AccountServices as = new AccountServices();

        String op = request.getParameter("operation");

        // Get all stores for a certain company
        if (op.equals("store")) {
            String companyId = request.getParameter("companyId");

            List<Store> storelist = null;
            try {
                storelist = cs.get(Short.parseShort(companyId)).getStoreList();
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder storeJSON = new StringBuilder();
            storeJSON.append('[');
            if (storelist != null) {
                for (Store store : storelist) {
                    storeJSON.append('{');
                    storeJSON.append("\"store_name\":" + "\"" + store.getStoreName() + "\",");
                    storeJSON.append("\"store_id\":" + "\"" + store.getStoreId() + "\"");
                    storeJSON.append("},");
                }
            }
            if (storeJSON.length() > 2) {
                storeJSON.setLength(storeJSON.length() - 1);
            }

            storeJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(storeJSON.toString());
        }

        List<User> allSupervisors = null;

        // Get Supervisors for a program
        if (op.equals("program")) {
            String programAdd = (String) request.getParameter("programId");
            Short pogramAddId = Short.valueOf(programAdd);

            try {
                allSupervisors = as.getAllActiveSupervisorsByProgram(pogramAddId);

            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder programJSON = new StringBuilder();
            programJSON.append('[');

            if (allSupervisors != null) {

                for (User user : allSupervisors) {
                    programJSON.append('{');
                    programJSON
                            .append("\"user_name\":" + "\"" + user.getFirstName() + " " + user.getLastName() + "\",");
                    programJSON.append("\"user_id\":" + "\"" + user.getUserId() + "\"");
                    programJSON.append("},");
                }
            }

            if (programJSON.length() > 2) {
                programJSON.setLength(programJSON.length() - 1);
            }

            programJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(programJSON.toString());
        }

        // Get coordinators for a hotline program
        if (op.equals("hotlineCoordinators")) {

            List<User> allCoordinators = null;

            try {
                allCoordinators = as.getAllActiveHotlineCoordinators();

            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder programJSON = new StringBuilder();
            programJSON.append('[');

            if (allCoordinators != null) {

                for (User user : allCoordinators) {
                    programJSON.append('{');
                    programJSON
                            .append("\"user_name\":" + "\"" + user.getFirstName() + " " + user.getLastName() + "\",");
                    programJSON.append("\"user_id\":" + "\"" + user.getUserId() + "\"");
                    programJSON.append("},");
                }
            }

            if (programJSON.length() > 2) {
                programJSON.setLength(programJSON.length() - 1);
            }

            programJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(programJSON.toString());
        }

        // Get all the programs
        if (op.equals("allProgram")) {
            List<Program> programs = null;
            ProgramServices ps = new ProgramServices();

            try {
                programs = ps.getAll();

            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder programJSON = new StringBuilder();
            programJSON.append('[');

            if (programs != null) {
                for (Program program : programs) {
                    programJSON.append('{');
                    programJSON.append("\"program_name\":" + "\"" + program.getProgramName() + "\",");
                    programJSON.append("\"program_id\":" + "\"" + program.getProgramId() + "\"");
                    programJSON.append("},");
                }
            }

            if (programJSON.length() > 2) {
                programJSON.setLength(programJSON.length() - 1);
            }

            programJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(programJSON.toString());
        }

        // Get detailed information on a specific task
        if (op.equals("singleTaskInfo")) {
            Task task = null;
            TaskService ts = new TaskService();

            String task_id = request.getParameter("task_id");
            Long taskId = Long.parseLong(task_id);
	    User approvingManager = null;

            try {
                task = ts.get(taskId);
		approvingManager = as.getByID(task.getApprovingManager());

            } catch (Exception ex) {
                Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder taskJSON = new StringBuilder();

            if (task != null) {
                Date startDate = task.getStartTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(startDate);
                simpleDateFormat = new SimpleDateFormat("HH:mm");
                String startTime = simpleDateFormat.format(startDate);
//				Date endDate = task.getEndTime();
//				String endTime = simpleDateFormat.format(endDate);
		String approvingManagerString = approvingManager.getFirstName() + " " + approvingManager.getLastName();

                taskJSON.append('{');
                taskJSON.append("\"task_id\":" + "\"" + task.getTaskId() + "\",");
                taskJSON.append("\"task_description\":" + "\"" + task.getTaskDescription() + "\",");
                taskJSON.append("\"program_name\":" + "\"" + task.getProgramId().getProgramName() + "\",");
                taskJSON.append("\"task_city\":" + "\"" + task.getTaskCity() + "\",");
                taskJSON.append("\"start_time\":" + "\"" + task.getStartTime() + "\",");
                taskJSON.append("\"end_time\":" + "\"" + task.getEndTime() + "\",");
                taskJSON.append("\"approving_manager\":" + "\"" + approvingManagerString + "\",");
                taskJSON.append("\"spots_taken\":" + "\"" + task.getSpotsTaken() + "\",");
                taskJSON.append("\"max_users\":" + "\"" + task.getMaxUsers() + "\"");
                taskJSON.append("},");

                if (taskJSON.length() > 2) {
                    taskJSON.setLength(taskJSON.length() - 1);
                }

                response.setContentType("text/html");
                response.getWriter().write(taskJSON.toString());
            }
        }

        if (op.equals("cancelTask")) {

        }

        // Get all the stores
        if (op.equals("storeAll")) {

            List<Store> storelist = null;
            try {
                storelist = ss.getAll();
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder storeJSON = new StringBuilder();
            storeJSON.append('[');
            if (storelist != null) {
                for (Store store : storelist) {
                    storeJSON.append('{');
                    storeJSON.append("\"store_name\":" + "\"" + store.getStoreName() + "\",");
                    storeJSON.append("\"store_id\":" + "\"" + store.getStoreId() + "\"");
                    storeJSON.append("},");
                }
            }
            if (storeJSON.length() > 2) {
                storeJSON.setLength(storeJSON.length() - 1);
            }

            storeJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(storeJSON.toString());
        }

        // Get all the teams
        if (op.equals("teamAll")) {

            List<Team> teamlist = null;
            try {
                teamlist = tms.getAll();
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder storeJSON = new StringBuilder();
            storeJSON.append('[');
            if (teamlist != null) {
                for (Team team : teamlist) {
                    storeJSON.append('{');
                    storeJSON.append("\"team_supervisor\":" + "\"" + team.getTeamSupervisor() + "\",");
                    storeJSON.append("\"team_id\":" + "\"" + team.getTeamId() + "\"");
                    storeJSON.append("},");
                }
            }
            if (storeJSON.length() > 2) {
                storeJSON.setLength(storeJSON.length() - 1);
            }

            storeJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(storeJSON.toString());
        }

        // Get a list of users based on their first and last name
        if (op.equals("findUser")) {
            String name = request.getParameter("name");

            List<User> userlist = null;
            try {
                userlist = as.getUsersByFullName(name, name);
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringBuilder storeJSON = new StringBuilder();
            storeJSON.append('[');
            if (userlist != null) {
                for (User user : userlist) {
                    storeJSON.append('{');
                    storeJSON.append("\"user_name\":" + "\"" + user.getFirstName() + " " + user.getLastName() + "\",");
                    storeJSON.append("\"user_id\":" + "\"" + user.getUserId() + "\"");
                    storeJSON.append("},");
                }
            }
            if (storeJSON.length() > 2) {
                storeJSON.setLength(storeJSON.length() - 1);
            }

            storeJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(storeJSON.toString());
        }

        // Find a team from team name
        if (op.equals("findTeam")) {
            String teamName = request.getParameter("teamName");

            List<Team> teamList = null;
            try {
                teamList = tms.getTeamByName(teamName);
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.WARNING, null, ex);
            }

            StringBuilder teamJSON = new StringBuilder();
            teamJSON.append('[');
            if (teamList != null) {
                for (Team lookForTeam : teamList) {
                    teamJSON.append('{');
                    teamJSON.append("\"team_name\":" + "\"" + lookForTeam.getTeamName() + "\",");
                    teamJSON.append("\"team_id\":" + "\"" + lookForTeam.getTeamId() + "\"");
                    teamJSON.append("},");
                }
            }
            if (teamJSON.length() > 2) {
                teamJSON.setLength(teamJSON.length() - 1);
            }

            teamJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(teamJSON.toString());
        }

        // Find a stpre from store name
        if (op.equals("findStore")) {
            String storeName = request.getParameter("storeName");

            List<Store> storeList = null;
            try {
                storeList = ss.getStoresByName(storeName);
            } catch (Exception ex) {
                Logger.getLogger(GetDataServlet.class.getName()).log(Level.WARNING, null, ex);
            }

            StringBuilder storeJSON = new StringBuilder();
            storeJSON.append('[');
            if (storeList != null) {
                for (Store store : storeList) {
                    storeJSON.append('{');
                    storeJSON.append("\"store_name\":" + "\"" + store.getStoreName() + "\",");
                    storeJSON.append("\"store_id\":" + "\"" + store.getStoreId() + "\"");
                    storeJSON.append("},");
                }
            }
            if (storeJSON.length() > 2) {
                storeJSON.setLength(storeJSON.length() - 1);
            }

            storeJSON.append(']');

            response.setContentType("text/html");
            response.getWriter().write(storeJSON.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
