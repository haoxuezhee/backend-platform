package com.hxt.project.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    private Long moduleId;

    private Long parentId;

    private String moduleName;

    private String moduleUrl;

    private String moduleIcon;

    private Integer moduleOrder;

    private Boolean isLeaf;

    private Integer moduleLevel;

    private String systemNo;

    private String fullIndex;

    private String addUser;

    private String editUser;

    private Date addTime;

    private Date editTime;

    private String moduleNotes;

    private List<Menu> children; // 添加 children 属性




}