package com.hxt.project.bean;

import lombok.Data;

import java.util.Date;
@Data
public class Permission {
    private Integer permissionId;

    private String permissionName;

    private String permission;

    private String addUser;

    private String editUser;

    private Date addTime;

    private Date editTime;

}