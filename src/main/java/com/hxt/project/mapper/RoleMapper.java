package com.hxt.project.mapper;

import com.hxt.project.bean.Menu;
import com.hxt.project.bean.Role;
import com.hxt.project.bean.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role row);

    Role selectByPrimaryKey(Integer roleid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role row);

    List<Role> getAllByRoleNameAndNo(@Param("roleName") String roleName, @Param("roleNo") String roleNo);

    // 获取角色的菜单权限
    List<Menu> getRolePermissions(@Param("roleId") Integer roleId);

    // 删除角色的旧权限
    int deleteRolePermissions(@Param("roleId") Integer roleId);

    // 保存角色的菜单权限
    int saveRolePermissions(@Param("roleId") Integer roleId, @Param("moduleIds") List<Long> moduleIds);

    //获取单个角色菜单关系表信息
    int getRoleMenuCountByRoleId(@Param("roleId") Integer roleId);
}