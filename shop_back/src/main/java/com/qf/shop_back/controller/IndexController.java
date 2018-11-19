package com.qf.shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ken
 * @Time 2018/11/19 9:46
 * @Version 1.0
 */
@Controller
public class IndexController {

    /**
     * 统一的页面跳转
     * @param page
     * @return
     */
    @RequestMapping("/topage/{page}")
    public String toPage(@PathVariable("page") String page){
        return page;
    }
}
