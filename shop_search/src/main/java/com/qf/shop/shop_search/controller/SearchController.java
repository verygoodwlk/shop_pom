package com.qf.shop.shop_search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.entity.Goods;
import com.qf.entity.PageSolr;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ken
 * @Time 2018/11/20 10:31
 * @Version 1.0
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Reference
    private ISearchService searchService;

    @Value("${image.path}")
    private String path;

    /**
     * 搜索商品列表
     * @return
     */
    @RequestMapping("/list")
    public String search(String keyword, Model model, PageSolr<Goods> pageSolr){
//        List<Goods> goods = searchService.queryIndex(keyword);
        pageSolr = searchService.queryIndexPage(keyword, pageSolr);
        model.addAttribute("pageSolr", pageSolr);
        model.addAttribute("path", path);
        model.addAttribute("keyword", keyword);
        return "searchlist";
    }

    /**
     * 添加索引
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public String addIndex(@RequestBody Goods goods){
        //同步索引库
        int reslut = searchService.addIndex(goods);
        if(reslut == 1){
            return "succ";
        }
        return "error";
    }
}
