package com.qf.shop.dao;

import com.qf.entity.User;

/**
 * @Author ken
 * @Time 2018/11/27 9:37
 * @Version 1.0
 */
public interface IUserDao {

    User queryByUsername(String username);
}
