package com.qf.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author ken
 * @Time 2018/11/27 9:40
 * @Version 1.0
 */
@Getter
@Setter
public class ResultData<T> implements Serializable {

    private Integer code;//返回码 100 - 登录成功 101 - 用户名错误 102 - 密码错误 103 -
    private String msg;//具体的错误信息
    private T data;
}
