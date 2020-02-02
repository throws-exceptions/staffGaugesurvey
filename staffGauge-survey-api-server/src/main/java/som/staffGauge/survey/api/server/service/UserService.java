package som.staffGauge.survey.api.server.service;

import com.staffGauge.survey.user.api.ApiUserService;
import com.staffGauge.survey.user.dao.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import som.staffGauge.survey.api.server.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Mr.F on 2020/1/24
 */
@Service
public class UserService {
    @Reference
    private ApiUserService apiUserService;

    public Map<String,Object> register(String username,String password){
        Map<String,Object> map=new ConcurrentHashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        if(apiUserService.selectUserByName(username)!=null){
            map.put("msg","用户名已经存在");
            return map;
        }
        boolean flag=apiUserService.register(username,password);
        if(flag)map.put("sumsg","注册成功");
        else map.put("msg","Dubbo服务出错");
        return map;

    }
    public Map<String,Object> login(String username,String password){
        Map<String,Object> map=new ConcurrentHashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }
        User user=apiUserService.selectUserByName(username);
        if(user==null){
            map.put("msg","用户名不存在，请先注册！");
            return map;
        }
        if(!Utils.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg" ,"密码不正确！");
            return map;
        }
        map.put("sumsg","登陆成功");
        map.put("username",username);
        map.put("head",user.getHeadImgUrl());
        map.put("permission",user.getPermission());
        return map;


    }
    public Map<String,Object> addManager(String managerName,String username){
        Map<String,Object> map=new ConcurrentHashMap<>();
        boolean flag=apiUserService.updateManager(managerName,"A",username);
        if(flag){
            map.put("sumsg","添加管理员成功");
            return map;
        }
        map.put("msg","请重试");
        return map;
    }
    public Map<String,Object> deleteManager(String managerName,String username){
        Map<String,Object> map=new ConcurrentHashMap<>();
        boolean flag=apiUserService.updateManager(managerName,"B",username);
        if(flag){
            map.put("sumsg","删除管理员成功");
            return map;
        }
        map.put("msg","请重试");
        return map;
    }

    /**
     * todo修改用户信息
     * @param username
     * @return
//     */
//    public Map<String,Object> updateUserInfo(String username){
//        apiUserService.updateUser()
//    }
}
