package som.staffGauge.survey.api.server.utils;

public enum Menu {
    menu1(1,"个人信息","/user/Info","B"),
    menu2(2,"用户管理","/user/manger","A"),
    menu3(3,"识别系统","/distinguish","B"),
    menu4(4,"设备状态查询","/device/status","B"),
    menu5(5,"设备状态管理","/device/manger","A");
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
        return JSONString.getJSONMenuString(id,authName,path);
    }
}
