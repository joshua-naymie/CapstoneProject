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
 * @author lixia
 */
public class GetDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CompanyService cs = new CompanyService();
		StoreServices ss = new StoreServices();
		TeamServices tms = new TeamServices();
		AccountServices as = new AccountServices();

		String op = request.getParameter("operation");

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

		if (op.equals("program")) {

			String programAdd = (String) request.getParameter("programId");

			// String[] parts = programAdd.split(";");

			// String programAddName = parts[0];
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

			// if (programJSON.length() == 1) {
			// programJSON.setLength(programJSON.length() - 1);
			// }

			programJSON.append(']');

			response.setContentType("text/html");
			response.getWriter().write(programJSON.toString());
		}
                
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

		if (op.equals("singleTaskInfo")) {
			Task task = null;
			TaskService ts = new TaskService();

			String task_id = request.getParameter("task_id");
			Long taskId = Long.parseLong(task_id);

			try {
				task = ts.get(taskId);

			} catch (Exception ex) {
				Logger.getLogger(AddTaskServlet.class.getName()).log(Level.SEVERE, null, ex);
			}
			StringBuilder taskJSON = new StringBuilder();
//			taskJSON.append('[');

			if (task != null) {
				Date startDate = task.getStartTime();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = simpleDateFormat.format(startDate);
				simpleDateFormat = new SimpleDateFormat("HH:mm");
				String startTime = simpleDateFormat.format(startDate);
				Date endDate = task.getEndTime();
				String endTime = simpleDateFormat.format(endDate);

				taskJSON.append('{');
				taskJSON.append("\"task_id\":" + "\"" + task.getTaskId() + "\",");
				taskJSON.append("\"task_description\":" + "\"" + task.getTaskDescription() + "\",");
				taskJSON.append("\"program_name\":" + "\"" + task.getProgramId().getProgramName() + "\",");
				taskJSON.append("\"task_city\":" + "\"" + task.getTaskCity() + "\",");
				taskJSON.append("\"start_time\":" + "\"" + task.getStartTime() + "\",");
				taskJSON.append("\"end_time\":" + "\"" + task.getEndTime() + "\",");
				taskJSON.append("\"approving_manager\":" + "\"" + task.getApprovingManager() + "\",");
				taskJSON.append("\"store_name\":" + "\"" + task.getTeamId().getStoreId().getStoreName() + "\",");
				taskJSON.append("\"max_users\":" + "\"" + task.getMaxUsers() + "\"");
				taskJSON.append("},");

				if (taskJSON.length() > 2) {
					taskJSON.setLength(taskJSON.length() - 1);
				}

//				taskJSON.append(']');

				response.setContentType("text/html");
				response.getWriter().write(taskJSON.toString());
			}
		}
                
                if(op.equals("cancelTask")){
                    
                }

		// if (taskJSON.length() > 2) {
		// taskJSON.setLength(programJSON.length() - 1);
		// }

		// taskJSON();
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
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
