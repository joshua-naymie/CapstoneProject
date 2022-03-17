package servlets;

import models.util.JSONKey;
import models.util.JSONBuilder;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

import services.*;
import models.*;

/**
 *
 * @author <jnaymie@gmail.com>
 */
public class HistoryServlet extends HttpServlet
{
    private static final
    DateFormat jsonDateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm a");
    
    private static final
    String HISTORY_JSP_DIR = "/WEB-INF/history.jsp";
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        TaskService taskService = new TaskService();
        try
        {
            String id = request.getParameter("id");
            
            id = (id == null || id.isBlank()) ? "1" : id;
            List<Task> tasks = taskService.getHistory(Long.parseLong(id));
            StringBuilder historyVar = new StringBuilder();
            
            historyVar.append("var historyData = [");
            
            JSONKey[] historyKeys = {
                                        new JSONKey("id", false),
                                        new JSONKey("program", true),
                                        new JSONKey("startTime", true),
                                        new JSONKey("endTime", true),
                                        new JSONKey("status", true),
                                        new JSONKey("city", true)
                                    };
            
            JSONBuilder historyBuilder = new JSONBuilder(historyKeys);
            
            if(!tasks.isEmpty())
            {
                int i;
                for(i=0; i<tasks.size()-1; i++)
                {
                    historyVar.append(buildTaskJSON(tasks.get(i), historyBuilder));
                    historyVar.append(',');
                }
                historyVar.append(buildTaskJSON(tasks.get(i), historyBuilder));
            }
            historyVar.append("];");
            
            request.setAttribute("historyData", historyVar.toString());
            getServletContext().getRequestDispatcher(HISTORY_JSP_DIR).forward(request, response);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private String buildTaskJSON(Task task, JSONBuilder builder)
    {
        Object[] values = {
                            task.getTaskId(),
                            task.getProgramId().getProgramName(),
                            jsonDateFormat.format(task.getStartTime()),
                            jsonDateFormat.format(task.getEndTime()),
                            getTaskStatus(task),
                            task.getTaskCity()
                          };
        
        return builder.buildJSON(values);
    }
    
    private String getTaskStatus(Task task)
    {
        if(task.isApproved())
        {
            return "Approved";
        }
        else if(task.isDissaproved() != null &&task.isDissaproved())
        {
            return "Dissaproved";
        }
        else if(task.isDissaproved() != null && task.isSubmitted())
        {
            return "Submitted";
        }
        else
        {
            return "Not Submitted";
        }
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
            throws ServletException, IOException
    {
        
    }
}
