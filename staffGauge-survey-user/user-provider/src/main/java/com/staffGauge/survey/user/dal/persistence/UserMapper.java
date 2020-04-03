package com.staffGauge.survey.user.dal.persistence;

import com.github.pagehelper.PageHelper;
import com.staffGauge.survey.user.dao.User;
import com.staffGauge.survey.user.tkmapper.TKMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;


import javax.validation.constraints.Email;
import java.util.List;
@Mapper
public interface UserMapper extends TKMapper<User> {
    List<User> selectUsers(@Param("permission")String permission);
    List<User> selectAllUser();
    String selectPermissionByUserName(@Param("username")String username);
    Integer addUser(@Param("userName")String userName,
                    @Param("password")String password,
                    @Param("salt")String salt,
                    @Param("headImgUrl") String headImgUrl,
                    @Param("phoneNumber")String phoneNumber,
                    @Param("permission")String permission,
                    @Param("mail")String mail);
    Integer updateInformation(@Param("userName")String userName,
                   @Param("permission")String permission,
                   @Param("headImgUrl")String headImgUrl,
                   @Param("phoneNumber")String phoneNumber);
    User selectUserByName(@Param("username")String username);



}