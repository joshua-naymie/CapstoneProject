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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Task;
import models.Team;
import models.User;
import models.util.CSVBuilder;
import services.AccountServices;
import services.TaskService;
import services.TeamServices;

/**
 *
 * @author 641380
 */
public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        getServletContext().getRequestDispatcher("/WEB-INF/report.jsp").forward(request, response);
        getServletContext().getRequestDispatcher("/WEB-INF/reportsTestDeleteAfter.jsp").forward(request, response);

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
        
        //REMOVE WHEN DONE
        //------------------------------
        if(request.getParameter("TEST").equals("TEST"))
        {
            exportReportPerStore(request, response);
        }
        else
        {
            // getting user action from JSP
            String action = request.getParameter("action");
            // switch to determine users chosen action
            try {
                switch (action) {
                    // add new program
                    case "foodTeamReport":
                        exportFoodProgramTeamReport(request, response);
                        break;
                    // save edit changes
                    case "individualReport":
                        exportIndividualReport(request, response);
                        break;
                    // throw exception if the action is none of the above    
                    default:
                        throw new Exception();
                }


                //------------------------------
            } catch (Exception e) {
                Logger.getLogger(ReportServlet.class.getName()).log(Level.WARNING, null, e);
                System.err.println("Error Occured carrying out action:" + action);
            }
        }
        
    }

    private void exportFoodProgramTeamReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"FoodProgramTeamReport.csv\"");

            //total food weight
            short totalWeightOfOrgDonations = 0;
            short totalWeightOfComDonations = 0;
            // retrieve team id 
            TeamServices tmService = new TeamServices();
            int teamID = Integer.parseInt(request.getParameter("teamId"));
            // System.out.println("TeamId from web: " + teamID);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // start date for report
            String stringStartdate = request.getParameter("startdate");
            // System.out.println("start Date: " + stringStartdate);
            Date startDate = dateFormat.parse(stringStartdate);
            // System.out.println("start Date: " + startDate);

            // end date for report
            String stringEnddate = request.getParameter("enddate");
            Date endDate = dateFormat.parse(stringEnddate);
            // get all tasks
            TaskService ts = new TaskService();
            List<Task> allTask = ts.getAll();

            CSVBuilder builder = new CSVBuilder();

            String[] tableHeader = {"Date",
                "Store Name",
                "Quantity",
                "Package",
                "Family Count",
                "Organization Name",
                "Total Weight Organization (Ibs)",
                "Total Weight Community (Ibs)"};

            builder.addRecord(tableHeader);

            // hour in milliseconds
            long hour = 3600 * 1000;

            for (Task checkTask : allTask) {

                if (checkTask.getTeamId().getTeamId() == teamID && checkTask.getIsApproved()
                        && (checkTask.getStartTime().getTime() >= startDate.getTime()
                        && checkTask.getStartTime().getTime() <= (endDate.getTime() + 23 * hour))) {
                    //System.out.println("task start date: " + checkTask.getStartTime());
                    String dateOfTask = checkTask.getStartTime() == null
                            ? "No date recorded"
                            : dateFormat.format(checkTask.getStartTime());
                    short familyCount = 0;
                    if (checkTask.getFoodDeliveryData().getFamilyCount() != null) {
                        familyCount = checkTask.getFoodDeliveryData().getFamilyCount();
                        totalWeightOfComDonations += checkTask.getFoodDeliveryData().getFoodAmount() * (checkTask.getFoodDeliveryData().getPackageId().getWeightLb());
                    }
                    String orgName = "None";
                    if (checkTask.getFoodDeliveryData().getOrganizationId() != null) {
                        orgName = checkTask.getFoodDeliveryData().getOrganizationId().getOrgName();
                        totalWeightOfOrgDonations += checkTask.getFoodDeliveryData().getFoodAmount() * (checkTask.getFoodDeliveryData().getPackageId().getWeightLb());
                    }
//                    short familyCount = checkTask.getFoodDeliveryData().getFamilyCount() == null
//                            ? 0
//                            :checkTask.getFoodDeliveryData().getFamilyCount();
//                    String orgName = checkTask.getFoodDeliveryData().getOrganizationId().getOrgName() == null
//                            ? "None"
//                            : checkTask.getFoodDeliveryData().getOrganizationId().getOrgName();
                    //System.out.println("famcount: " + familyCount);
                    //System.out.println("orgName: " + orgName);
                    Object[] recordData = {dateOfTask,
                        checkTask.getTeamId().getTeamName(),
                        checkTask.getFoodDeliveryData().getFoodAmount(),
                        checkTask.getFoodDeliveryData().getPackageId().getPackageName(),
                        familyCount,
                        orgName};

                    builder.addRecord(recordData);
                }
            }
            Object[] recordData = {"", "", "", "", "", "",
                totalWeightOfOrgDonations,
                totalWeightOfComDonations};

            builder.addRecord(recordData);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(builder.printFile());
            writer.flush();

            return;
        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exportIndividualReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"IndividualReport.csv\"");

            //total calculations
            BigDecimal totalHoursWorked = new BigDecimal(0);
            short totalTasksCompleted = 0;
            short totalMileage = 0;

            // retrieve user id
            int userID = Integer.parseInt(request.getParameter("userId"));
            // System.out.println("TeamId from web: " + teamID);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // start date for report
            String stringStartdate = request.getParameter("startdate");
            // System.out.println("start Date: " + stringStartdate);
            Date startDate = dateFormat.parse(stringStartdate);
            // System.out.println("start Date: " + startDate);

            // end date for report
            String stringEnddate = request.getParameter("enddate");
            Date endDate = dateFormat.parse(stringEnddate);
            // get all tasks
            TaskService ts = new TaskService();
            List<Task> allTask = ts.getAll();

            CSVBuilder builder = new CSVBuilder();

            String[] tableHeader = {"Date",
                "Hours Worked",
                "Program",
                "Total Hours Worked",
                "Total tasks completed",
                "Total mileage"};

            builder.addRecord(tableHeader);

            // hour in milliseconds
            long hour = 3600 * 1000;

            for (Task checkTask : allTask) {
                if (checkTask.getUserId() != null && checkTask.getUserId().getUserId() == userID && checkTask.getIsApproved()
                        && (checkTask.getStartTime().getTime() >= startDate.getTime()
                        && checkTask.getStartTime().getTime() <= (endDate.getTime() + 23 * hour))) {
                    //System.out.println("task start date: " + checkTask.getStartTime());
                    String dateOfTask = checkTask.getStartTime() == null
                            ? "No date recorded"
                            : dateFormat.format(checkTask.getStartTime());
                    //check if its food program task
                    BigDecimal hoursWorkedPerTask = new BigDecimal(0);
                    if (checkTask.getProgramId().getProgramId() == 1) {
                        totalHoursWorked.add(checkTask.getFoodDeliveryData().getFoodHoursWorked());
                        hoursWorkedPerTask = checkTask.getFoodDeliveryData().getFoodHoursWorked();
                        totalTasksCompleted++;
                        totalMileage += checkTask.getFoodDeliveryData().getMileage();
                    }
                    //check if its hotline task
                    if (checkTask.getProgramId().getProgramId() == 2) {
                        totalHoursWorked.add(checkTask.getHotlineData().getHotlineHoursWorked());
                        hoursWorkedPerTask = checkTask.getFoodDeliveryData().getFoodHoursWorked();
                        totalTasksCompleted++;
                    }

                    Object[] recordData = {dateOfTask,
                        hoursWorkedPerTask,
                        checkTask.getProgramId().getProgramName()};

                    builder.addRecord(recordData);
                }
            }
            Object[] recordData = {"", "", "",
                totalHoursWorked,
                totalTasksCompleted,
                totalMileage};

            builder.addRecord(recordData);

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(builder.printFile());
            writer.flush();

            return;
        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void exportReportPerStore(HttpServletRequest request, HttpServletResponse response)
    {
        CSVBuilder reportBuilder = new CSVBuilder();
        
        String[] headerData = { "Date",
                                "Store Name",
                                "Qty",
                                "Package",
                                "Families/Code",
                                "Type"};
        
        reportBuilder.addRecord(headerData);
        
        TaskService taskServ = new TaskService();
        try
        {
            List<Task> tasks = taskServ.getByProgramCityDate("1", "Calgary", "2020-03-15", "2024-04-18");
            
            System.out.println(tasks.size());
            for(Task t : tasks)
            {
                System.out.println("City: " + t.getTaskCity());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
}
