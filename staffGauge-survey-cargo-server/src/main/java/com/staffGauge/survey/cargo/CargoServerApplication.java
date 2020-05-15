package com.staffGauge.survey.cargo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Mr.F on 2020/2/6
 */
@EnableDubbo
@SpringBootApplication
public class CargoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CargoServerApplication.class, args);
    }

}
