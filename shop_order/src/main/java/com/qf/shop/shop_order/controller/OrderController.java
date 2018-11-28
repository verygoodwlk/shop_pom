package com.qf.shop.shop_order.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

/**
 * @Author ken
 * @Time 2018/11/28 11:35
 * @Version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    /**
     * 进入订单编辑页面
     * @return
     */
    @IsLogin(true)
    @RequestMapping("/edit")
    public String orderEdit(Integer[] goodsid, User user){

        System.out.println("商品的id：" + Arrays.toString(goodsid) + " 当前用户：" + user);

        //当前用户的收货地址
        //当前勾选的购物车信息

        return "orderedit";
    }
}
