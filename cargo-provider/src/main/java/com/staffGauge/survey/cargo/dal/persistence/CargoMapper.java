package com.staffGauge.survey.cargo.dal.persistence;

import com.cargo.dao.Cargo;
import com.staffGauge.survey.cargo.tkmapper.TKMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CargoMapper extends TKMapper<Cargo> {

    List<Cargo> selectAllCargoList();


    List<Cargo> selectCargoByUserName(@Param("person") String person);

    Integer addCargo(@Param("freightersNum") String freightersNum,
                     @Param("startWeight") float startWeight,
                     @Param("endWeight") float endWeight,
                     @Param("goodsWeight") float goodsWeight,
                     @Param("person") String person,
                     @Param("time") String time);


}