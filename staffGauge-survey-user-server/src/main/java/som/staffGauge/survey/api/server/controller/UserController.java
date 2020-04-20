package som.staffGauge.survey.api.server.controller;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.user.api.ApiToDoListService;
import com.staffGauge.survey.user.api.ApiUserService;
import com.staffGauge.survey.user.dao.BaseToDoList;
import com.staffGauge.survey.user.dao.User;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import som.staffGauge.survey.api.server.redis.RedisClient;
import som.staffGauge.survey.api.server.service.MailService;
import som.staffGauge.survey.api.server.service.UserService;
import som.staffGauge.survey.api.server.utils.ErrorMessage;
import som.staffGauge.survey.api.server.utils.JSONString;
import som.staffGauge.survey.api.server.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * Created by Mr.F on 2020/1/23
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private MailService mailService;
    @Reference
    private ApiUserService apiUserService;
    private String msg = "msg";
    @Reference
    private ApiToDoListService apiToDoListService;

    /**
     * 注册用户,可行！
     */
    @RequestMapping(path = "/register")
    @PostMapping
    @ResponseBody
    public String register(@RequestBody String request) {
        try {
            //解析JSON数据
            Map<String, String> data = JSONString.parseJson(request);
            String mail = data.get("mail");//注册邮箱
            String username = data.get("username");//用户名
            String password = data.get("password");//密码
            String verifyCode = data.get("Code");
            String test = apiUserService.selectUserCode(username);
            logger.info(data.toString());
            logger.info(test);
            logger.info(verifyCode);

            if (verifyCode.equals(test)) {
                Map<String, Object> map = userService.register(mail, username, password);
                if (map.containsKey("sumsg")) {
                    return JSONString.getJSONString(200, map);
                }
                return JSONString.getJSONString(200, map);
            } else return JSONString.getJSONString(500, "验证失败!");

        } catch (Exception e) {
            logger.error("注册失败" + e.getMessage());
            return JSONString.getJSONString(501, ErrorMessage.REGISTER_ERROR.message);
        }
    }

    @RequestMapping(path = "/verifyCode")
    @PostMapping
    @ResponseBody
    public String getVerifyCode(@RequestBody String request) {
        //未解析json字符串所以导致错误
        Map<String, String> data = JSONString.parseJson(request);
        String email = data.get("mail");
        String username = data.get("username");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为：" + verifyCode;
//        String email = mail.replace("%40", "@");
        try {
            userService.verifyCode(username, verifyCode);
            mailService.sendSimpleMail(email, "注册验证码", message);
        } catch (Exception e) {
            return JSONString.getJSONString(500, ErrorMessage.VERIFYCODE_ERROR.message);
        }
        return JSONString.getJSONString(200, "发送验证码成功！");
    }

    @RequestMapping("/updateCode")
    @PostMapping
    @ResponseBody
    public String updateCode(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String verifyCode = data.get("Code");
        String username = data.get("name");
        String test = apiUserService.selectUserCode(username);
        if (verifyCode.equals(test)) {
            return JSONString.getJSONString(200, "验证成功");
        } else {
            return JSONString.getJSONString(500, "验证失败");
        }
    }

    /**
     * 登录，可行！
     */
    @RequestMapping("/login")
    @PostMapping
    @ResponseBody
    public String login(@RequestBody String request) {
        try {
            Map<String, String> data = JSONString.parseJson(request);
            String username = data.get("username");
            String password = data.get("password");
            String isRemember = data.get("isRemember");
            logger.info(username + " " + password + " " + isRemember);
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("sumsg")) {
                System.out.println(isRemember);
                if (isRemember.equals("1")) {
                    String token = JWTUtils.encode(username, 60 * 1000);
                    System.out.println(token);
                    redisClient.set(token, apiUserService.selectUserByName(username), 3);
                    map.put("token_id", token);
                }
                map.put("token", "");
                return JSONString.getJSONString(200, map);
            }
            return JSONString.getJSONString(500, map);
        } catch (Exception e) {
            logger.error("登录失败" + e.getMessage());
            return JSONString.getJSONString(msg, ErrorMessage.LOGIN_ERROR.message);
        }
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping("/info")
    @GetMapping
    @ResponseBody
    public String getUserInfo(@RequestParam("username") String username) {
        Map<String, Object> map = userService.getUserInfo(username);
        return JSONString.getJSONString(200, map);
    }

    /**
     * 根据权限获取用户菜单
     */
    @RequestMapping("/menus")
    @PostMapping
    @ResponseBody
    public String getMenus(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String permission = data.get("permission");
        String res = userService.getMenu(permission);
        if (StringUtils.isNotBlank(res)) return res;
        else return JSONString.getJSONString(500, "获取列表失败！");
    }

    @RequestMapping("/getUserList")
    @GetMapping
    @ResponseBody
    public PageInfo<User> getUserList(@RequestParam("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize) {
        logger.info(pageNum + " " + pageSize);
        return userService.getUserList(pageNum, pageSize);
    }

    @RequestMapping("/getToDoList")
    @GetMapping
    @ResponseBody
    public PageInfo<BaseToDoList> getToDoList(@RequestParam("username") String username) {
        return apiToDoListService.selectToDoList(username);
    }

    @RequestMapping("/addToDoList")
    @PostMapping
    @ResponseBody
    public String addToDoList(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String username = data.get("username");
        String date = data.get("date");
        String eventTitle = data.get("title");
        String eventDesc = data.get("desc");
        if (apiToDoListService.insertToDoList(username, date, eventTitle, eventDesc))
            return JSONString.getJSONString(200, "添加待办事项成功");
        else return JSONString.getJSONString(500, "添加待办事项失败");

    }

    @RequestMapping("/updateToDoList")
    @PutMapping
    @ResponseBody
    public String updateToDoList(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String username = data.get("username");
        String date = data.get("date");
        String eventTitle = data.get("title");
        String eventDesc = data.get("desc");
        logger.info(date);
        if (apiToDoListService.updateToDoList(username, date, eventTitle, eventDesc))
            return JSONString.getJSONString(200, "更新待办事项成功");
        else return JSONString.getJSONString(500, "更新待办事项失败");

    }

    @RequestMapping("/deleteToDoList")
    @PostMapping
    @ResponseBody
    public String deleteToDoList(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String username = data.get("username");
        String date = data.get("date");
        String eventTitle = data.get("title");
        String eventDesc = data.get("desc");
        logger.info(date);
        if (apiToDoListService.deleteToDoList(username, date, eventTitle, eventDesc))
            return JSONString.getJSONString(200, "删除待办事项成功");
        else return JSONString.getJSONString(500, "删除待办事项失败");

    }

    /**
     * 单点登录，通过调用直接返回用户信息给其他服务
     */
    @RequestMapping("/authentication")
    @GetMapping
    @ResponseBody
    public User authentication(@RequestHeader("token") String token) {
        return redisClient.get(token);
    }

    /**
     * 设置管理员,可行
     */
    @RequestMapping("/suIndex/addManager")
    @PostMapping
    @ResponseBody
    public String setManager(@Param("admin") String admin,
                             @Param("username") String username) {
        try {
            Map<String, Object> map = userService.addManager(admin, username);
            if (map.containsKey("sumsg")) {
                return JSONString.getJSONString(200, map);
            }
            return JSONString.getJSONString(500, map);
        } catch (Exception e) {
            logger.error("添加管理员失败" + e.getMessage());
            return JSONString.getJSONString(msg, "/suIndex/addManager");
        }

    }

    /**
     * 删除管理员，可行
     */
    @RequestMapping("/suIndex/deleteManager")
    @PostMapping
    @ResponseBody
    public String deleteManager(@Param("admin") String admin,
                                @Param("username") String username, Model model) {
        try {
            Map<String, Object> map = userService.deleteManager(admin, username);
            if (map.containsKey("sumsg")) {
                return JSONString.getJSONString(200, map);
            }
            return JSONString.getJSONString(500, map);
        } catch (Exception e) {
            logger.error("删除管理员失败" + e.getMessage());
            return JSONString.getJSONString(msg, "/suIndex/deleteManager");
        }

    }

    /**
     * 更新用户信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateUserInfo")
    @PostMapping
    @ResponseBody
    public String updateMail(@RequestBody String request) {
        try {
            Map<String, String> data = JSONString.parseJson(request);
            String mail = data.get("mail");//注册邮箱
            String oldUsername = data.get("name");//旧用户名
            String newUsername = data.get("newname");
            String headImgUrl = data.get("head");
            boolean flag = apiUserService.updateUser(oldUsername, newUsername, null, null, mail);
            if (flag) {
                return JSONString.getJSONString(200, "更新用户信息成功");
            }
            return JSONString.getJSONString(500, "更新用户信息失败");


        } catch (Exception e) {
            logger.error("更新用户信息失败" + e.getMessage());
            return JSONString.getJSONString(501, ErrorMessage.REGISTER_ERROR.message);
        }
    }

    /**
     * 上传头像
     */
    @RequestMapping("/up")
    @PostMapping
    @ResponseBody
    public String up(@RequestParam("file") MultipartFile picture, @RequestParam("username") String username, HttpServletRequest request) {
        //获取文件在服务器的储存位置
        String path = "/home/diamond/桌面/staffGaugesurvey/vue-manger/src/assets/avator";
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            System.out.println(path + fileName);
            //将文件在服务器的存储路径返回
            if (apiUserService.updateUser(username, null, fileName, null, null)) {
                return JSONString.getJSONString(200, "更新成功");
            }
            return JSONString.getJSONString(500, "更新失败");
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return JSONString.getJSONString(500, "上传失败");
        }

    }

}
