package com.qf.service;

import com.qf.entity.Orders;
import com.qf.entity.User;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 15:25
 * @Version 1.0
 */
public interface IOrderService {

    int addOrder(Integer aid, Integer[] cids, User user);

    List<Orders> queryOrdersByUid(Integer uid);
}
