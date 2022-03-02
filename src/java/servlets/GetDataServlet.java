package servlets;

import com.google.gson.Gson;
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
import services.CompanyService;
import services.StoreServices;

/**
 *
 * @author lixia
 */
public class GetDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		CompanyService cs = new CompanyService();		
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
				for (Store store : storelist)
				{
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
