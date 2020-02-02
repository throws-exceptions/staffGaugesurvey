package som.staffGauge.survey.api.server.controller;

import com.auth0.jwt.internal.org.apache.commons.lang3.StringUtils;
import com.staffGauge.survey.user.dao.User;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import som.staffGauge.survey.api.server.service.UserService;
import som.staffGauge.survey.api.server.utils.JSONString;
import som.staffGauge.survey.api.server.utils.JWTUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr.F on 2020/1/23
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    private String re="redirect";//跳转界面
    /**
     * 注册用户
     */
    @RequestMapping(path = "/register")
    @PostMapping
    @ResponseBody
    public String register(Model model, @RequestParam("username")String username,
                          @RequestParam("password")String password,
                          @RequestParam(value = "isRemember",defaultValue = "false") boolean isRemember
                          ) {
        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("sumsg")) {
                if (isRemember) {
                    String token = JWTUtils.encode(username, 60 * 1000);
                    map.put("token", token);
                }
                map.put(re,"/index");
                return JSONString.getJSONString(200,map);
            }
            return JSONString.getJSONString(500,map);
        }catch (Exception e){
            logger.error("注册失败"+e.getMessage());
            model.addAttribute("msg","服务器错误，注册失败");
            return JSONString.getJSONString(re,"/register");
        }
    }
    /**
     *登录
     */
    @RequestMapping("/login")
    @GetMapping
    @ResponseBody
    public String login(@Param("username")String username,
                        @Param("password")String password,
                        @RequestParam(value = "isRemember",defaultValue = "false") boolean isRemember) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("sumsg")) {
                if (isRemember) {
                    String token = JWTUtils.encode(username, 60 * 1000);
                    map.put("token", token);
                }
                if (map.get("permission").equals("A")) {
                    map.put(re, "/spIndex");
                }
                map.put(re, "/index");
                return JSONString.getJSONString(200, map);
            }
            return JSONString.getJSONString(500, map);
        }catch (Exception e){
            logger.error("登录失败"+e.getMessage());
            return JSONString.getJSONString(re,"/login");
        }
    }
    /**
     * 退出当前用户登录
     */
    @RequestMapping("/logout")
    @GetMapping
    @ResponseBody
    public String logout(){
        return JSONString.getJSONString(re,"/index");
    }
    /**
     * 设置管理员
     */
    @RequestMapping("/suIndex/addManager")
    @PostMapping
    @ResponseBody
    public String setManager(@Param("mangerName")String mangerName,
                             @Param("username")String username,Model model){
        try{
            Map<String,Object> map=userService.addManager(mangerName,username);
            if(map.containsKey("sumsg")){
                return JSONString.getJSONString(200,map);
            }
            return JSONString.getJSONString(500,map);
        }catch (Exception e){
            logger.error("添加管理员失败"+e.getMessage());
            model.addAttribute("msg","服务器错误，添加失败");
            return JSONString.getJSONString(re,"/suIndex/addManager");
        }

    }
    /**
     * 设置管理员
     */
    @RequestMapping("/suIndex/deleteManager")
    @PostMapping
    @ResponseBody
    public String deleteManager(@Param("mangerName")String mangerName,
                             @Param("username")String username,Model model){
        try{
            Map<String,Object> map=userService.deleteManager(mangerName,username);
            if(map.containsKey("sumsg")){
                return JSONString.getJSONString(200,map);
            }
            return JSONString.getJSONString(500,map);
        }catch (Exception e){
            logger.error("删除管理员失败"+e.getMessage());
            model.addAttribute("msg","服务器错误，删除失败");
            return JSONString.getJSONString(re,"/suIndex/deleteManager");
        }

    }

}
