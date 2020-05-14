package com.staffGauge.survey.user.tkmapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
/**
 * Created by Mr.F on 2020/1/23
 */
@org.apache.ibatis.annotations.Mapper
public interface TKMapper<T> extends Mapper<T>,MySqlMapper<T>{
}
