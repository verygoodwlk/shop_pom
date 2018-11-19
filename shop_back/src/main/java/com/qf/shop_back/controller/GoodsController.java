package com.qf.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/19 9:48
 * @Version 1.0
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodsService goodsService;

    @RequestMapping("/list")
    public String goodsManager(Model model){
        List<Goods> goods = goodsService.queryAll();
        System.out.println(goods);
        model.addAttribute("goods", goods);
        return "goodslist";
    }


    @RequestMapping("/add")
    public String goodsAdd(Goods goods, MultipartFile gfile){

        //上传图片
        //失败 - 返回错误
        //成功 - 上传的路径->goods->service层->dao->数据库

        return "redirect:/goods/list";
    }
}
