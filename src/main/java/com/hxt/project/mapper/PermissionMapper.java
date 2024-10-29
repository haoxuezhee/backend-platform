package com.hxt.project.mapper;

import com.hxt.project.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer permissionid);

    int insert(Permission row);

    Permission selectByPrimaryKey(Integer permissionid);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission row);

    List<Permission> getAllByNameAndCode(@Param("permissionName") String permissionName, @Param("permission") String permission);

    int deletePermissionsByRoleId(@Param("roleId") Integer roleId);

    int insertRolePermissions(@Param("roleId") Integer roleId, @Param("permissionIds") List<Integer> permissionIds);

}