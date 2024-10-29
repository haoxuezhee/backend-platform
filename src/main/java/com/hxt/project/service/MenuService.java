package com.hxt.project.service;

import com.hxt.project.bean.Menu;

import java.util.List;

/**
 * ClassName: MenuService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/8 20:16
 * @Version 1.0
 */
public interface MenuService {
    List<Menu> getAllMenus();

    List<Menu> getParentMenus();

    boolean saveMenu(Menu menu);

    Menu getMenuById(Long id);

    boolean deleteMenu(Long id);
}
