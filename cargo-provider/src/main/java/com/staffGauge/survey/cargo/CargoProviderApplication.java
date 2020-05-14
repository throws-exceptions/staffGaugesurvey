package com.staffGauge.survey.cargo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@MapperScan(basePackages = "com.staffGauge.survey.cargo.dal.persistence")
@SpringBootApplication
public class CargoProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CargoProviderApplication.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);
    }

}
