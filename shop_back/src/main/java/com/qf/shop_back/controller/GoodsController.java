package com.qf.shop_back.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.gson.Gson;
import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import com.qf.util.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${image.path}")
    private String spath;

    @RequestMapping("/list")
    public String goodsManager(Model model){
        List<Goods> goods = goodsService.queryAll();
        model.addAttribute("goods", goods);
        model.addAttribute("spath", spath);
        return "goodslist";
    }


    @RequestMapping("/add")
    public String goodsAdd(Goods goods, MultipartFile gfile) throws IOException {

        //上传图片
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                gfile.getInputStream(),
                gfile.getSize(),
                "jpg", null);
        String path = result.getFullPath();

        //成功 - 上传的路径->goods->service层->dao->数据库
        goods.setGimage(path);

        //调用service保存商品
        goods = goodsService.saveGoods(goods);

        //添加商品成功之后，调用搜索工程的接口，同步到索引库中 - httpclient
        HttpClientUtil.sendJson("http://localhost:8082/search/add", new Gson().toJson(goods));
        HttpClientUtil.sendJson("http://localhost:8083/item/createhtml", new Gson().toJson(goods));

        return "redirect:/goods/list";
    }

    /**
     * 查询新品上架
     * @return
     */
//    @RequestMapping("/newlist")
//    @ResponseBody
//    public String queryNewGoods(boolean flag){
//        List<Goods> goods = goodsService.queryNewGoods();
//        System.out.println("查询新品列表：" + goods);
//        return flag ? "hello(" + new Gson().toJson(goods) + ")" : new Gson().toJson(goods);
//    }


    /**
     * @CrossOrigin 实现跨域
     * @param flag
     * @return
     */
    @RequestMapping("/newlist")
    @ResponseBody
    @CrossOrigin
    public List<Goods> queryNewGoods(boolean flag){
        List<Goods> goods = goodsService.queryNewGoods();
        System.out.println("查询新品列表：" + goods);
        return goods;
    }
}
