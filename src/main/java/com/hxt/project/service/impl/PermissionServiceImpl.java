package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.Permission;
import com.hxt.project.mapper.PermissionMapper;
import com.hxt.project.service.PermissionService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: PermissionServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 19:47
 * @Version 1.0
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.selectAll();
    }

    @Override
    public List<Permission> getAllByNameAndCode(String permissionName, String permission) {
        return permissionMapper.getAllByNameAndCode(permissionName,permission);
    }

    @Override
    public String getToken(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String username = verify.getClaim("username").asString();
            return username;
        }
        return null;
    }

    @Override
    public boolean savePermission(Permission permission) {
        return permissionMapper.insert(permission)>0;
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return permissionMapper.updateByPrimaryKey(permission)>0;
    }

    @Override
    public boolean deletePermission(Integer permissionId) {
        return permissionMapper.deleteByPrimaryKey(permissionId)>0;
    }

    @Override
    public boolean getRolePermissions(Integer roleId, List<Integer> permissionIds) {
        // 先删除角色原有的权限
        permissionMapper.deletePermissionsByRoleId(roleId);

        // 如果有新的权限ID列表，进行批量插入
        if (permissionIds != null && !permissionIds.isEmpty()) {
            permissionMapper.insertRolePermissions(roleId, permissionIds);
        }

        return true;
    }
}
