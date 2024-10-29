package com.hxt.project.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 42L;

    private Long id;

    private String company;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String sex;

    private String email;

    private Date updateTime;

    private Integer status;//0-管理员 1-普通用户

    private String deptName;

    private Integer loginCount;

}