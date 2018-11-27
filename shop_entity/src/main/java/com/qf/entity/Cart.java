package com.qf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author ken
 * @Time 2018/11/27 14:15
 * @Version 1.0
 *
 * List<Cart> - 整个购物车
 */
@Setter
@Getter
@ToString
public class Cart {

    private Integer gid;
    private Integer gumber;
    private double money;

    private Goods goods;
}
