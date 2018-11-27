package com.qf.shop.shop_sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author ken
 * @Time 2018/11/27 9:27
 * @Version 1.0
 */
@Controller
@RequestMapping("/sso")
public class SSOController {

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/tologin")
    public String toLogin(String returnUrl, Model model){
        model.addAttribute("returnUrl", returnUrl);
        return "login";
    }

    /**
     * 登录
     * http://localhost:8084/sso/login
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpServletResponse response, String returnUrl){

        //调用service层进行登录
        ResultData<User> data = userService.login(username, password);
        switch (data.getCode()){
            case 100:
                //登录成功！！！！

                if(returnUrl == null || "".equals(returnUrl)){
                    returnUrl = "http://localhost:8081";
                }

                System.out.println("登录后，最终要去的位置：" + returnUrl);

                //生成一个uuid
                String token = UUID.randomUUID().toString();
                //将用户信息放入redis中
                redisTemplate.opsForValue().set(token, data.getData());
                redisTemplate.expire(token, 7, TimeUnit.DAYS);
                //将uuid写入浏览器的cookie中
                Cookie cookie = new Cookie(Constact.LOGIN_TOKEN_NAME, token);
                cookie.setMaxAge(60 * 60 * 24 * 7);//设置cookie的有效期

                cookie.setPath("/");//表示cookie的路径范围 -
//                cookie.setDomain("jd.com");//表示cookie的域名范围 www.jd.com  cart.js.com search.jd.com
//                cookie.setHttpOnly(true);//如果设置为true，表示cookie不能别浏览器脚本读取，比如js
//                cookie.setSecure(true);//如果设置为true，该cookie只有在https协议下才能参数，在http协议下不会传输该cookie

                response.addCookie(cookie);

                break;
            default:
                model.addAttribute("error", data);
                return "login";
        }
        return "redirect:" + returnUrl;
    }

    /**
     * 验证是否登录成功
     * @return
     */
    @RequestMapping("/islogin")
    @ResponseBody
    public String checkLogin(@CookieValue(value = Constact.LOGIN_TOKEN_NAME, required = false) String token){
        User user = null;

        if(token != null){
            //有可能已经登录
            user = (User) redisTemplate.opsForValue().get(token);
        }

        return user != null ? "islogin('" + new Gson().toJson(user) + "')" : "islogin(null)";
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping("/logout")
    public String logout(@CookieValue(value = Constact.LOGIN_TOKEN_NAME, required = false) String token, HttpServletResponse response){
        if(token != null){
            //清空redis
            redisTemplate.delete(token);
            //删除cookie
            Cookie cookie = new Cookie(Constact.LOGIN_TOKEN_NAME, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "login";
    }
}
