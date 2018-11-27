package com.qf.aop;

import java.lang.annotation.*;

/**
 *
 * 自定义注解
 * 注解的声明：public @interface 注解名称
 *
 * 元注解：标记注解的注解
 * @Documented：表示该注解会被javadoc命令写入api文档中
 * @Target：注解的标记位置
 *  ElementType.ANNOTATION_TYPE：该注解可以标记别的注解
 *  ElementType.CONSTRUCTOR：标注到构造方法
 *  ElementType.FIELD：标注到成员属性
 *  ElementType.LOCAL_VARIABLE：标注到局部变量
 *  ElementType.METHOD：标注到方法上
 *  ElementType.PACKAGE：标注到包上
 *  ElementType.PARAMETER：标注到方法形参上
 *  ElementType.TYPE：标注到类、接口、枚举类上
 * @Retention：注解的作用范围
 *  RetentionPolicy.SOURCE：注解的有效范围只在源码中，编译后就被丢弃
 *  RetentionPolicy.CLASS：注解有效范围在编译文件中，运行时丢弃
 *  RetentionPolicy.RUNTIME：注解在运行时仍然有效，这个范围的注解可以通过反射获取
 *
 * 注解内的方法声明：
 * 类型 方法名() [defualt 默认值];
 *
 * 注意：
 * 如果一个属性没有设置default默认值，在标记这个注解时，必须给定该属性值
 * 如果一个属性的名字为value,则在赋值时可以省略属性名。当如果需要赋值两个以上的属性，则value不能省略
 * 如果一个属性的类型是数组类型，则应该用{}赋值，如果只要给一个值，{}可以省略
 *
 * @Author ken
 * @Time 2018/11/27 14:26
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {

    boolean value() default false;
}
