package com.hxt.project.service;

import com.hxt.project.bean.Dept;
import com.hxt.project.bean.Goods;

import java.util.List;

/**
 * ClassName: GoodsService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 16:34
 * @Version 1.0
 */
public interface GoodsService {
    List<Goods> getAllGoods();

    List<Goods> getAllByGoodsName(String goodName);

    String getToken(String token);

    boolean saveGoods(Goods goods);

    boolean updateGoods(Goods goods);

    boolean deleteGoods(Long goodId);
}
