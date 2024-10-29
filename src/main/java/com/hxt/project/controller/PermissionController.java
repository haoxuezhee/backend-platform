package com.hxt.project.controller;

import com.hxt.project.bean.Permission;
import com.hxt.project.bean.request.RolePermissionRequest;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * ClassName: PermissionCOntroller
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 19:51
 * @Version 1.0
 */
@RestController
@RequestMapping("/permission")
@Slf4j
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/list")
    public Resp getAllPermission(String permissionName, String permission){
        if ((permissionName == null || permissionName.equals("")) && (permission == null || permission.equals(""))) {
            List<Permission> permissionList = permissionService.getAllPermission();
            return Resp.builder().data(permissionList).msg("获取权限信息中")
                    .code(permissionList!=null? RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Permission> permissionList = permissionService.getAllByNameAndCode(permissionName, permission);
        return Resp.builder().data(permissionList).msg("获取权限信息中")
                .code(permissionList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveDept(@RequestBody Permission permission, HttpServletRequest request){
        String token = request.getHeader("token");
        String username = permissionService.getToken(token);
        permission.setEditTime(new Date());
        permission.setEditUser(username);
        boolean ret = permissionService.savePermission(permission);
        return Resp.builder()
                .data(ret)
                .msg(ret?"保存成功":"保存失败")
                .code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateDept(@RequestBody Permission permission,HttpServletRequest request){
        log.info("请求update方法的入参为:{}",permission);
        String token = request.getHeader("token");
        String username = permissionService.getToken(token);
        permission.setEditTime(new Date());
        permission.setEditUser(username);
        boolean ret = permissionService.updatePermission(permission);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .msg(ret?"修改成功":"修改失败")
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteDept(@PathVariable Integer id){
        log.info("请求delete方法的入参为:{}",id);
        boolean ret = permissionService.deletePermission(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .msg(ret?"删除成功":"删除失败")
                .build();
    }

    @PostMapping("/role")
    public Resp configureRolePermissions(@RequestBody RolePermissionRequest rolePermissionRequest) {
        log.info("配置角色权限请求入参: {}", rolePermissionRequest);

        boolean result = permissionService.getRolePermissions(rolePermissionRequest.getRoleId(), rolePermissionRequest.getPermissionIds());

        return Resp.builder()
                .code(result ? RespCode.UPDATE_SUCCESS_CODE : RespCode.UPDATE_FAIL_CODE)
                .msg(result ? "配置权限成功" : "配置权限失败")
                .build();
    }
}
