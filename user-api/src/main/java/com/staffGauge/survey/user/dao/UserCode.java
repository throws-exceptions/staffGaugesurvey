package com.staffGauge.survey.user.dao;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

@Table(schema = "tb_user_code")
public class UserCode implements Serializable {
    private static final long serialVersionUID = 62800861449940425L;
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    private String code;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
