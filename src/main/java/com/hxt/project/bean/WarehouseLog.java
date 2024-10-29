package com.hxt.project.bean;

import lombok.Data;

import java.util.Date;
@Data
public class WarehouseLog {
    private Integer id;

    private Long productId;

    private String productName;

    private String operationType;

    private Integer quantity;

    private String operator;

    private Date operationTime;

}