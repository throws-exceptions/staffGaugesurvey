package com.staffGauge.survey.api.server.controller;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.api.server.redis.RedisClient;
import com.staffGauge.survey.api.server.service.MailService;
import com.staffGauge.survey.api.server.utils.*;
import com.staffGauge.survey.user.api.ApiToDoListService;
import com.staffGauge.survey.user.api.ApiUserService;
import com.staffGauge.survey.user.dao.BaseToDoList;
import com.staffGauge.survey.user.dao.User;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr.F on 2020/1/23
 */
@Controller
@RequestMapping(path = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
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
     * 注册用户,可行！调试完毕！
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
            String verifyCode = data.get("Code");// 前端传入的验证码
            String test = apiUserService.selectUserCode(username);//后端获取的验证码
            if (verifyCode.equals(test)) {//验证码相等，进行注册
                if (apiUserService.selectUserByName(username) != null) {
                    return JSONString.getJSONString(500, "用户名已经存在");
                }
                boolean flag = apiUserService.register(mail, username, password);
                if (flag) return JSONString.getJSONString(200, "注册成功");
                else return JSONString.getJSONString(500, "Dubbo服务出错");
            } else return JSONString.getJSONString(500, "验证失败!");
        } catch (Exception e) {
            logger.error("注册失败" + e.getMessage());
            return JSONString.getJSONString(501, ErrorMessage.REGISTER_ERROR.message);
        }
    }

    /**
     * 发送验证码，调试完毕
     *
     * @param request
     * @return
     */
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
        try {
            String resCode = apiUserService.selectUserCode(username);
            if (StringUtils.isNotBlank(resCode)) {
                apiUserService.updateCode(username, verifyCode);
                logger.info("用户更新验证码");
            } else {
                logger.info("用户写入验证码");
                apiUserService.insertCode(username, verifyCode);
            }
            mailService.sendSimpleMail(email, "注册验证码", message);
        } catch (Exception e) {
            return JSONString.getJSONString(500, ErrorMessage.VERIFYCODE_ERROR.message);
        }
        return JSONString.getJSONString(200, "发送验证码成功！");
    }

    /**
     * 更新验证码，调试完毕
     *
     * @param request
     * @return
     */
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
            System.out.println(isRemember);
            Map<String, Object> map = new ConcurrentHashMap<>();
            if (StringUtils.isBlank(username)) {
                return JSONString.getJSONString(500, "用户名不能为空");
            }
            if (StringUtils.isBlank(password)) {
                return JSONString.getJSONString(500, "密码不能为空");
            }
            User user = apiUserService.selectUserByName(username);
            if (user == null) {
                return JSONString.getJSONString(500, "用户名不存在，请先注册！");
            }
            if (!Utils.MD5(password + user.getSalt()).equals(user.getPassword())) {
                return JSONString.getJSONString(500, "密码不正确！");
            }
            map.put("username", username);
            map.put("head", user.getHeadImgUrl());
            map.put("mail", user.getMail());
            map.put("permission", user.getPermission());
            if (isRemember.equals("1")) {
                String token = JWTUtils.encode(username, 60 * 1000);
                //Todo redis
//                redisClient.set(token, apiUserService.selectUserByName(username), 600);
                map.put("token_id", token);
            }
            return JSONString.getJSONString(200, map);
        } catch (Exception e) {
            logger.error("登录失败" + e.getMessage());
            return JSONString.getJSONString(msg, ErrorMessage.LOGIN_ERROR.message);
        }
    }

    /**
     * 获取用户信息,调试成功
     *
     * @param username
     * @return
     */
    @RequestMapping("/info")
    @GetMapping
    @ResponseBody
    public String getUserInfo(@RequestParam("username") String username) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        User user = apiUserService.selectUserByName(username);
        if (user == null) return JSONString.getJSONString(500, "用户不存在");
        map.put("username", username);
        map.put("head", user.getHeadImgUrl());
        map.put("mail", user.getMail());
        map.put("permission", user.getPermission());
        return JSONString.getJSONString(200, map);
    }

    /**
     * 根据权限获取用户菜单,调试完毕
     */
    @RequestMapping("/menus")
    @PostMapping
    @ResponseBody
    public String getMenus(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String permission = data.get("permission");
        String menu1 = Menu.menu1.toString();
        String menu2 = Menu.menu2.toString();
        String menu3 = Menu.menu3.toString();
        String menu4 = Menu.menu4.toString();
        String menu5 = Menu.menu5.toString();
        if (permission.equals("A")) {
            return "[" + menu1 + "," + menu2 + "," + menu3 + "," + menu4 + "," + menu5 + "]";
        } else if (permission.equals("B")) {
            return "[" + menu1 + "," + menu3 + "," + menu4 + "]";
        } else return JSONString.getJSONString(500, "获取列表失败！");
    }

    /**
     * 分页查询用户列表，调试完毕
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/getUserList")
    @GetMapping
    @ResponseBody
    public PageInfo<User> getUserList(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        User user = new User();
        user.setPageNum(pageNum);
        user.setPageSize(pageSize);
        return apiUserService.selectAllUsers(user);
    }

    /**
     * 获取代办事项列表，调试完毕
     *
     * @param username
     * @return
     */
    @RequestMapping("/getToDoList")
    @GetMapping
    @ResponseBody
    public PageInfo<BaseToDoList> getToDoList(@RequestParam("username") String username) {
        return apiToDoListService.selectToDoList(username);
    }

    /**
     * 添加代办事项，调试完毕
     *
     * @param request
     * @return
     */
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

    /**
     * 更新代办事项列表，调试完毕
     *
     * @param request
     * @return
     */
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

    /**
     * 删除代办事项列表
     *
     * @param request
     * @return
     */
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
     * TODO 单点登录，通过调用直接返回用户信息给其他服务
     */
    @RequestMapping("/authentication")
    @GetMapping
    @ResponseBody
    public User authentication(@RequestHeader("token") String token) {
        return redisClient.get(token);
    }

    /**
     * 设置管理员,调试完毕
     */
    @RequestMapping("/addManager")
    @PostMapping
    @ResponseBody
    public String setManager(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String admin = data.get("admin");
        String username = data.get("username");
        try {
            boolean flag = apiUserService.updateManager(admin, "A", username);
            if (flag) {
                return JSONString.getJSONString(200, "添加管理员成功");
            }
            return JSONString.getJSONString(500, "添加管理员失败");
        } catch (Exception e) {
            logger.error("添加管理员失败" + e.getMessage());
            return JSONString.getJSONString(500, "添加管理员失败");
        }

    }

    /**
     * 删除管理员，调试完毕
     */
    @RequestMapping("/deleteManager")
    @PostMapping
    @ResponseBody
    public String deleteManager(@RequestBody String request) {
        Map<String, String> data = JSONString.parseJson(request);
        String admin = data.get("admin");
        String username = data.get("username");
        try {
            boolean flag = apiUserService.updateManager(admin, "B", username);
            if (flag) {
                return JSONString.getJSONString(200, "删除管理员成功");
            }
            return JSONString.getJSONString(500, "删除管理员失败");
        } catch (Exception e) {
            logger.error("删除管理员失败" + e.getMessage());
            return JSONString.getJSONString(500, "删除管理员失败");
        }

    }

    /**
     * 更新用户信息，调试完毕
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
     * 上传头像，调试完毕
     */
    @RequestMapping("/up")
    @PostMapping
    @ResponseBody
    public String up(@RequestParam("file") MultipartFile picture, @RequestParam("username") String username, HttpServletRequest request) {
        //获取文件在服务器的储存位置
        String path = "/home/diamond/桌面/staffGaugesurvey/vue-manger/src/assets/avator";
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
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
