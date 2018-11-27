package com.qf.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author ken
 * @Time 2018/11/27 9:34
 * @Version 1.0
 */
@Setter
@Getter
@ToString
public class User implements Serializable {

    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private Date birthday;
    private String idcard;
    private String phone;
    private String email;
    private String token;
    private String time;
}
