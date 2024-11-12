package org.launchcode.techjobsauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.launchcode.techjobsauth.controllers.AuthenticationController;
import org.launchcode.techjobsauth.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class AuthenticationFilter implements HandlerInterceptor {

    // Autowired dependencies
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;

    // Whitelist of paths that can be accessed without authentication
    private static final String[] WHITELIST = {
            "/login",
            "/register",
            "/public/**",
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if the current request URL is in the whitelist
        if (isWhitelisted(request.getRequestURI())) {
            return true;  // Allow access to whitelisted paths
        }

        // Grab session data
        Object userSession = request.getSession().getAttribute("user");

        // Check if user is logged in
        if (userSession != null) {
            return true;  // User is logged in, continue with the request
        }

        // If no user session, redirect to login page
        response.sendRedirect("/login");
        return false;  // Don't proceed with the request
    }

    // Helper method to check if the path is in the whitelist
    private boolean isWhitelisted(String path) {
        for (String whitelistPath : WHITELIST) {
            if (path.startsWith(whitelistPath)) {
                return true;  // Path matches one of the whitelist entries
            }
        }
        return false;  // Path is not in the whitelist
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // Post-processing logic, if needed (can be left empty for now)
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Cleanup after the request has been completed, if needed (can be left empty for now)
    }
}
