package com.qf.shop.dao;

import com.qf.entity.Cart;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 9:05
 * @Version 1.0
 */
public interface ICartDao {

    int addCart(Cart cart);

    List<Cart> queryCartsByUid(Integer uid);
}
