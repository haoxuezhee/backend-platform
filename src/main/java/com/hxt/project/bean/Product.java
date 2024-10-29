package com.hxt.project.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class Product {
    @ExcelProperty("id")
    private Long id;
    @ExcelProperty("产品名称")
    private String name;
    @ExcelProperty("入库总值")
    private Integer inventorySum;
    @ExcelProperty("库存值")
    private Integer inventory;
    @ExcelProperty("出库总值")
    private Integer inventoryRemove;
    @ExcelProperty("库房")
    private String unit;
    @ExcelProperty("产品地址")
    private String localtion;
    @ExcelProperty("告警限制值")
    private Byte alarm;
    @ExcelProperty("物品代码")
    private String code;
    @ExcelProperty("警告值")
    private Integer alarmValue;
    @ExcelProperty("型号")
    private String type;
    @ExcelProperty("货架")
    private String img;
    @ExcelProperty("状态")
    private Byte statusCode;
    @ExcelProperty("创建人")
    private String createUser;
    @ExcelProperty("创建时间")
    private Date createTime;
    @ExcelProperty("修改人")
    private String updateUser;
    @ExcelProperty("修改时间")
    private Date updateTime;

    @ExcelProperty("图片地址")
    private String imgUrl;
}