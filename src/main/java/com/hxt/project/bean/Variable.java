package com.hxt.project.bean;

import lombok.Data;

import java.util.Date;
@Data
public class Variable {
    private Integer varId;

    private String varLable;

    private String varName;

    private String varValue;

    private Date editTime;

    private String editUser;

}