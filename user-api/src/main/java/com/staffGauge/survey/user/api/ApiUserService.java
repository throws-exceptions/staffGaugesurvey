package com.staffGauge.survey.user.api;

import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.user.dao.User;

import java.util.List;

/**
 * Created by Mr.F on 2020/1/21
 */
public interface ApiUserService {
    /**
     * 分页，查询所有用户列表
     *
     * @return
     */
    PageInfo<User> selectAllUsers(User user);

    /**
     * 查询普通用户列表，普通用户权限即可B
     */
    List<User> selectUser_B();

    /**
     * 根据用户名查找用户
     */
    User selectUserByName(String username);

    /**
     * 注册，权限默认为B
     */
    boolean register(String mail, String username, String password);

    /**
     * 添加/删除管理员，需要管理员操作,根据用户名验证是否是管理员，
     */
    boolean updateManager(String managerName, String permission, String username);

    /**
     * 更新用户信息，id不可改,权限不可改
     */
    boolean updateUser(String oldUsername, String newUsername, String headImgUrl, String phoneNumber, String mail);
    /**
     * 找回密码功能暂定
     */
    /**
     * 查询用户验证码
     */
    String selectUserCode(String username);

    /**
     * 写入用户验证码
     */
    boolean insertCode(String username, String code);

    /**
     * 更新验证码
     */
    boolean updateCode(String username, String code);

}
