package com.staffGauge.survey.cargo;

import com.staffGauge.survey.cargo.filter.CargoFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.F on 2020/2/6
 */
@SpringBootApplication
public class CargoServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CargoServerApplication.class,args);
    }
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        CargoFilter cargoFilter=new CargoFilter();
        filterRegistrationBean.setFilter(cargoFilter);
        List<String> urlPatterns=new ArrayList<>();
        urlPatterns.add("/*");
        filterRegistrationBean.setUrlPatterns(urlPatterns);
        return filterRegistrationBean;
    }
}
