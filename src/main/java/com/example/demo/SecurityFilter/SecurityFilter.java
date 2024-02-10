package com.example.demo.SecurityFilter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class SecurityFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("usuario") != null;
        String loginURL = httpRequest.getContextPath() + "/pages/security/login.xhtml";

        if (loggedIn || httpRequest.getRequestURI().equals(loginURL)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURL);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}