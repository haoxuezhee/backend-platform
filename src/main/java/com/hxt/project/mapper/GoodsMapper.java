package com.hxt.project.mapper;

import com.hxt.project.bean.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long goodid);

    int insert(Goods row);

    Goods selectByPrimaryKey(Long goodid);

    List<Goods> selectAll();

    int updateByPrimaryKey(Goods row);

    List<Goods> selectByGoodsName(@Param("goodName") String goodName);

}