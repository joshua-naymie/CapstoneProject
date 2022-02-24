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
import models.User;
import services.ProgramServices;

/**
 *
 * @author 861349
 */
public class ProgramServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
       // getting a list of all programs
       ProgramServices ps = new ProgramServices();
       List<Program> allPrograms = null;
        try {
            //get a list of all programs
            allPrograms = ps.getAll();  
        } catch (Exception ex) {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.WARNING, null, ex);
        }
        request.setAttribute("programs", allPrograms);
        
        // sending jason data
        StringBuilder returnData = new StringBuilder();
        returnData.append("var data = [");
        
        // Create keys
        JSONKey[] keys = { new JSONKey("storeId", true),
                           new JSONKey("program", true),
                           new JSONKey("manager", true), 
                           new JSONKey("active", true)};
        
         // Create builder with above keys
        JSONBuilder builder = new JSONBuilder(keys);
        
        // Create user JSON objects
        if(allPrograms.size() > 0)
        {
            int i;
            for(i=0; i<allPrograms.size()-1; i++)
            {
                returnData.append(buildUserJSON(allPrograms.get(i), builder));
                returnData.append(',');
            }
            returnData.append(buildUserJSON(allPrograms.get(i), builder));         
        }
        returnData.append("];");
        
        // setting program data attribute for the front end to use
        request.setAttribute("programData", returnData);
        
        // forwards data to jsp
        getServletContext().getRequestDispatcher("/WEB-INF/programTest.jsp").forward(request, response);

    }
    
    /**
     * Creates a user JSON object
     * @param user  The User to populate the JSON with
     * @param builder The JSONBuilder to create the JSON with
     * @return A User JSON as a String
     */
    private String buildUserJSON(Program program, JSONBuilder builder)
    {
        // retrieving program values into an array
        Object[] programValues = { program.getProgramId(),
                                program.getProgramName(),
                                program.getManagerName(),
                                program.getIsActive()};
            
        return builder.buildJSON(programValues);
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
                case "Add":
                    add(request, response);
                    break;
                // edit current programs
                // change status of program  
                case "Edit":
                    edit(request, response);
                    break;
                // save edit changes
                case "Save":
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
        
        // getting user entered values and insert new program
        try {
            // programs are always active on creation
            String userMsg = proService.insert(true, 
                    // obtaining user entered program name
                    request.getParameter("programName"),
                    // obtaining user entered manager name
                    request.getParameter("managerName"));
            
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
            getServletContext().getRequestDispatcher("/WEB-INF/programTest.jsp").forward(request, response);
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
            short programID = Short.parseShort(request.getParameter("savepID"));

            // updating program
            String userMsg = proService.update(programID,
                    // is active (change later with front end connect)
                    true,
                    // obtaining user entered program name
                    request.getParameter("savepName"),
                    // obtaining user entered manager name
                    request.getParameter("savepMan"));
            
            response.sendRedirect("programs");
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }
    }

}
