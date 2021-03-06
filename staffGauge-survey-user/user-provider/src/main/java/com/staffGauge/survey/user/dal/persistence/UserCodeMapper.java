package com.staffGauge.survey.user.dal.persistence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserCodeMapper {
    Integer insertCode(@Param("username") String username,
                       @Param("code") String code);

    String selectUserCode(@Param("username") String username);

    Integer updateCode(@Param("username") String username,
                       @Param("code") String code);
}
