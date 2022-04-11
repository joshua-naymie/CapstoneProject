/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.FoodDeliveryData;
import models.Task;
import models.User;
import models.util.CSVBuilder;
import services.AccountServices;
import services.FoodHotlineDataService;
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
        getServletContext().getRequestDispatcher("/WEB-INF/report.jsp").forward(request, response);
//        getServletContext().getRequestDispatcher("/WEB-INF/reportsTestDeleteAfter.jsp").forward(request, response);

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
        // getting user action from JSP
        String action = request.getParameter("action");
        // switch to determine users chosen action
        try {
            switch (action) {
                // food program specific team report
                case "foodTeamReport":
                    exportFoodProgramTeamReport(request, response);
                    break;
                // individual all tasks report
                case "individualReport":
                    exportIndividualReport(request, response);
                    break;
                case "foodProgramStoreReport":
                    exportFoodProgramStoreReport(request, response);
                    break;
                case "individualHotlineReport":
                    exportIndividualHotline(request, response);
                    break;
                case "wholeFoodProgramReport":
                    exportFoodDeliveryTotalWeightPerOrganization(request, response);
                    break;
                case "foodProgramCityReport":
                    exportReportPerCity(request, response);
                    break;
                // throw exception if the action is none of the above    
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
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
            //System.out.println("start Date: " + startDate);

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
//            BigDecimal totalHoursWorked = new BigDecimal(0);
            double tempHoursWorked = 0.0;
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
                        tempHoursWorked = checkTask.getFoodDeliveryData().getFoodHoursWorked().doubleValue();
                        hoursWorkedPerTask = checkTask.getFoodDeliveryData().getFoodHoursWorked();
                        totalTasksCompleted++;
                        totalMileage += checkTask.getFoodDeliveryData().getMileage();
                    }
                    //check if its hotline task
                    if (checkTask.getProgramId().getProgramId() == 2) {
                        tempHoursWorked = checkTask.getHotlineData().getHotlineHoursWorked().doubleValue();
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
                tempHoursWorked,
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

    private void exportFoodProgramStoreReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"FoodProgramStoreReport.csv\"");

            //total food weight
            short totalWeightOfOrgDonations = 0;
            short totalWeightOfComDonations = 0;

            // retrieve store id
            int storeID = Integer.parseInt(request.getParameter("storeId"));
            // System.out.println("TeamId from web: " + teamID);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // start date for report
            String stringStartdate = request.getParameter("startdate");
            // System.out.println("start Date: " + stringStartdate);
            Date startDate = dateFormat.parse(stringStartdate);
            System.out.println("start Date: " + startDate);

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

                if (checkTask.getFoodDeliveryData() != null
                        && checkTask.getFoodDeliveryData().getStoreId().getStoreId() == storeID && checkTask.getIsApproved()
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

    private void exportIndividualHotline(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=\"IndividualHotline.csv\"");
        CSVBuilder builder = new CSVBuilder();
        String[] tableHeader = {"First Name",
            "Last Name",
            "Date",
            "Shift Hours",
            "Total Hours"};
        builder.addRecord(tableHeader);
        FoodHotlineDataService fh = new FoodHotlineDataService();
        AccountServices as = new AccountServices();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringStartdate = request.getParameter("startdate");
        Date startDate = dateFormat.parse(stringStartdate);
        String stringEnddate = request.getParameter("enddate");
        Date endDate = dateFormat.parse(stringEnddate);
        long hour = 3600 * 1000;

        User user = as.getByID(Integer.parseInt(request.getParameter("userId")));
        //System.out.println("User Id: " + user.getUserId());
        //get tasks of the user for hotline 
        TaskService ts = new TaskService();
        List<Task> hotlineTasks = ts.getHotlineApprovedByUser(user.getUserId());
        //System.out.println("Task size: " + hotlineTasks.size());
        double shiftHrs = 0;
        double totalHrs = 0;
        for (Task t : hotlineTasks) {
            LocalDateTime startTime = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = t.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if ((t.getStartTime().getTime() >= startDate.getTime()
                    && t.getStartTime().getTime() <= (endDate.getTime() + 23 * hour))) {
                shiftHrs = t.getHotlineData().getHotlineHoursWorked().doubleValue();
                        //java.time.Duration.between(startTime, endTime).toHours();
                totalHrs += shiftHrs;
                String dateOfTask = t.getStartTime() == null
                        ? "No date recorded"
                        : dateFormat.format(t.getStartTime());
                Object[] recordData = {user.getFirstName(),
                    user.getLastName(),
                    dateOfTask,
                    shiftHrs
                };
                builder.addRecord(recordData);
            }
            Object[] recordData = {"", "", "", "",
                totalHrs
            };
            builder.addRecord(recordData);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(builder.printFile());
            writer.flush();
            return;
        }
    }

    public void exportFoodDeliveryTotalWeightPerOrganization(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Type", "text/csv");
        response.setHeader("Content-Disposition", "attachment;filename=\"FoodProgramReport.csv\"");
        //total food weight
        short totalWeightOfOrgDonations = 0;
        short totalWeightOfComDonations = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // start date for report
        String stringStartdate = request.getParameter("startdate");
        Date startDate = dateFormat.parse(stringStartdate);
        // end date for report
        String stringEnddate = request.getParameter("enddate");
        Date endDate = dateFormat.parse(stringEnddate);
        // get all food delivery data 
        FoodHotlineDataService fd = new FoodHotlineDataService();
        List<FoodDeliveryData> fdd = fd.getAllFoodDeliveryData();
        CSVBuilder builder = new CSVBuilder();
        String[] tableHeader = {"Organization Name",
            "Family Count",
            "Delivery Date",
            "Food Amount",
            "Package name",
            "Weight",
            "Total Weight Organization (Ibs)",
            "Total Weight Community (Ibs)"};
        builder.addRecord(tableHeader);
        long hour = 3600 * 1000;
        for (FoodDeliveryData f : fdd) {
            if ((f.getTask().getStartTime().getTime() >= startDate.getTime()
                    && f.getTask().getStartTime().getTime() <= (endDate.getTime() + 23 * hour))) {
                String deliveryDate = f.getTask().getStartTime() == null
                        ? "No date recorded"
                        : dateFormat.format(f.getTask().getStartTime());
                short families = 0;
                if (f.getFamilyCount() != null) {
                    families = f.getFamilyCount();
                    totalWeightOfComDonations += f.getFoodAmount() * (f.getPackageId().getWeightLb());
                }
                String orgName = "Community";
                if (f.getOrganizationId() != null) {
                    orgName = f.getOrganizationId().getOrgName();
                    totalWeightOfOrgDonations += f.getFoodAmount() * (f.getPackageId().getWeightLb());
                }
                Object[] recordData = {orgName,
                    families,
                    deliveryDate,
                    f.getFoodAmount(),
                    f.getPackageId().getPackageName(),
                    (f.getFoodAmount() * (f.getPackageId().getWeightLb())),};
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
    }

    private void exportReportPerCity(HttpServletRequest request, HttpServletResponse response)
    {
        CSVBuilder reportBuilder = new CSVBuilder();

        String city = request.getParameter("city"),
               startDate = request.getParameter("startdate"),
               endDate = request.getParameter("enddate");
        
        String[] titleData = { "Food delivered ", city, startDate + " - " + endDate };
        reportBuilder.addField("Food delivered");
        reportBuilder.newRecord();
        reportBuilder.addField("Calgary");
        reportBuilder.newRecord();
        reportBuilder.addField("2022-12-02 - 2022-12-04");
        reportBuilder.newRecord();
        
//        reportBuilder.addRecord(titleData);
        reportBuilder.newRecord();  
        
        String[] headerData = { null,
                                "Date",
                                "Store Name",
                                "Qty",
                                "Package",
                                "Families/Code",
                                "Type" };

        reportBuilder.addRecord(headerData);

        TaskService taskServ = new TaskService();
        try
        {
            List<Task> tasks = taskServ.getByProgramCityDate("1", "Calgary", "2020-03-15", "2024-04-18");
            int totalOrgWeightLbs = 0,
                totalFamWeightLbs = 0;
            
//            System.out.println(tasks.size());
            for (Task t : tasks)
            {
                FoodDeliveryData taskData = t.getFoodDeliveryData();
                if(taskData == null)
                {
                    System.out.println("TASK-ID: " + t.getTaskId());
                }
                short familyOrgCode;
                String type;
                
                if(taskData.getOrganizationId() != null)
                {
                    totalOrgWeightLbs += taskData.getFoodAmount() * taskData.getPackageId().getWeightLb();
                    familyOrgCode = taskData.getOrganizationId().getOrgCode();
                    type = "Organization";
                }
                else
                {
                    totalFamWeightLbs += taskData.getFoodAmount() * taskData.getPackageId().getWeightLb();
                    familyOrgCode = taskData.getFamilyCount();
                    type = "Community";
                }
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                
                Object[] taskRecord = { null,
                                        dateFormat.format(t.getStartTime()),
                                        taskData.getStoreId().getStoreName(), 
                                        taskData.getFoodAmount(),
                                        taskData.getPackageId().getPackageName(),
                                        familyOrgCode,
                                        type };
                
                reportBuilder.addRecord(taskRecord);             
            }
            
            reportBuilder.newRecord();
            reportBuilder.newRecord();

            String[] totaHeader = { null,
                                    "Total Organization (lbs)",
                                    "Total Family (lbs)" };
            
            reportBuilder.addRecord(totaHeader);

            
            Object[] totalRecord = { null, 
                                     totalOrgWeightLbs,
                                     totalFamWeightLbs };
            
            reportBuilder.addRecord(totalRecord);
            
            
            response.setHeader("Content-Type", "text/csv");
            response.setHeader("Content-Disposition", "attachment;filename=\"FoodDeliveredByCity-Report.csv\"");
            
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-32"));
            writer.write(reportBuilder.printFile());
            writer.flush();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}