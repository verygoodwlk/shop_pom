package com.qf.shop.shop_order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author ken
 * @Time 2018/11/29 10:50
 * @Version 1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {


    @Reference
    private IOrderService orderService;

    private AlipayClient alipayClient;

    {
        //AlipayClient应该是全局唯一
        alipayClient = new DefaultAlipayClient(
                "https://openapi.alipaydev.com/gateway.do",
                "2016073000127352",
                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTPbgYeBIIamie7oWyr6Z80DeTNlwH0GiIyrscyWX/hj+V2yvw94WDqRuWbo5KAdGuCaws2LziLf6IeTnnXkbs4fnFpY7hx8uH6urnEXw0HIHXvnfq59kdECcGBv4cqMrImtKkz4HHXFx7JRz4owHs7JgAYShiIeDmi87TKm6kFUjxS+q9A7Gn0K5Suo8QYS/bK67k49CStpoJWqVOeMXsiiHpBRjgiPka+3RVKFSbj7w00hy0TYS0NZ2RagmPKo3E7q7LgnwPYWJ6//L0OXjr6m6yTRnI/0mPyDoqb1cirO0ccA9e+52WrziFb6Fbp6dUM8IRzQ/zDwidyX1WFcGzAgMBAAECggEABYm/v+PcQcbG0puEGdlzeKRYQsg7o+uZ0vNVIFsO5BKHl7pbp7+XnQEiUYEtBumUmmgwuqsYR6WVOkrc+6/XGRfSzjVQ73sVU7JpTXGh60/bLmMynXpE90vhO9/NdQSARBVWsO5BIl999v/JXCy9aMzpVrJQ04gUrnc5u0WQTcmUY9Zrloc4xKTl2hctAqlJnFm3NmoYKthLneLKx5gM/G+Uh9m0ZNLbdFvbgD8zbQt/7ALKIDpN3ezsWx7jRvf0py3WBJKDl9TQKBvfCtm2TfuAW9S74ARPY7Xpfvr7JhIlM6TwoczTczZ9MnmS03SmjrHx+BVmL7WaUi0KnpCD+QKBgQDh72yHsRN+rWnNwpaPLBcGKvPdq8XHv9GcbF2sL9/C03IJMuvT6EbfDgVk2Yq9W42R8G+iHuWlnB0noWhgkgttBwN9WwEwIBaobh+/BvF6+Kojaw2L3+Vwuvk8L8AztPoDc63Xqja/OQfnP+n1gWloqZldMGNCqeLR8VksgpOupwKBgQCm1Yvyrpfken0M6O7Q3OKXmnqJck8Y0tiwHo4yGGJTHm+Lh3GfrulHGc6Bth3H1AIQj+qd/vsFzsvkHzwWrGcjUwCRC+tQd+voyuz2Dw8q82AJtkIuZAt5E2azhvD36IcO3Fv/IfnO4zdSAJZBtfLYcC9bAut8yal1UgxTXA/iFQKBgDwfmqIBXU64lw9KWQaAlR/vDjZPH7KoOP+HVuYbV1BhNFd+VYNurG1vLcBIo8MAOgw8D3j0/+eyQ4/oQ/5u3tuEokkZjpdf8qefppMPJfOzeJM0ScaPLl9xYiHCE0OvRVjlz700bp00cAX53CiY55QSjd1eowt6agG0WCagGOzBAoGAHbPfWrGi8VatdOLzwdPRs9QZBOeJFY5UbebCwkB9IPdGPL08iYWGmtYJYiaeXrlsNK6qwr5myr3qpw64kADZy/w3GZvZpVX0dlnKnnRH6wmxi4kbd07wi/ncSxI3TKvlf8vTxTSzWRYy/zKDjedq+QXgKH/4lRsCRJT7BMxbN5ECgYEAibUNheCEQURlqxnTTMv3JFHPx5o6WsN1H4lmdud5tPDc1fPgYNZkxMlp+2FMEgeSvQb4Sv1CL9avTXne8GDHnOwT/IyC9ix27JF6bj6IOwQC2g+gUnav+XvFTpXKui6FwBd1817rcZlrfMQUlO15pe2BCuRChvwQBs5Ehc56Tyk=",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx+n+pEqTVi27mtj3CuUQXi2ixqeeTwE/0tGrW+sg6xtfajvJV67GYf2zzNxxBV0TYfhdbi70VI3DftEijg7GSNKoOilAu2DKQFqidnSxmN1Es1oRTaiaehqm1Uzs2uswpzBVR21iygLHujwthC8kNkMgxVFkjbE/qTn7z5wlsailtg6wF+hY3BcDCiaLyVLjEDngmrLyLXPLenjAuvXq20h9zV7CW9HXuhpPBDfsn4fv5TjgEl1smjJNr4O/VxICKDNPsvrCyNXhfroK9PEFFwH+4IWGeBUJAP2cSufNU0OA+UH+2xQnaR8Cz30QIgIslckBGuXQZvxqaY2mMMz14QIDAQAB",
                "RSA2"); //获得初始化的AlipayClient
    }


    @RequestMapping("/alipay")
    public void pay(String orderid, HttpServletResponse response) throws IOException {

        //通过订单id获得订单信息
        Orders orders = orderService.queryByOrderid(orderid);


        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://www.baidu.com");//设置同步响应URl
        alipayRequest.setNotifyUrl("http://verygoodwlk.xicp.net/pay/notifypay");//设置异步响应URl
        //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orders.getOrderid() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":" + orders.getOprice() + "," +
                "    \"subject\":\"" + orders.getOrderid() + "\"," +
                "    \"body\":\"" + orders.getOrderid() + "\"," +
                "    \"passback_params\":\"\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     * 去支付
     * @return
     */
    @RequestMapping("/topay")
    public String topay(String orderid, Model model){
        model.addAttribute("orderid", orderid);
        return "topay";
    }

    /**
     * 主动发送请求判断是否支付成功！！！
     * @return
     */
    @RequestMapping("/ispay")
    public String ispay(String orderid) throws AlipayApiException {

        //通过订单号判断是否支付成功 - 发送请求给支付宝主动确认
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + orderid + "\"" +
                "  }");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            if(response.getTradeStatus().equals("TRADE_SUCCESS")){
                //支付成功

                //修改订单状态
                orderService.updateOrderStatus(orderid, 1);

            }
        } else {
            System.out.println("调用失败");
        }


        return "redirect:/order/list";
    }


    /**
     * 接受支付宝异步请求的接口
     * @return
     */
    @RequestMapping("/notifypay")
    @ResponseBody
    public String notifyPay(String out_trade_no, String trade_status ){
        System.out.println("异步通知消息已经接收到！！！！！" + out_trade_no + "  " + trade_status);
        if(trade_status.equals("TRADE_SUCCESS")){
            //修改订单状态
            orderService.updateOrderStatus(out_trade_no, 1);
        }
        return null;
    }
}
