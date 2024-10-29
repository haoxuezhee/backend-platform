package com.hxt.project.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private Integer roleId;

    private String systemNo;

    private String roleNo;

    private String roleName;

    private Date editTime;

    private String editUser;
}