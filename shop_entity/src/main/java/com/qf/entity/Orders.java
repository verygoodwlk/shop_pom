package com.qf.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 15:19
 * @Version 1.0
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    private Integer id;
    private String orderid;
    private Integer uid;
    private String person;
    private String address;
    private String phone;
    private Integer code;
    private Double oprice;
    private Integer status = 0;//状态 0 - 未支付 1 - 已支付（待发货） 2 - 已发货（待收货） 3 - 已收货 4 - 已评价 5 - 申请退款
    private Date ordertime;

    private List<OrderDetils> detils;
}
