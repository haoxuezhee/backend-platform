package com.hxt.project.service;

import com.hxt.project.bean.Menu;
import com.hxt.project.bean.Role;

import java.util.List;

/**
 * ClassName: RoleService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 15:26
 * @Version 1.0
 */
public interface RoleService {

    List<Role> getAllRole();

    List<Role> getAllByRoleNameAndNo(String roleName, String roleNo);

    boolean saveRole(Role role);

    boolean updateRole(Role role);

    boolean deleteRole(Integer id);
    String getToken(String token);

    List<Menu> getRolePermissions(Integer roleId);
    boolean saveRolePermissions(Integer roleId, List<Long> menuIds);
}
