package com.qf.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author ken
 * @Time 2018/11/28 15:23
 * @Version 1.0
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetils implements Serializable {

    private Integer id;
    private Integer oid;
    private Integer gid;
    private String gname;
    private String ginfo;
    private Double price;
    private Integer gcount;
    private String gimage;
}
