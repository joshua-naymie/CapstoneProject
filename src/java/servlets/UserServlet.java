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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ProgramTraining;
import models.util.JSONBuilder;
import models.util.JSONKey;
import models.User;
import services.AccountServices;
import services.ProgramServices;

/**
 * handling all actions on users page
 * 
 */
public class UserServlet extends HttpServlet {
    
    // remove dashs / white space
    private static final String ZIPCODE_REMOVE_REGEXP = "[\\-\\s]";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * sends up all user data
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("username");
        if (id != null) {
            AccountServices accService = new AccountServices();
            User editUser = new User();
            try {
                editUser = accService.get(id);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, ex);
            }
            JSONKey[] userKeys = {new JSONKey("id", false),
                new JSONKey("email", true),
                new JSONKey("firstName", true),
                new JSONKey("lastName", true),
                new JSONKey("phoneNum", true),
                new JSONKey("address", true),
                new JSONKey("isAdmin", false),
                new JSONKey("city", true),
                new JSONKey("isActive", false),
                new JSONKey("DOB", true),
                new JSONKey("postalCode", true),
                new JSONKey("regDate", true),
                new JSONKey("teamInput", true),
                new JSONKey("roleId", false)};
            
            // obtaining roleId
            System.out.println("user id + program Id: " + editUser.getUserId() + ", " + editUser.getTeamId().getProgramId().getProgramId());
            ProgramServices ps = new ProgramServices();
            ProgramTraining checkRole = new ProgramTraining();
            if (editUser.getUserId() != null && editUser.getTeamId() != null && !editUser.getIsAdmin()) {
                try {
                    checkRole = ps.getUserRoleIdFromProgramTraining(editUser.getUserId(), editUser.getTeamId().getProgramId().getProgramId());
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//           System.out.println ("rolId: " + checkRole.getRoleId().getRoleId());

            JSONBuilder userBuilder = new JSONBuilder(userKeys);
            // make the user object into json data
            StringBuilder returnData = new StringBuilder();

            returnData.append("var editUser = ");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String DOB = dateFormat.format(editUser.getDateOfBirth());
            String regDate = dateFormat.format(editUser.getRegistrationDate());
            
            int roleId = checkRole.getRoleId() == null ?  1 : checkRole.getRoleId().getRoleId();
            
            Object[] userData = {editUser.getUserId(),
                editUser.getEmail(),
                editUser.getFirstName(),
                editUser.getLastName(),
                editUser.getPhoneNumber(),
                editUser.getHomeAddress(),
                editUser.getIsAdmin(),
                editUser.getUserCity(),
                editUser.getIsActive(),
                DOB,
                editUser.getPostalCode(),
                regDate,
                editUser.getTeamId().getTeamName(),
                roleId};

            returnData.append(userBuilder.buildJSON(userData));
            returnData.append(";");

            request.setAttribute("userData", returnData);
        }
//        System.out.println("edit user ID: " + id);
//        User test = (User) request.getAttribute("editUser");
//        System.out.println(test.getFirstName());
        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
    }

    // checking if the string value is null so it can be appropriately returned to the
    // json file
    private String checkNull(String check) {
        if (check == null) {
            return "null";
        }
        return "\"" + check + "\"";
    }

    // convert a date object to string
    // adds quotes to the dates
    private String dateToString(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return "\"" + simpleDateFormat.format(date) + "\"";
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * handles adding, editing and cancel functions on the page
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtain the action from the JSP
        // get parameter name from front end
        String action = request.getParameter("action");

        // edit user
        // change status
        // create user 
        // try catch to handle what happens based on the action obtained
        try {
            switch (action) {
                // creating a new user
                case "Add":
                    // request.setAttribute("startView", true);
                    add(request, response);
                    break;

                // saving account status change
                case "Save":
                    save(request, response);
                    break;

                case "Cancel":
                    response.sendRedirect("users");
                    break;
                default:
                    System.out.println("no action picked");
                    break;
            }
        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
            System.err.println("Error Occured carrying out action:" + action);
//            log("Error Occured carrying out action:" + action);
        }
        // work on exporting if we have time before use case is due
    }

