package com.qf.shop.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Address;
import com.qf.entity.Cart;
import com.qf.entity.Orders;
import com.qf.entity.User;
import com.qf.service.IAddressService;
import com.qf.service.ICartService;
import com.qf.service.IOrderService;
import com.qf.util.Constact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author ken
 * @Time 2018/11/28 11:35
 * @Version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference
    private IAddressService addressService;

    @Reference
    private ICartService cartService;

    @Reference
    private IOrderService orderService;

    /**
     * 进入订单编辑页面
     * @return
     */
    @IsLogin(true)
    @RequestMapping("/edit")
    public String orderEdit(
            @CookieValue(value= Constact.CART_TOKEN_NAME, required = false) String cartToken,
            Integer[] goodsid,
            User user,
            Model model){

        System.out.println("商品的id：" + Arrays.toString(goodsid) + " 当前用户：" + user);

        //当前用户的收货地址
        List<Address> addresses = addressService.queryByUid(user.getId());


        //总价格
        double allprice = 0;
        //当前勾选的购物车信息
        List<Cart> carts = cartService.cartList(cartToken, user);
        //用户需要相当的购物车信息
        List<Cart> carts2 = new ArrayList<>();
        for (Cart cart : carts) {
            for (Integer gid : goodsid) {
                if(cart.getGid().equals(gid)){
                    allprice += cart.getMoney();
                    carts2.add(cart);
                }
            }
        }

        model.addAttribute("addresss", addresses);
        model.addAttribute("carts", carts2);
        model.addAttribute("allprice", allprice);

        return "orderedit";
    }

    /**
     * 新增收货地址
     * @return
     */
    @RequestMapping("/addaddress")
    @ResponseBody
    @IsLogin
    public Integer addAddress(Address address, User user){
        address.setUid(user.getId());
       return addressService.addAddress(address);
    }

    /**
     * 添加订单
     * @param aid
     * @param cids
     * @return
     */
    @RequestMapping("/addorder")
    @IsLogin
    @ResponseBody
    public String addOrder(Integer aid, Integer[] cids, User user){
        String orderid = orderService.addOrder(aid, cids, user);
        return orderid;
    }

    /**
     * 查询用户的订单列表 - 必须登录
     * @return
     */
    @RequestMapping("/list")
    @IsLogin(true)
    public String orderList(User user, Model model){
        List<Orders> orders = orderService.queryOrdersByUid(user.getId());
        model.addAttribute("orders", orders);
        return "orderlist";
    }
}
