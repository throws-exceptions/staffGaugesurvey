package com.staffGauge.survey.user.client;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.staffGauge.survey.user.dao.User;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mr.F on 2020/2/5
 */
public abstract class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    //用缓存，防止每次都去请求中读取token
    private static Cache<String,User> cache= CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(3, TimeUnit.MINUTES).build();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截器运行");
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String token=request.getParameter("token");
        if(StringUtils.isBlank(token)){
            Cookie[] cookies=request.getCookies();
            if(cookies!=null){
                for(Cookie c:cookies){
                    if(c.getName().equals("token")){
                        token=c.getValue();
                    }
                }
            }
        }
        User user=null;
        if(StringUtils.isNotBlank(token)){
            cache.getIfPresent(token);
            if(user==null) {
                user = requestUser(token);
                if(user!=null){
                    cache.put(token,user);
                }
            }
        }
        if(user==null){
            response.sendRedirect("http://user-service:8081/user/login");
            return;
        }

        login(request,response,user);
        filterChain.doFilter(request,response);
    }

    protected abstract void login(HttpServletRequest request, HttpServletResponse response, User user);

    private User requestUser(String token) {
        String url = "http://user-service:8081/user/authentication";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        post.addHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse httpResponse = httpClient.execute(post);
            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                new RuntimeException("请求用户信息失败！" + httpResponse.getStatusLine());
            }
            inputStream = httpResponse.getEntity().getContent();
            byte[] temp = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            User user = new ObjectMapper().readValue(sb.toString(), User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
