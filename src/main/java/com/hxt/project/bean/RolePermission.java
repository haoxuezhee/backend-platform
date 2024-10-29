package com.hxt.project.bean;

import lombok.Data;

/**
 * ClassName: RolePermission
 * Package: com.hxt.project.bean
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/16 15:49
 * @Version 1.0
 */
@Data
public class RolePermission {

    private Integer rolePermissionId;
    private Integer roleId;
    private Integer permissionId;
}
