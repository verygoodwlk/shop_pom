package com.qf.shop.shop_cart.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.Cart;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ken
 * @Time 2018/11/27 14:07
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {



    /**
     * 添加购物车 - AOP
     * @return
     */
    @RequestMapping("/add")
    @IsLogin
    public String addCart(Cart cart, User user){
        System.out.println("添加购物车" + cart.getGid() + "  " + cart.getGumber());
        System.out.println("是否登录：" + user);
        return "addsucc";
    }
}
