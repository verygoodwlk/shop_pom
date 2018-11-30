package com.qf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Author ken
 * @Time 2018/11/30 9:22
 * @Version 1.0
 */
@Setter
@Getter
@ToString
public class Kill {

    private Integer id;
    private String title;
    private String image;
    private double price;
    private Integer save;
    private Date starttime;
    private Date endtime;
    private Integer statu;
    private Integer version;
}
