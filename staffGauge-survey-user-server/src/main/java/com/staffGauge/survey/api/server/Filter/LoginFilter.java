package com.staffGauge.survey.api.server.Filter;

import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Mr.F on 2020/2/5
 */
public class LoginFilter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
//        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

//    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("进入拦截器");
//    }
//
//
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("拦截器运行");
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
//            response.setStatus(HttpServletResponse.SC_OK);
//        }
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
//        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
////        String token = request.getParameter("token");
////        if (StringUtils.isBlank(token)) {
////            Cookie[] cookies = request.getCookies();
////            if (cookies != null) {
////                for (Cookie c : cookies) {
////                    if (c.getName().equals("token")) {
////                        token = c.getValue();
////                    }
////                }
////            }
////        }
////        User user = null;
////        if (StringUtils.isNotBlank(token)) {
////            cache.getIfPresent(token);
////            if (user == null) {
////                user = requestUser(token);
////                if (user == null) filterChain.doFilter(request, response);
////                if (user != null) {
////                    cache.put(token, user);
////                }
////            }
////        }
////        System.out.println(user.getMail());
////        System.out.println(user.getPermission());
//////        if (user == null) {
//////            response.sendRedirect("http://localhost:8081/user/login");
//////            return;
//////        }
////
//////        login(request, response, user);
////        filterChain.doFilter(request, response);
//    }
//
//
//    public void destroy() {
//        System.out.println("拦截器退出");
//    }
}
