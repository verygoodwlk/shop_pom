package com.qf.shop.shop_item;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author ken
 * @Time 2018/11/21 14:32
 * @Version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    /**
     * 生成静态页面
     * @return
     */
    @RequestMapping("/createhtml")
    public String createHtml(@RequestBody Goods goods, HttpServletRequest request){

        Writer out = null;
        try {
            Template template = configuration.getTemplate("goods.ftl");

            Map<String, Object> map = new HashMap<>();
            map.put("goods", goods);
            map.put("context", request.getContextPath());

            //获得classpath路径
            String path = this.getClass().getResource("/static/html/").getPath();
            System.out.println("classpath路径：" + path);
            System.out.println("最终静态页面的全路径：" + path + goods.getId() + ".html");
            out = new FileWriter(path + goods.getId() + ".html");
            template.process(map, out);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}
