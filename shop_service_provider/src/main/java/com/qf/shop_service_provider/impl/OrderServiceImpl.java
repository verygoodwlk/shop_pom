package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.*;
import com.qf.service.IOrderService;
import com.qf.shop.dao.IAddressDao;
import com.qf.shop.dao.ICartDao;
import com.qf.shop.dao.IOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author ken
 * @Time 2018/11/28 15:26
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private IAddressDao addressDao;

    @Autowired
    private ICartDao cartDao;


    /**
     * 添加订单
     * @param aid
     * @param cids
     * @param user
     * @return
     */
    @Override
    @Transactional
    public int addOrder(Integer aid, Integer[] cids, User user) {

        //根据地址的id查询地址的信息
        Address address = addressDao.queryById(aid);

        //根据购物车id数据查询购物车的信息
        List<Cart> carts = cartDao.queryCartsByIds(cids);

        //计算总价
        double allprice = 0;
        for (Cart cart : carts) {
            allprice += cart.getMoney();
        }

        //创建订单对象
        Orders orders = new Orders();
        orders.setOrderid(UUID.randomUUID().toString().replace("-", ""));
        orders.setUid(user.getId());
        orders.setPerson(address.getPerson());
        orders.setAddress(address.getAddress());
        orders.setPhone(address.getPhone());
        orders.setCode(address.getCode());
        orders.setStatus(0);//未付款
        orders.setOrdertime(new Date());
        orders.setOprice(allprice);

        //添加订单
        orderDao.addOrder(orders);

        //构建订单详情
        List<OrderDetils> detils = new ArrayList<>();
        for (Cart cart : carts) {
            OrderDetils orderDetils = new OrderDetils();
            orderDetils.setOid(orders.getId());
            orderDetils.setGid(cart.getGid());
            orderDetils.setGname(cart.getGoods().getTitle());
            orderDetils.setGinfo(cart.getGoods().getGinfo());
            orderDetils.setPrice(cart.getGoods().getPrice());
            orderDetils.setGcount(cart.getGnumber());
            orderDetils.setGimage(cart.getGoods().getGimage());
            detils.add(orderDetils);
        }

        orderDao.addOrderDetils(detils);

        //删除购物车
        cartDao.deleteCarts(cids);

        return 1;
    }

    @Override
    public List<Orders> queryOrdersByUid(Integer uid) {
        return orderDao.queryByUid(uid);
    }
}
