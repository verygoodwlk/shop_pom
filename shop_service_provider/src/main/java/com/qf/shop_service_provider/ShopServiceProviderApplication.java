package com.qf.shop_service_provider;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.qf.shop.dao")
@DubboComponentScan("com.qf.shop_service_provider.impl")
@EnableTransactionManagement
public class ShopServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceProviderApplication.class, args);
    }
}
