package com.hxt.project.bean.request;

import lombok.Data;

import java.util.List;

/**
 * ClassName: RolePermissionRequest
 * Package: com.hxt.project.bean
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/16 15:43
 * @Version 1.0
 */
@Data
public class RolePermissionRequest {
    private Integer roleId; // 角色ID
    private List<Integer> permissionIds; // 权限ID列表
}
