package com.qf.aop;

import com.qf.entity.User;
import com.qf.util.Constact;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * 登录的切面
 *
 * @Author ken
 * @Time 2018/11/27 14:45
 * @Version 1.0
 */
@Aspect
public class LoginAop {


    @Autowired
    private RedisTemplate redisTemplate;

    //环绕增强
    //@Around - value表示切点表达式
    @Around("execution(* *..*Controller.*(..)) && @annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //获得request请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获得cookie
        String loginToken = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constact.LOGIN_TOKEN_NAME)) {
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }

        User user = null;

        if(loginToken != null){
            //已经登录
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        if(user == null){
            //未登录 - 获得注解上的属性
            MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
            //获得增强的目标方法 -
            Method method = signature.getMethod();
            //获取目标方法上的注解
            IsLogin annotation = method.getAnnotation(IsLogin.class);
            boolean tologin = annotation.value();

            if(tologin){
                String returnUrl = request.getRequestURL() + "?" + request.getQueryString();
                returnUrl = URLEncoder.encode(returnUrl, "utf-8");
                returnUrl = returnUrl.replace("&", "%26");

                //表示强制跳转到登录页面
                return "redirect:http://localhost:8084/sso/tologin?returnUrl=" + returnUrl;
            }
        }


        Object[] args = proceedingJoinPoint.getArgs();
        //获得所有参数列表
        for (int i = 0; i < args.length; i++) {
            if(args[i] != null && args[i].getClass() == User.class){
                args[i] = user;
                break;
            }
        }


        //调用目标方法 - 指定参数列表
        Object result = proceedingJoinPoint.proceed(args);

        return result;
    }

}
