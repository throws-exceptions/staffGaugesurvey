package com.staffGauge.survey.cargo.filter;

import com.staffGauge.survey.user.client.LoginFilter;
import com.staffGauge.survey.user.dao.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mr.F on 2020/2/6
 */
public class CargoFilter extends LoginFilter {
    @Override
    protected void login(HttpServletRequest request, HttpServletResponse response, User user) {
        request.setAttribute("user",user);
    }
}
