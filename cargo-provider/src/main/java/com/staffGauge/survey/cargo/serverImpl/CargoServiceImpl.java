package com.staffGauge.survey.cargo.serverImpl;

import com.cargo.api.ApiCargoService;
import com.cargo.dao.Cargo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.staffGauge.survey.cargo.dal.persistence.CargoMapper;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Mr.F on 2020/1/23
 */

@Service
public class CargoServiceImpl implements ApiCargoService {
    private static final Logger logger = LoggerFactory.getLogger(CargoServiceImpl.class);
    @Autowired
    private CargoMapper cargoMapper;


    @Override
    public PageInfo<Cargo> selectAllCargoList(Cargo cargo) {
        PageHelper.startPage(cargo.getPageNum(), cargo.getPageSize());
        List<Cargo> cargoList = cargoMapper.selectAllCargoList();
        PageInfo<Cargo> pageInfo = new PageInfo<>(cargoList);
        return pageInfo;
    }

    @Override
    public PageInfo<Cargo> selectCargoList(Cargo cargo) {
        PageHelper.startPage(cargo.getPageNum(), cargo.getPageSize());
        System.out.println(cargo.getPerson());
        List<Cargo> cargoList = cargoMapper.selectCargoByUserName(cargo.getPerson());
        PageInfo<Cargo> pageInfo = new PageInfo<>(cargoList);
        return pageInfo;
    }

    @Override
    public boolean insertCargolList(Cargo cargo) {
        String freightersNum = cargo.getFreightersNum();
        float startWeight = cargo.getStartWeight();
        float endWeight = cargo.getEndWeight();
        float goodsWeight = cargo.getGoodsWeight();
        String person = cargo.getPerson();
        String time = cargo.getTime();
        try {
            cargoMapper.addCargo(freightersNum, startWeight, endWeight, goodsWeight, person, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

