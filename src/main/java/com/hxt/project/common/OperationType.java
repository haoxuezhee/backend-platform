package com.hxt.project.common;

/**
 * ClassName: OperationType
 * Package: com.hxt.project.common
 * Description:
 *
 * @Author hxt
 * @Create 2024/10/12 14:56
 * @Version 1.0
 */
public enum OperationType {

    IN("入库"),
    OUT("出库");

    private String description;

    OperationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
