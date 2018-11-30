package com.qf.shop.shop_kill.dao;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/30 9:26
 * @Version 1.0
 */
@Mapper
public interface IKillDao {

    /**
     * 根据id查询秒杀商品信息
     * @param id
     * @return
     */
    Kill queryKillInfo(Integer id);

    int updateKillSave(@Param("id") Integer id, @Param("number") Integer number);

    int updateKillSaveLG(@Param("id") Integer id, @Param("number") Integer number, @Param("version") Integer version);

    int updateKillSaveLG2(@Param("id") Integer id, @Param("number") Integer number);

    int updateKill(@Param("id") Integer id, @Param("save") Integer save);

    int saveOrders(Orders orders);

    int saveOrdersList(@Param("orderslist") List<Orders> ordersList);
}
