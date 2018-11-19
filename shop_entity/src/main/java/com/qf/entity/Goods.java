package com.qf.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author ken
 * @Time 2018/11/19 9:50
 * @Version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Goods implements Serializable {

    private Integer id;
    private String title;
    private String ginfo;
    private Integer gcount;
    private Integer tid = 1;
    private Double allprice;
    private Double price;
    private String gimage;
}
