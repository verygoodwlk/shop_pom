package com.qf.entity;

import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author ken
 * @Time 2018/11/27 14:15
 * @Version 1.0
 *
 * List<Cart> - 整个购物车
 */
@ToString
public class Cart implements Serializable {

    private Integer id;
    private Integer gid;
    private Integer gnumber;
    private double money;
    private Integer uid;

    private Goods goods;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getGnumber() {
        return gnumber;
    }

    public void setGnumber(Integer gnumber) {
        this.gnumber = gnumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;

        BigDecimal price = BigDecimal.valueOf(goods.getPrice());
        BigDecimal number = BigDecimal.valueOf(getGnumber());
        this.setMoney(price.multiply(number).doubleValue());
    }

    public static void main(String[] args) {
        System.out.println(5.0 - 4.9);
        BigDecimal a = BigDecimal.valueOf(5.0);
        BigDecimal b = new BigDecimal("4.9");
        System.out.println(a.subtract(b).doubleValue());
    }
}
