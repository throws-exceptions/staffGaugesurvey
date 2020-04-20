package com.staffGauge.survey.user.serverImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.user.api.ApiUserService;
import com.staffGauge.survey.user.dal.persistence.UserCodeMapper;
import com.staffGauge.survey.user.dal.persistence.UserMapper;
import com.staffGauge.survey.user.dao.User;
import com.staffGauge.survey.user.utils.Utils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private UserCodeMapper userCodeMapper;

    @Override
    public PageInfo<User> selectAllUsers(User user) {
        PageHelper.startPage(user.getPageNum(), user.getPageSize());
        List<User> userList = userMapper.selectAllUser();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
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
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean register(String mail, String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadImgUrl(head);
        user.setPassword(Utils.MD5(password + user.getSalt()));
        try {
            userMapper.addUser(user.getUserName(), user.getPassword(), user.getSalt(),
                    user.getHeadImgUrl(), "123456", "B", mail);
            logger.debug("注册成功");
            return true;
        } catch (Exception e) {
            logger.error("注册失败" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean updateManager(String admin, String permission, String username) {
        String managerPermission = userMapper.selectPermissionByUserName(admin);
        if (managerPermission.equals("A")) {
            try {
                userMapper.updateInformation(username, null, permission, null, null, null);
                logger.debug("修改权限成功");
                return true;
            } catch (Exception e) {
                logger.error("修改权限失败" + e.getMessage());
            }
        }
        return false;
    }


    @Override
    public boolean updateUser(String oldUsername, String newUsername, String headImgUrl, String phoneNumber, String mail) {

        try {
            userMapper.updateInformation(oldUsername, newUsername, null, headImgUrl, phoneNumber, mail);
            logger.debug("更新用户信息成功");
            return true;
        } catch (Exception e) {
            logger.error("更新用户信息失败" + e.getMessage());
        }
        return false;
    }

    @Override
    public String selectUserCode(String username) {
        String code = "";
        try {
            code = userCodeMapper.selectUserCode(username);
        } catch (Exception e) {
            logger.error("用户获取验证码失败" + e.getMessage());
        }
        return code;
    }

    @Override
    public boolean insertCode(String username, String code) {
        Integer res = 0;
        try {
            res = userCodeMapper.insertCode(username, code);
        } catch (Exception e) {
            logger.error("用户写入验证码失败" + e.getMessage());
        }
        return res == 1 ? true : false;
    }

    @Override
    public boolean updateCode(String username, String code) {
        Integer res = 0;
        try {
            res = userCodeMapper.updateCode(username, code);
        } catch (Exception e) {
            logger.error("用户更新验证码失败" + e.getMessage());
        }
        return res == 1 ? true : false;
    }
}

