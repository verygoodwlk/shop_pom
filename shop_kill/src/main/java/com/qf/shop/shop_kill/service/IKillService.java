package com.qf.shop.shop_kill.service;

import com.qf.entity.Kill;

/**
 * @Author ken
 * @Time 2018/11/30 9:22
 * @Version 1.0
 */
public interface IKillService {

    /**
     * 根据id查询秒杀信息
     * @param id
     * @return
     */
    Kill queryKillInfo(Integer id);

    /**
     * 秒杀业务
     */
    int kill(Integer id, Integer number, Integer uid);

}
