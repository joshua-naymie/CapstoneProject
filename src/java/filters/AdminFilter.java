package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.*;
import dataaccess.*;
import services.*;

import java.io.IOException;

public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpServletRequest.getSession();

        if (httpSession.getAttribute("email") != null) {
            int user_id = (int) httpSession.getAttribute("email");
            try {
                AccountServices as = new AccountServices();
                if (!as.admin(user_id)) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect("tasks");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}