package com.hxt.project.mapper;

import com.hxt.project.bean.WarehouseLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WarehouseLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseLog row);

    WarehouseLog selectByPrimaryKey(Integer id);

    List<WarehouseLog> selectAll();

    int updateByPrimaryKey(WarehouseLog row);
}