package com.qf.shop.dao;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/19 9:54
 * @Version 1.0
 */
public interface IGoodsDao {

    List<Goods> queryAll();

    int insert(Goods goods);

    List<Goods> queryNewGoods();

    Goods queryById(Integer gid);
}
