package com.hxt.project.bean;

import lombok.Data;

import java.util.Date;
@Data
public class LoginLog {
    private Integer id;

    private Long userId;

    private Date loginTime;

    private String loginIp;
}