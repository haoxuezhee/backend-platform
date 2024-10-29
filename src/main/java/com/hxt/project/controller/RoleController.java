package com.hxt.project.controller;

import com.hxt.project.bean.Menu;
import com.hxt.project.bean.Role;
import com.hxt.project.bean.request.RoleMenuRequest;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.MenuService;
import com.hxt.project.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * ClassName: RoleController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 15:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Resp getAllDept(String roleName, String roleNo){

        if (roleName == null || roleName.equals("") && roleNo == null || roleNo.equals("")) {
            List<Role> roleList = roleService.getAllRole();
            return Resp.builder().data(roleList).msg("获取角色信息中")
                    .code(roleList!=null? RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Role> roleList = roleService.getAllByRoleNameAndNo(roleName,roleNo);
        return Resp.builder().data(roleList).msg("获取角色信息中")
                .code(roleList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }


    @PostMapping("/save")
    public Resp saveRole(@RequestBody Role role, HttpServletRequest request){
        String token = request.getHeader("token");
        String username = roleService.getToken(token);
        role.setEditTime(new Date());
        role.setEditUser(username);
        boolean ret = roleService.saveRole(role);
        return Resp.builder()
                .data(ret)
                .msg(ret?"保存成功":"保存失败")
                .code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateRole(@RequestBody Role role, HttpServletRequest request){
        log.info("请求update方法的入参为:{}",role);
        String token = request.getHeader("token");
        String username = roleService.getToken(token);
        role.setEditTime(new Date());
        role.setEditUser(username);
        boolean ret = roleService.updateRole(role);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .msg(ret?"修改成功":"修改失败")
                .build();
    }


    @PostMapping("/delete/{id}")
    public Resp deleteRole(@PathVariable Integer id){
        log.info("请求delete方法的入参为:{}",id);
        boolean ret = roleService.deleteRole(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .msg(ret?"删除成功":"删除失败")
                .build();
    }
    /**
     * 构建树形结构的菜单数据
     *
     * @param menus 原始扁平化的菜单数据列表
     * @return 树形结构的菜单数据列表
     */
    private List<Menu> buildTree(List<Menu> menus) {
        // 创建一个映射，用于快速查找菜单项
        Map<Long, Menu> menuMap = new HashMap<>();
        for (Menu menu : menus) {
            menuMap.put(menu.getModuleId(), menu);
        }

        // 创建一个列表，用于存储根菜单项
        List<Menu> rootMenus = new ArrayList<>();

        // 遍历所有菜单项，构建树形结构
        for (Menu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                // 如果是根菜单项，则添加到根菜单列表中
                rootMenus.add(menu);
            } else {
                // 如果不是根菜单项，则找到其父菜单项，并将其添加到父菜单项的子菜单列表中
                Menu parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    if (parentMenu.getChildren() == null) {
                        // 如果父菜单项没有子菜单列表，则初始化一个
                        parentMenu.setChildren(new ArrayList<>());
                    }
                    parentMenu.getChildren().add(menu);
                }
            }
        }
        return rootMenus;
    }

    // 获取角色菜单权限
    @GetMapping("/permissions/{roleId}")
    public Resp getRolePermissions(@PathVariable Integer roleId) {
        // 获取所有可用的权限（菜单项）
        List<Menu> allMenus = menuService.getAllMenus();
        //List<Menu> menus = roleService.getRolePermissions(roleId);
       // List<Menu> treeMenus = buildTree(menus);
        List<Menu> treeMenuAll = buildTree(allMenus);
        return Resp.builder()
                .code(RespCode.QUERY_SUCCESS_CODE)
                .msg("获取角色菜单成功")
                .data(treeMenuAll).build();
    }

    // 保存角色菜单权限
    @PostMapping("/permissions/save")
    public Resp saveRolePermissions(@RequestBody RoleMenuRequest roleMenuRequest) {
        // 打印调试日志
        System.out.println("保存权限参数: " + roleMenuRequest);

        // 从请求体中获取 roleId 和 menuIds
        int roleId = roleMenuRequest.getRoleId();
        List<Long> menuIds = roleMenuRequest.getModuleIds();

        // 调用 service 方法进行保存操作
        boolean isSuccess = roleService.saveRolePermissions(roleId, menuIds);

        // 返回响应体，包含操作结果
        if (isSuccess) {
            return Resp.builder()
                    .code(20040)
                    .msg("权限保存成功")
                    .data(true)
                    .build();
        } else {
            return Resp.builder()
                    .code(20041)
                    .msg("权限保存失败")
                    .data(false)
                    .build();
        }
    }
}
