package com.staffGauge.survey.api.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by Mr.F on 2019/12/28
 */
@EnableCaching
@EnableDubbo
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean registrationBean() {
//        FilterRegistrationBean filter = new FilterRegistrationBean();
//        filter.addUrlPatterns("/user/*");
//        //多个过滤器时执行顺序
//        //filter.setOrder(1);
//        return filter;
//    }
}
