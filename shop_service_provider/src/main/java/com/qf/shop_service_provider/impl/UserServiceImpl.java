package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.shop.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author ken
 * @Time 2018/11/27 9:37
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResultData<User> login(String username, String password) {
        User user = userDao.queryByUsername(username);
        ResultData<User> resultData = new ResultData<>();
        if(user != null){
            //用户名正确
            if(user.getPassword().equals(password)){
                //密码正确
                resultData.setCode(100);
                resultData.setMsg("登录成功");
                resultData.setData(user);
            } else {
                //密码不对
                resultData.setCode(102);
                resultData.setMsg("密码错误");
            }
        } else {
            //用户名不正确
            resultData.setCode(101);
            resultData.setMsg("用户名不存在");
        }
        return resultData;
    }
}
