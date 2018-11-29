package com.qf.shop.dao;

import com.qf.entity.OrderDetils;
import com.qf.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 15:27
 * @Version 1.0
 */
public interface IOrderDao {

    int addOrder(Orders orders);

    int addOrderDetils(@Param("orderdetils") List<OrderDetils> orderDetils);

    List<Orders> queryByUid(Integer uid);

    Orders queryByOrderid(String orderid);

    int updateOrderStatus(@Param("orderid") String orderid, @Param("status") int status);
}
