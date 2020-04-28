package som.staffGauge.survey.api.server;

import com.staffGauge.survey.user.dao.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import som.staffGauge.survey.api.server.Filter.LoginFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mr.F on 2019/12/28
 */
@EnableCaching
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filter = new FilterRegistrationBean(new LoginFilter() {
            @Override
            protected void login(HttpServletRequest request, HttpServletResponse response, User user) {

            }
        });
        filter.addUrlPatterns("/user/*");
        //多个过滤器时执行顺序
        //filter.setOrder(1);
        return filter;
    }
}
