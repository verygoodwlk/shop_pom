package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/19 9:52
 * @Version 1.0
 */
public interface IGoodsService {

    List<Goods> queryAll();

    int saveGoods(Goods goods);
}
