package com.qf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author ken
 * @Time 2018/11/28 14:00
 * @Version 1.0
 */
@Setter
@Getter
@ToString
public class Address implements Serializable {

    private Integer id;
    private String person;
    private String address;
    private String phone;
    private Integer code;
    private Integer uid;
    private Integer isdefault;
}
