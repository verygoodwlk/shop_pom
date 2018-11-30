package com.qf.shop.shop_kill.service.impl;

import com.qf.entity.Kill;
import com.qf.entity.Orders;
import com.qf.shop.shop_kill.dao.IKillDao;
import com.qf.shop.shop_kill.service.IKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @Author ken
 * @Time 2018/11/30 10:47
 * @Version 1.0
 */
@Service
public class LG2KillServiceImpl implements IKillService {

    @Autowired
    private IKillDao killDao;

    @Override
    public Kill queryKillInfo(Integer id) {
        System.out.println("乐观锁2生效！！");
        return killDao.queryKillInfo(id);
    }

    @Override
    @Transactional
    public int kill(Integer id, Integer number, Integer uid) {

        int result = killDao.updateKillSaveLG2(id, number);
        if(result > 0){
            //生成订单
            Orders orders = new Orders();
            orders.setOrderid(UUID.randomUUID().toString());
            orders.setUid(uid);
            orders.setOrdertime(new Date());

            //保存订单
            killDao.saveOrders(orders);
            return 1;
        }

        return 0;
    }
}
