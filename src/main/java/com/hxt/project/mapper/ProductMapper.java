package com.hxt.project.mapper;

import com.hxt.project.bean.Goods;
import com.hxt.project.bean.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product row);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product row);

    List<Product> selectByProductName(@Param("name") String name);

    int outBound(@Param("name") String name, @Param("inventoryRemove") Integer inventoryRemove);

    Product getByName(@Param("name") String name);

    int inBound(@Param("name") String name, @Param("inventorySum") Integer inventorySum);

    void insertProductsBatch(@Param("products") List<Product> products);

    Product findByCode(@Param("code") String code);

    int updateProductImgUrl(@Param("id") Long id, @Param("imgUrl") String imgUrl);

    List<Product> getTopFiveOutBound();
}