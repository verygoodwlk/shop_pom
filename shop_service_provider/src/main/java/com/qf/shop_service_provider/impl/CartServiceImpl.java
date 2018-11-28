package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Cart;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.shop.dao.ICartDao;
import com.qf.shop.dao.IGoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author ken
 * @Time 2018/11/28 9:02
 * @Version 1.0
 */
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IGoodsDao goodsDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String addCart(Cart cart, User user, String cartToken) {

        //判断是否登录
        if(user != null){
            //已经登录 - 将购物车的信息添加到数据库中
            cart.setUid(user.getId());
            cartDao.addCart(cart);
        } else {
            //未登录 - 添加进redis
            if(cartToken == null) {
                cartToken = UUID.randomUUID().toString();
            }
            redisTemplate.opsForList().leftPush(cartToken, cart);
            redisTemplate.expire(cartToken, 365, TimeUnit.DAYS);

            return cartToken;
        }

        return null;
    }

    /**
     * 获取购物车的信息
     * @param cartToken
     * @param user
     * @return
     */
    @Override
    public List<Cart> cartList(String cartToken, User user) {

        //合并购物车
        List<Cart> carts = null;


        //未登录去redis中查询
        if(cartToken != null) {
            Long size = redisTemplate.opsForList().size(cartToken);
            carts = redisTemplate.opsForList().range(cartToken, 0, size);

            //数据库中再查询商品信息
            for (int i = 0; i < carts.size(); i++) {
                Goods goods = goodsDao.queryById(carts.get(i).getGid());
                carts.get(i).setGoods(goods);
            }
        }

        if(user != null){
            //如果已经登录，但是临时购物车还有数据，则合并到购物车中
            if(carts != null){
                //说明临时购物车还有数据
                for (int i = 0; i < carts.size(); i++) {
                    carts.get(i).setUid(user.getId());
                    cartDao.addCart(carts.get(i));
                }

                //清空临时购物车
                redisTemplate.delete(cartToken);
            }

            //已经登录去数据库中查询
            carts = cartDao.queryCartsByUid(user.getId());

        }

        return carts;
    }
}
