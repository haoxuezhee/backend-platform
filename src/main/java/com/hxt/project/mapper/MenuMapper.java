package com.hxt.project.mapper;

import com.hxt.project.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(Long moduleid);

    int insert(Menu row);

    Menu selectByPrimaryKey(Long moduleid);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu row);

    List<Menu> selectParentsMenu();
}