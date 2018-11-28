package com.qf.shop.dao;

import com.qf.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 14:03
 * @Version 1.0
 */
public interface IAddressDao {

    List<Address> queryByUid(Integer uid);

    int insert(@Param("address") Address address);

    Address queryById(Integer id);
}
