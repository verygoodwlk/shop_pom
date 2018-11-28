package com.qf.shop.shop_cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.aop.IsLogin;
import com.qf.entity.Cart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.util.Constact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/27 14:07
 * @Version 1.0
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference
    private ICartService cartService;

    /**
     * 添加购物车 - AOP
     * @return
     */
    @RequestMapping("/add")
    @IsLogin
    public String addCart(
            @CookieValue(value = Constact.CART_TOKEN_NAME, required = false)
                    String cart_key,
            Cart cart,
            User user,
            HttpServletResponse response){

        cart_key = cartService.addCart(cart, user, cart_key);

        if(cart_key != null){
            Cookie cookie = new Cookie(Constact.CART_TOKEN_NAME, cart_key);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
        }

        return "addsucc";
    }

    /**
     * 获得购物车列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    @IsLogin
    public String cartList(
            @CookieValue(value = Constact.CART_TOKEN_NAME, required = false) String cart_key,
            User user){

        List<Cart> carts = cartService.cartList(cart_key, user);

        return "getCarts(" + new Gson().toJson(carts) + ")";
    }

    /**
     * 展示购物车列表
     * @return
     */
    @RequestMapping("/showlist")
    @IsLogin
    public String cartShowList(@CookieValue(value = Constact.CART_TOKEN_NAME, required = false) String cart_key,
                               User user, Model model){

        List<Cart> carts = cartService.cartList(cart_key, user);
        model.addAttribute("carts", carts);

        return "cartlist";
    }
}
