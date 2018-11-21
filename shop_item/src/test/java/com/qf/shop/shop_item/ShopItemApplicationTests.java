package com.qf.shop.shop_item;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() throws Exception {
        //获得模板对象
        Template template = configuration.getTemplate("hello.ftl");

        //准备数据
        Map<String, Object> map = new HashMap<>();
        map.put("key", "world!");

        map.put("age", 46);

        List<Goods> goodsList = new ArrayList<>();
        goodsList.add(new Goods(1, "商品1", "详情1", null, null, null, null, null));
        goodsList.add(new Goods(2, "商品2", "详情2", null, null, null, null, null));
        goodsList.add(new Goods(3, "商品3", "详情3", null, null, null, null, null));
        map.put("goodslist", goodsList);

        map.put("time", new Date());

        map.put("money", 19809.89);

        map.put("person", null);

        Writer out = new FileWriter("C:\\Users\\ken\\Desktop\\hello.html");
        //通过freemarker生成静态页面
        template.process(map, out);
        out.close();
    }

}
