package com.staffGauge.survey.user.serverImpl;

import com.staffGauge.survey.user.api.ApiUserService;

import com.staffGauge.survey.user.dal.persistence.UserMapper;
import com.staffGauge.survey.user.dao.User;

import com.staffGauge.survey.user.utils.Message;
import com.staffGauge.survey.user.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Mr.F on 2020/1/23
 */

@Service
public class UserServiceImpl implements ApiUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUsers() {
        List<User> userList=userMapper.selectUsers("A");
        List<User> userList1=userMapper.selectUsers("B");
        userList.addAll(userList1);
        return userList;
    }

    @Override
    public List<User> selectUser_B() {

        return userMapper.selectUsers("B");
    }

    @Override
    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    /**
     * 手机号功能暂时未开发
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean register(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadImgUrl(head);
        user.setPassword(Utils.MD5(password+user.getSalt()));
        try {
            userMapper.addUser(user.getUserName(),user.getPassword(),user.getSalt(),
                    user.getHeadImgUrl(),"123456","B");
            logger.debug("注册成功");
            return true;
        }catch (Exception e){
            logger.error("注册失败"+e.getMessage());
            return false;
        }

    }

    @Override
    public boolean updateManager(String admin, String permission,String username) {
        String managerPermission=userMapper.selectPermissionByUserName(admin);
        if(managerPermission.equals("A")){
            try {
                userMapper.updateInformation(username,permission,null,null);
                logger.debug("修改权限成功");
                return true;
            }catch (Exception e){
                logger.error("修改权限失败"+e.getMessage());
            }
        }
        return false;
    }


    @Override
    public boolean updateUser(String userName,String headImgUrl, String phoneNumber) {

        if(StringUtils.isBlank(userName))
        {
            logger.error("当前用户未登录");
            return false;
        }
        try{
            userMapper.updateInformation(userName,null,headImgUrl,phoneNumber);
            logger.debug("更新用户信息成功");
            return true;
        }catch(Exception e){
            logger.error("更新用户信息失败"+e.getMessage());
        }
        return false;
    }
}

