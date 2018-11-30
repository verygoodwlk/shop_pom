package com.qf.shop.shop_kill.service.impl;

import com.google.gson.Gson;
import com.qf.entity.Orders;
import com.qf.shop.shop_kill.dao.IKillDao;
import com.qf.shop.shop_kill.service.IDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/30 14:37
 * @Version 1.0
 */
@Service
public class DBServiceImpl implements IDBService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IKillDao killDao;

    @PostConstruct
    public void init(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
    }


    @Override
    @Transactional
    public void syncDataBase(Integer gid) {
        System.out.println("开始同步数据库" + Thread.currentThread().getName());

        //获得redis中库存的数据量，同步更新到数据库中
        Integer save = Integer.parseInt(redisTemplate.opsForValue().get("kill" + gid) + "");
        killDao.updateKill(gid, save);

        //插入订单
        //获得订单链表的长度
        Long orderSize = redisTemplate.opsForList().size("orders" + gid);
        //批量获得订单，并且插入到数据库中
        List<String> ordersStrList = redisTemplate.opsForList().range("orders" + gid, 0, orderSize);

        List<Orders> ordersList = new ArrayList<>();
        for (int i = 0; i < ordersStrList.size(); i++) {
            Orders orders = new Gson().fromJson(ordersStrList.get(i), Orders.class);
            ordersList.add(orders);

            if(i % 5000 == 0 || i == ordersStrList.size() - 1){
                //批量插入订单到数据库中
                killDao.saveOrdersList(ordersList);
                ordersList.clear();
            }
        }

        //清空redis
        redisTemplate.delete("kill" + gid);
        redisTemplate.delete("orders" + gid);

        System.out.println("完成同步数据库");
    }
}
