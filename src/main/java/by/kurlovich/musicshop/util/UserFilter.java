package by.kurlovich.musicshop.util;

import by.kurlovich.musicshop.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {

    private String errorPage;

    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig != null) {
            errorPage = filterConfig.getInitParameter("error_page");
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (errorPage == null) {
            returnError(request, response, "AuthorizationFilter not properly configured!  Contact Administrator.");
        }

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            returnError(request, response, "User does not exist in session!");
        } else {
            if (currentUser.getRole().equals("admin") || currentUser.getRole().equals("user")) {

                chain.doFilter(request, response);
            } else {
                returnError(request, response, "You are not authorized to access this area!");
            }
        }
    }

    public void destroy() {
    }

    private void returnError(ServletRequest request, ServletResponse response, String errorString)
            throws ServletException, IOException {
        request.setAttribute("message", errorString);
        request.getRequestDispatcher(errorPage).forward(request, response);
    }
}
