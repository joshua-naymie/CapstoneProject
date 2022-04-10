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
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Team;
import models.User;
import models.util.CSVBuilder;
import services.AccountServices;
import services.TeamServices;

/**
 *
 * @author 641380
 */
public class ReportServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/report.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         // Obtain the action from the JSP
        String action = request.getParameter("action");

        // edit user
        // change status
        // create user 
        // try catch to handle what happens based on the action obtained
        // dummy names for now match the cases with the frontend
        try {
            switch (action) {
                // creating a new user
                case "edit":
                    // request.setAttribute("startView", true);
                    //edit(request, response);
                    break;
                    
                case "export":
                    //export(request, response);
                    break;
                    
                case "fdTeam":
                    exportTeamCSV(request, response);
                    break;
                    
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
        }
    }
    
     private void exportTeamCSV(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"teamFoodDeliveryReport.csv\"");
            
            // match with frontend team id
            int teamId = Integer.parseInt(request.getParameter("teamId"));
            
            // retrieving team based on team id
            TeamServices ts = new TeamServices();
            Team findTeam = ts.get(teamId);
            
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            CSVBuilder builder = new CSVBuilder();
            
            String[] tableHeader = { "Email",
                                     "Last Name",
                                     "First Name",
                                     "Phone Number",
                                     "Street Address",
                                     "City",
                                     "Postal Code",
                                     "Date of Birth" };
            
            builder.addRecord(tableHeader);
            
            for(User user : users)
            {
                String dateOfBirth = user.getDateOfBirth() == null
                                                            ? null
                                                            : dateFormat.format(user.getDateOfBirth());
                
                Object[] recordData = { user.getEmail(),
                                        user.getLastName(),
                                        user.getFirstName(),
                                        user.getPhoneNumber(),
                                        user.getHomeAddress(),
                                        user.getUserCity(),
                                        user.getPostalCode(),
                                        dateOfBirth };
                
                builder.addRecord(recordData);
            }
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(builder.printFile());
            writer.flush();
            
            return;
        }
        catch (Exception ex)
        {
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
