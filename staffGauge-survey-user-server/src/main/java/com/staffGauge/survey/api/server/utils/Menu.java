package com.staffGauge.survey.api.server.utils;

public enum Menu {
    menu1(1, "首页", "/index", "B"),
    menu2(2, "用户管理", "/user/manger", "A"),
    menu3(3, "识别系统", "/distinguish", "B"),
    menu4(4, "货物个人统计", "/cargo", "B"),
    menu5(5, "货物统计", "/cargoManger", "A");
    private int id;
    private String authName;
    private String path;
    private String permission;

    Menu(int id, String authName, String path, String permission) {
        this.id = id;
        this.authName = authName;
        this.path = path;
        this.permission = permission;
    }

    @Override
    public String toString() {
        return JSONString.getJSONMenuString(id, authName, path);
    }
}
