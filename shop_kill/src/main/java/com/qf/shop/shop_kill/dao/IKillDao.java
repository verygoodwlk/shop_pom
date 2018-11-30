package com.qf.shop.shop_kill.dao;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    int saveOrders(Orders orders);
}
