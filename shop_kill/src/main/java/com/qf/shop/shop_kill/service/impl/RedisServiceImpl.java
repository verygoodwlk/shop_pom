package com.qf.shop.shop_kill.service.impl;

import com.google.gson.Gson;
import com.qf.entity.Kill;
import com.qf.entity.Orders;
import com.qf.shop.shop_kill.dao.IKillDao;
import com.qf.shop.shop_kill.service.IDBService;
import com.qf.shop.shop_kill.service.IKillService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Author ken
 * @Time 2018/11/30 9:25
 * @Version 1.0
 */
@Service
@Primary
public class RedisServiceImpl implements IKillService {

    @Autowired
    private IKillDao killDao;

    @Autowired
    private IDBService dbService;

    @Autowired
    private RedisTemplate redisTemplate;
    private RedisConnection connection;
    private String sha1;


    @PostConstruct
    public void init(){
        //缓存lua脚本
        String path = this.getClass().getResource("/static/lua/kill.lua").getPath();
        connection = redisTemplate.getConnectionFactory().getConnection();
        try {
            sha1 = connection.scriptLoad(FileUtils.readFileToByteArray(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Kill queryKillInfo(Integer id){
        System.out.println("redis业务生效");
        return killDao.queryKillInfo(id);
    }

    @Override
    public int kill(Integer id, Integer number, Integer uid) {
        //通过lua脚本进行redis的操作 - 某则仍然有数据一致性的问题
        //生成订单
        Orders orders = new Orders();
        orders.setOrderid(UUID.randomUUID().toString());
        orders.setUid(uid);
        orders.setOrdertime(new Date());

        //执行脚本
        long result = connection.evalSha(sha1,
                ReturnType.INTEGER,
                1,
                (id + "").getBytes(),
                (number + "").getBytes(),
                new Gson().toJson(orders).getBytes());

        if(result == 0){
            //秒杀结束，需要同步数据库
            System.out.println("秒杀的业务逻辑：" + Thread.currentThread().getName());
            dbService.syncDataBase(id);
        }


        return (int) result;
    }
}
