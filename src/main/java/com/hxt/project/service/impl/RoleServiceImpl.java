package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.Menu;
import com.hxt.project.bean.Role;
import com.hxt.project.mapper.RoleMapper;
import com.hxt.project.service.RoleService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: RoleServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 15:26
 * @Version 1.0
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getAllRole() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Role> getAllByRoleNameAndNo(String roleName, String roleNo) {
        return roleMapper.getAllByRoleNameAndNo(roleName,roleNo);
    }

    @Override
    public boolean saveRole(Role role) {
        return roleMapper.insert(role)>0;
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role)>0;
    }

    @Override
    public boolean deleteRole(Integer id) {
        int count = roleMapper.getRoleMenuCountByRoleId(id);
        if(count>0){
            roleMapper.deleteRolePermissions(id);
        }
        return roleMapper.deleteByPrimaryKey(id)>0;
    }

    public String getToken(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String username = verify.getClaim("username").asString();
            return username;
        }
        return null;
    }

    // 获取角色菜单权限
    public List<Menu> getRolePermissions(Integer roleId) {

        return roleMapper.getRolePermissions(roleId);
    }

    // 保存角色菜单权限
    public boolean saveRolePermissions(Integer roleId, List<Long> moduleIds) {
        if (moduleIds == null || moduleIds.isEmpty()) {
            throw new IllegalArgumentException("moduleIds 不能为空或为空");
        }
        roleMapper.deleteRolePermissions(roleId);  // 删除旧权限
        return roleMapper.saveRolePermissions(roleId, moduleIds)>0;  // 保存新权限
    }


}
