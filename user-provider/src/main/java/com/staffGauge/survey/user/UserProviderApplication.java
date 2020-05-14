package com.staffGauge.survey.user;

import com.staffGauge.survey.user.dao.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@MapperScan(basePackages = "com.staffGauge.survey.user.dal.persistence")
@SpringBootApplication
public class UserProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserProviderApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
