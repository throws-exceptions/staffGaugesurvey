package com.staffGauge.survey.cargo.controller;

import com.staffGauge.survey.user.dao.User;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.spring.ServiceBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mr.F on 2020/2/6
 */
public class CargoController {

    @RequestMapping("/")
    @GetMapping
    @ResponseBody
    public String cargoList(HttpServletRequest request){
        User user= (User) request.getAttribute("user");
        //这样可拿到user
        return null;
    }
}
