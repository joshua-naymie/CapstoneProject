package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;


import java.io.IOException;

public class AuthenticationFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("email") == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse)response;
            httpServletResponse.sendRedirect("login");
            return;
        }

        chain.doFilter(request, response);
    }
}