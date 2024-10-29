package com.hxt.project.controller;

import com.hxt.project.bean.Menu;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: MenuController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/8 20:15
 * @Version 1.0
 */
@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Resp getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        // 构建树形结构的菜单数据
        List<Menu> treeMenus = buildTree(menus);
        return Resp.builder()
                .data(treeMenus)
                .msg("获取菜单列表成功")
                .code(RespCode.QUERY_SUCCESS_CODE)
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


    @GetMapping("/nodes")
    public Resp getParentMenus() {
        List<Menu> menus = menuService.getParentMenus();
        return Resp.builder()
                .data(menus)
                .msg("获取父级菜单成功")
                .code(menus!=null? RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE)
                .build();
    }

    @GetMapping("/{id}")
    public Resp getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        if (menu != null) {
            return Resp.builder()
                    .data(menu)
                    .msg("获取菜单详情成功")
                    .code(RespCode.QUERY_SUCCESS_CODE)
                    .build();
        } else {
            return Resp.builder()
                    .msg("未找到对应的菜单")
                    .code(RespCode.QUERY_FAIL_CODE)
                    .build();
        }
    }

    @PostMapping("/save")
    public Resp saveMenu(@RequestBody Menu menu) {
        boolean result = menuService.saveMenu(menu);
        return Resp.builder()
                .msg(result ? "保存成功" : "保存失败")
                .code(result ? RespCode.SAVE_SUCCESS_CODE : RespCode.SAVE_FAIL_CODE)
                .build();
    }

    @PostMapping("delete/{id}")
    public Resp deleteMenu(@PathVariable Long id) {
        boolean result = menuService.deleteMenu(id);
        return Resp.builder()
                .msg(result ? "删除成功" : "删除失败")
                .code(result ? RespCode.DELETE_SUCCESS_CODE : RespCode.DELETE_FAIL_CODE)
                .build();
    }

}
