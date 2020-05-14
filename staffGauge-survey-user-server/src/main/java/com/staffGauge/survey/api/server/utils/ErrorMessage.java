package com.staffGauge.survey.api.server.utils;

public enum ErrorMessage {
    REGISTER_ERROR("注册失败，服务器异常"),
    LOGIN_ERROR("登录错误，服务器异常"),
    VERIFYCODE_ERROR("发送邮箱失败"),
    USERINFO_ERROR("更新用户信息失败");

    public String message;

    ErrorMessage(String str) {
        this.message = str;
    }

}
