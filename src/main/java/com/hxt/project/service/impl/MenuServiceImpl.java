package com.hxt.project.service.impl;

import com.hxt.project.bean.Menu;
import com.hxt.project.mapper.MenuMapper;
import com.hxt.project.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: MenuServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/8 20:16
 * @Version 1.0
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.selectAll();
    }

    @Override
    public List<Menu> getParentMenus() {
        return menuMapper.selectParentsMenu();
    }

    @Override
    public boolean saveMenu(Menu menu) {
        return menuMapper.insert(menu)>0;
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean deleteMenu(Long id) {
        return menuMapper.deleteByPrimaryKey(id)>0;
    }
}
