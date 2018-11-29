package com.qf.demo.alipay_demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author ken
 * @Time 2018/11/29 10:17
 * @Version 1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {

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
    public void pay(HttpServletResponse response) throws IOException {

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://www.baidu.com");//设置同步响应URl
        alipayRequest.setNotifyUrl("http://www.baidu.com");//设置异步响应URl
        //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + UUID.randomUUID().toString() + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":66.88," +
                "    \"subject\":\"IphoneX 128G\"," +
                "    \"body\":\"IphoneX 128G\"," +
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
}
