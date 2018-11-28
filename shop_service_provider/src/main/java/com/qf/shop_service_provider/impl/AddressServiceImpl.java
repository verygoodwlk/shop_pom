package com.qf.shop_service_provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.entity.Address;
import com.qf.service.IAddressService;
import com.qf.shop.dao.IAddressDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 14:02
 * @Version 1.0
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private IAddressDao addressDao;

    @Override
    public List<Address> queryByUid(Integer uid) {
        return addressDao.queryByUid(uid);
    }

    @Override
    public int addAddress(Address address) {
        int result = addressDao.insert(address);
        System.out.println("添加地址" + result);
        return result;
    }
}
