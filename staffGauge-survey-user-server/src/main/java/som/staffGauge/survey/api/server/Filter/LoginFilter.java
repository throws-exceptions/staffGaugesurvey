package com.staffGauge.survey.api.server.Filter;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.staffGauge.survey.user.dao.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import som.staffGauge.survey.api.server.redis.RedisClient;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mr.F on 2020/2/5
 */
@Component
@WebFilter(urlPatterns = "/user/*")
public abstract class LoginFilter implements Filter {
    @Autowired
    RedisClient redisClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("进入拦截器");
    }

    //用缓存，防止每次都去请求中读取token
    private static Cache<String, User> cache = CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3, TimeUnit.MINUTES).build();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截器运行");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-App-Id, X-Token");
        response.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals("token")) {
                        token = c.getValue();
                    }
                }
            }
        }
        User user = null;
        if (StringUtils.isNotBlank(token)) {
            cache.getIfPresent(token);
            if (user == null) {
                user = requestUser(token);
                if (user == null) filterChain.doFilter(request, response);
                if (user != null) {
                    cache.put(token, user);
                }
            }
        }
        System.out.println(user.getMail());
        System.out.println(user.getPermission());
//        if (user == null) {
//            response.sendRedirect("http://localhost:8081/user/login");
//            return;
//        }

//        login(request, response, user);
        filterChain.doFilter(request, response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, User user);

    private User requestUser(String token) {
//        String url = "http://user-service:8081/user/authentication";
//        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(url);
//        post.addHeader("token", token);
//        InputStream inputStream = null;
//        try {
//            HttpResponse httpResponse = httpClient.execute(post);
//            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                new RuntimeException("请求用户信息失败！" + httpResponse.getStatusLine());
//            }
//            inputStream = httpResponse.getEntity().getContent();
//            byte[] temp = new byte[1024];
//            StringBuffer sb = new StringBuffer();
//            int len = 0;
//            while ((len = inputStream.read(temp)) > 0) {
//                sb.append(new String(temp, 0, len));
//            }
//            User user = new ObjectMapper().readValue(sb.toString(), User.class);
//            return user;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
        User user = redisClient.get(token);
        return user;
    }

    @Override
    public void destroy() {
        System.out.println("拦截器退出");
    }
}
