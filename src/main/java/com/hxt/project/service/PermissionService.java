package com.hxt.project.service;

import com.hxt.project.bean.Dept;
import com.hxt.project.bean.Permission;

import java.util.List;

/**
 * ClassName: PermissionService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 19:47
 * @Version 1.0
 */
public interface PermissionService {

    List<Permission> getAllPermission();

    List<Permission> getAllByNameAndCode(String permissionName, String permission);

    String getToken(String token);

    boolean savePermission(Permission permission);

    boolean updatePermission(Permission permission);

    boolean deletePermission(Integer permissionId);

    boolean getRolePermissions(Integer roleId, List<Integer> permissionIds);

}