    private void add(HttpServletRequest request, HttpServletResponse response) {
        AccountServices accService = new AccountServices();
        ProgramServices proService = new ProgramServices();

        try {
            // converting Strings to Date variables for DOB / registration date
            String dobDate = request.getParameter("birthday");
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dobDate);

            String regDate = request.getParameter("signupdate");
            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);

            //dummy date to test with
            //Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-06");
            // Getting Admin
            short roleSelection = Short.parseShort(request.getParameter("roleID"));
            boolean isAdmin = false;
            boolean isActive = request.getParameter("active") != null ? true : false;

            if (roleSelection == 1) {
                isAdmin = true;
            }

            System.out.println("active status: " + isActive);
            System.out.println("postal code: " + request.getParameter("user_postalcode"));

            // inserting the new user
            // role drop down: roleID
            String userMsg = accService.insert(
                    // user ID
                    Integer.parseInt(request.getParameter("id")),
                    // user email
                    request.getParameter("userEmail"),
                    //is admin
                    isAdmin,
                    request.getParameter("user_city"),
                    request.getParameter("user_firstname"),
                    request.getParameter("user_lastname"),
                    // is active
                    isActive,
                    request.getParameter("user_password"),
                    // DOB
                    dateOfBirth,
                    request.getParameter("user_phone"),
                    request.getParameter("street"),
                    request.getParameter("user_postalcode").replaceAll(ZIPCODE_REMOVE_REGEXP, ""),
                    // registration date
                    registrationDate,
                    // team 
                    Integer.parseInt(request.getParameter("teamId")));

            // add user to program training table if they are not an admin
            if (!isAdmin) {
                proService.insertProgramTrainingUserCreation(Integer.parseInt(request.getParameter("teamId")),
                        roleSelection,
                        Integer.parseInt(request.getParameter("id")));
            }
            // test print statements 
            //System.out.println(request.getParameter("username") + request.getParameter("user_firstname"));
            request.setAttribute("users", accService.getAll());
            request.setAttribute("userMessage", userMsg);

            // Redirect back to the account page
            response.sendRedirect("users");

        } catch (Exception e) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.WARNING, null, e);
        }

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        try {
            AccountServices accService = new AccountServices();
            ProgramServices proService = new ProgramServices();

            // Getting Admin
            short roleSelection = Short.parseShort(request.getParameter("roleID"));
            boolean isAdmin = false;
            boolean isActive = request.getParameter("active") != null ? true : false;
            if (roleSelection == 1) {
                isAdmin = true;
            }
            //parsing dates
            String dobDate = request.getParameter("birthday");
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dobDate);

            String regDate = request.getParameter("signupdate");
            Date registrationDate = new SimpleDateFormat("yyyy-MM-dd").parse(regDate);
            // insert parameters into account services to save user
            // change parameters to match front end
            String userMsg = accService.update(
                    // user ID
                    Integer.parseInt(request.getParameter("id")),
                    // user email
                    request.getParameter("userEmail"),
                    //is admin
                    isAdmin,
                    request.getParameter("user_city"),
                    request.getParameter("user_firstname"),
                    request.getParameter("user_lastname"),
                    // is active
                    isActive,
                    //request.getParameter("user_password"),
                    // DOB
                    dateOfBirth,
                    request.getParameter("user_phone"),
                    request.getParameter("street"),
                    request.getParameter("user_postalcode").replaceAll(ZIPCODE_REMOVE_REGEXP, ""),
                    // registration date
                    registrationDate,
                    // team
                    Integer.parseInt(request.getParameter("teamId")));

            if (!isAdmin) {
                proService.updateProgramTrainingUserCreation(Integer.parseInt(request.getParameter("teamId")),
                        roleSelection,
                        Integer.parseInt(request.getParameter("id")));
            }

            //request.setAttribute("users", accService.getAll());
            //getServletContext().getRequestDispatcher("/WEB-INF/UserTest.jsp").forward(request, response);
            request.setAttribute("users", accService.getAll());
            request.setAttribute("userMessage", userMsg);

            // Redirect back to the account page
            response.sendRedirect("users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
