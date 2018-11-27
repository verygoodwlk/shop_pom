package com.qf.service;

import com.qf.entity.ResultData;
import com.qf.entity.User;

/**
 * @Author ken
 * @Time 2018/11/27 9:36
 * @Version 1.0
 */
public interface IUserService {

    ResultData<User> login(String username, String password);
}
