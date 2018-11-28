package com.qf.service;

import com.qf.entity.Cart;
import com.qf.entity.User;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 9:02
 * @Version 1.0
 */
public interface ICartService {


    String addCart(Cart cart, User user, String cartToken);

    List<Cart> cartList(String cartToken, User user);
}
