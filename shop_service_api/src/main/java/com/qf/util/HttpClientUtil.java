package com.qf.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HttpClient的工具方法
 * @Author ken
 * @Time 2018/11/20 15:34
 * @Version 1.0
 */
public class HttpClientUtil {


    /**
     * 通过httpclient发送json数据
     * @param url
     * @param json
     * @throws IOException
     */
    public static String sendJson(String url, String json) throws IOException {
        //构建httpClient对象
        CloseableHttpClient client = HttpClientBuilder.create().build();

        //构建post请求
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        //设置请求体的格式类型
        StringEntity stringEntity = new StringEntity(json, "utf-8");
        //设置请求体的内容
        post.setEntity(stringEntity);

        //发送post请求
        CloseableHttpResponse response = client.execute(post);
        HttpEntity entity = response.getEntity();
        //从响应体中解析响应结果
        String result = EntityUtils.toString(entity);

        //关闭httplicent
        client.close();
        return result;
    }

//    public static void main(String[] args) throws IOException {
//        //构建httpClient对象
//        CloseableHttpClient client = HttpClientBuilder.create().build();
//
//        //构建一个get请求
//        HttpGet httpGet = new HttpGet("http://www.baidu.com");
//
//        //发送请求
//        CloseableHttpResponse response = client.execute(httpGet);
//        //获得响应体
//        HttpEntity entity = response.getEntity();
//        //从响应体中解析响应结果
//        String result = EntityUtils.toString(entity);
//        System.out.println("响应结果:" + result);
//    }
}
