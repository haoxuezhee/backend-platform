package com.hxt.project.bean;

import lombok.Data;

import java.util.Date;
@Data
public class Goods {
    private Long goodId;

    private String goodName;

    private Integer goodNum;

    private Date editTime;

    private String editUser;
}