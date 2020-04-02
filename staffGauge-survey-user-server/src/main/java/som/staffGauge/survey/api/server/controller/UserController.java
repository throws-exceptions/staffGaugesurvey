package som.staffGauge.survey.api.server.controller;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.staffGauge.survey.user.api.ApiUserService;
import com.staffGauge.survey.user.dao.User;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.remoting.transport.netty.NettyClient;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.protocol.dubbo.DubboInvoker;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import som.staffGauge.survey.api.server.redis.RedisClient;
import som.staffGauge.survey.api.server.service.MailService;
import som.staffGauge.survey.api.server.service.UserService;
import som.staffGauge.survey.api.server.utils.ErrorMessage;
import som.staffGauge.survey.api.server.utils.JSONString;
import som.staffGauge.survey.api.server.utils.JWTUtils;
import som.staffGauge.survey.api.server.utils.Menu;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr.F on 2020/1/23
 */
@Controller
@RequestMapping("/user")
@CrossOrigin
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
            String verifyCode=data.get("Code");
            String test=apiUserService.selectUserCode(username);
            logger.info(data.toString());
            logger.info(test);
            logger.info(verifyCode);

            if(verifyCode.equals(test)) {
                Map<String, Object> map = userService.register(mail, username, password);
                if (map.containsKey("sumsg")) {
                    return JSONString.getJSONString(200, map);
                }
                return JSONString.getJSONString(200, map);
            }else return JSONString.getJSONString(500,"验证失败!");

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
        Map<String,String> data=JSONString.parseJson(request);
        String email=data.get("mail");
        String username=data.get("username");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        String message = "您的注册验证码为：" + verifyCode;
//        String email = mail.replace("%40", "@");
        try {
            userService.verifyCode(username,verifyCode);
            mailService.sendSimpleMail(email, "注册验证码", message);
        } catch (Exception e) {
            return JSONString.getJSONString(500,ErrorMessage.VERIFYCODE_ERROR.message);
        }
        return JSONString.getJSONString(200,"发送验证码成功！");
    }

    /**
     *登录，可行！
     */
    @RequestMapping("/login")
    @PostMapping
    @ResponseBody
    public String login(@RequestBody String request) {
        try {
            Map<String, String> data = JSONString.parseJson(request);
            String username=data.get("username");
            String password=data.get("password");
            String isRemember=data.get("isRemember");
            logger.info(username +" "+ password+" "+isRemember);
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("sumsg")) {
                System.out.println(isRemember);
                if (isRemember.equals("1")) {
                    String token = JWTUtils.encode(username, 60 * 1000);
                    System.out.println(token);
                    redisClient.set(token,apiUserService.selectUserByName(username),3);
                    map.put("token_id", token);
                }
                map.put("token","");
                return JSONString.getJSONString(200, map);
            }
            return JSONString.getJSONString(500, map);
        }catch (Exception e){
            logger.error("登录失败"+e.getMessage());
            return JSONString.getJSONString(msg,ErrorMessage.LOGIN_ERROR.message);
        }
    }

    /**
     * 获取用户信息
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/info")
    @GetMapping
    @ResponseBody
    public String getUserInfo(@Param("username")String username,
                              @Param("password")String password){
        Map<String,Object> map=userService.getUserInfo(username, password);
        return JSONString.getJSONString(200,map);
    }
    /**
     * 根据权限获取用户菜单
     */
    @RequestMapping("/menus")
    @PostMapping
    @ResponseBody
    public String getMenus(@RequestBody String request){
        Map<String,String> data=JSONString.parseJson(request);
        String permission=data.get("permission");
        String res=userService.getMenu(permission);
        if(StringUtils.isNotBlank(res))return res;
        else return JSONString.getJSONString(500,"获取列表失败！");
    }
    /**
     * 单点登录，通过调用直接返回用户信息给其他服务
     */
    @RequestMapping("/authentication")
    @GetMapping
    @ResponseBody
    public User authentication(@RequestHeader("token")String token){
        return redisClient.get(token);
    }
    /**
     * 设置管理员,可行
     */
    @RequestMapping("/suIndex/addManager")
    @PostMapping
    @ResponseBody
    public String setManager(@Param("admin")String admin,
                             @Param("username")String username){
        try{
            Map<String,Object> map=userService.addManager(admin,username);
            if(map.containsKey("sumsg")){
                return JSONString.getJSONString(200,map);
            }
            return JSONString.getJSONString(500,map);
        }catch (Exception e){
            logger.error("添加管理员失败"+e.getMessage());
            return JSONString.getJSONString(msg,"/suIndex/addManager");
        }

    }
    /**
     * 删除管理员，可行
     */
    @RequestMapping("/suIndex/deleteManager")
    @PostMapping
    @ResponseBody
    public String deleteManager(@Param("admin")String admin,
                             @Param("username")String username,Model model){
        try{
            Map<String,Object> map=userService.deleteManager(admin,username);
            if(map.containsKey("sumsg")){
                return JSONString.getJSONString(200,map);
            }
            return JSONString.getJSONString(500,map);
        }catch (Exception e){
            logger.error("删除管理员失败"+e.getMessage());
            return JSONString.getJSONString(msg,"/suIndex/deleteManager");
        }

    }

}
