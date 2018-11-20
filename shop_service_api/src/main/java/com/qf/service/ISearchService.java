package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/20 15:56
 * @Version 1.0
 */
public interface ISearchService {

    int addIndex(Goods goods);

    List<Goods> queryIndex(String keyword);
}
