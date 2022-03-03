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
import models.JSONBuilder;
import models.JSONKey;
import models.Program;
import models.ProgramTraining;
import models.User;
import services.AccountServices;
import services.ProgramServices;

/**
 *
 * @author 861349
 */
public class ProgramServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // getting a list of all users
        AccountServices as = new AccountServices();
        List<User> allUsers = null;
        try {
            allUsers = as.getAllActive();  //get a list of all users
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // getting a list of all programs
        ProgramServices ps = new ProgramServices();
        List<Program> allPrograms = null;
        try {
            //get a list of all programs
            allPrograms = ps.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        //request.setAttribute("programs", allPrograms);

        // sending jason data
        StringBuilder returnData = new StringBuilder();
        returnData.append("var data = [");

        // Create keys
        JSONKey[] keys = {new JSONKey("programId", false),
            new JSONKey("program", true),
            new JSONKey("userId", true),
            new JSONKey("active", false)};

        // Create builder with above keys
        JSONBuilder builder = new JSONBuilder(keys);

        // Create user JSON objects
        if (allPrograms.size() > 0) {
            int i;
            for (i = 0; i < allPrograms.size() - 1; i++) {
                returnData.append(buildProgramJSON(allPrograms.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildProgramJSON(allPrograms.get(i), builder));
        }
        returnData.append("];");

        // setting program data attribute for the front end to use
        request.setAttribute("programData", returnData);

        // sending all active user full names
        StringBuilder userReturnData = new StringBuilder();
        userReturnData.append("var userData = [");
        // Create keys
        // send email as well
        JSONKey[] userKeys = {new JSONKey("ID", false),
                              new JSONKey("name", true),
                              new JSONKey("email", true)};

        // Create builder with above keys
        JSONBuilder userBuilder = new JSONBuilder(userKeys);

        // Create user JSON objects
        if (allUsers.size() > 0) {
            int i;
            for (i = 0; i < allUsers.size() - 1; i++) {
                userReturnData.append(buildUserJSON(allUsers.get(i), userBuilder));
                userReturnData.append(',');
            }
            userReturnData.append(buildUserJSON(allUsers.get(i), userBuilder));
        }
        userReturnData.append("];");

        // setting user data attribute for the front end to use
        request.setAttribute("userData", userReturnData);

        // forwards data to jsp
        getServletContext().getRequestDispatcher("/WEB-INF/program.jsp").forward(request, response);

    }

    /**
     * Creates a user JSON object
     *
     * @param user The User to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A User JSON as a String
     */
    private String buildUserJSON(User user, JSONBuilder builder) {
        Object[] userValues = {user.getUserId(),
                               user.getFirstName() + " " + user.getLastName(),
                               user.getEmail()};

        return builder.buildJSON(userValues);
    }

    /**
     * Creates a program JSON object
     *
     * @param program The program to populate the JSON data
     * @param builder The JSONBuilder to create the JSON with
     * @return A program JSON as a string
     */
    private String buildProgramJSON(Program program, JSONBuilder builder) {
        // retrieving program values into an array
        Object[] programValues = {program.getProgramId(),
            program.getProgramName(),
            program.getUserId(),
            program.getIsActive()};

        return builder.buildJSON(programValues);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // getting user action from JSP
        String action = request.getParameter("action");
        System.out.println(action);
        // switch to determine users chosen action
        try {
            switch (action) {
                // add new program
                case "add":
                    add(request, response);
                    break;
                // edit current programs
                // change status of program  
                case "Edit":
                    edit(request, response);
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
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        // program services to have data access
        ProgramServices proService = new ProgramServices();

        // user services to create program_training data
        AccountServices accService = new AccountServices();

        // getting user entered values and insert new program
        try {
            // getting program status
            String status = request.getParameter("status");
            boolean isActive = status.equals("active");

            // programs are always active on creation
            String userMsg = proService.insert(isActive,
                    // obtaining user entered program name
                    request.getParameter("program-name"),
                    // obtaining user entered manager name
                    request.getParameter("manager-name"));

            // change the role of the manager name typed to the matching program role
            // get user entered user name (match with frontend)
//            String userName = request.getParameter("userName");
//            
//            // put the first and last in an array
//            String[] names = userName.split("\\s+");
//            
//            //split into first and last name
//            String firstName = names[0];
//            String lastName = names[1];
//            
//            // retrieve the user with the matching name
//            User updateRole = accService.getUserByFullName(firstName, lastName);
            // get user entered user name (match with frontend)  
            int userId = Integer.parseInt(request.getParameter("userID"));

            // retrieve the user with the matching ID
            User updateRole = accService.getByID(userId);

            // get current programs the user entered is linked to
            List<ProgramTraining> currentRoles = updateRole.getProgramTrainingList();

            // get newly created programId
            short programId = proService.getProgramId(request.getParameter("program-name"), request.getParameter("manager-name"));

            // get roleId, fully implement when theres a page
            short roleId = 1;

            // if current user is not a manager change their role to manager
            if (currentRoles == null) {
                // get manager and programId to match
                ProgramTraining roleAdd = new ProgramTraining(userId, roleId, programId);
            } else {
                for (ProgramTraining pt : currentRoles) {
                    if((pt.getProgram().getProgramId() == programId)&& 
                            (pt.getUser().getUserId() == userId)){
                        // to be implemented
                        // pt.setRole(role);
                    }
                }
            }

            response.sendRedirect("programs");
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }

    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        // program services to have data access
        ProgramServices proService = new ProgramServices();

        try {
            // getting the program that is to be edited
            Program editProgram = proService.get(Short.parseShort(request.getParameter("programID")));

            // setting program attribute to edit
            request.setAttribute("editProgram", editProgram);
            getServletContext().getRequestDispatcher("/WEB-INF/program.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        // program services to have data access
        ProgramServices proService = new ProgramServices();

        // getting user entered values and editing program data
        try {
            // getting program ID
            short programID = Short.parseShort(request.getParameter("program-ID"));

            // getting program status
            String status = request.getParameter("status");
            boolean isActive = status.equals("active");

            // updating program
            String userMsg = proService.update(programID,
                    // is active (change later with front end connect)
                    isActive,
                    // obtaining user entered program name
                    request.getParameter("program-name"),
                    // obtaining user entered manager name
                    request.getParameter("manager-name"));

            response.sendRedirect("programs");
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }

}
