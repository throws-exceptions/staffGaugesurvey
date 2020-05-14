package com.cargo.api;

import com.cargo.dao.Cargo;
import com.github.pagehelper.PageInfo;

/**
 * Created by Mr.F on 2020/1/21
 */
public interface ApiCargoService {
    /**
     * 分页，查询所有用户列表
     *
     * @return
     */
    PageInfo<Cargo> selectAllCargoList(Cargo cargo);

    PageInfo<Cargo> selectCargoList(Cargo cargo);

    boolean insertCargolList(Cargo cargo);


}
