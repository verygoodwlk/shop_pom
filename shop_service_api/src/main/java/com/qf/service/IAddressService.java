package com.qf.service;

import com.qf.entity.Address;

import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 14:02
 * @Version 1.0
 */
public interface IAddressService {

    List<Address> queryByUid(Integer uid);

    int addAddress(Address address);
}
