package com.staffGauge.survey.cargo.filter;

import com.staffGauge.survey.user.dao.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mr.F on 2020/2/6
 */
public class CargoFilter implements Filter {

    protected void login(HttpServletRequest request, HttpServletResponse response, User user) {
        request.setAttribute("user", user);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
