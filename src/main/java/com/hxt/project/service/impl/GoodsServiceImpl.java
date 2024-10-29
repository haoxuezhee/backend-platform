package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.Goods;
import com.hxt.project.mapper.GoodsMapper;
import com.hxt.project.service.GoodsService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: GoodsServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 16:34
 * @Version 1.0
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.selectAll();
    }

    @Override
    public List<Goods> getAllByGoodsName(String goodName) {
        return goodsMapper.selectByGoodsName(goodName);
    }

    @Override
    public String getToken(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String username = verify.getClaim("username").asString();
            return username;
        }
        return null;
    }

    @Override
    public boolean saveGoods(Goods goods) {
        return goodsMapper.insert(goods)>0;
    }

    @Override
    public boolean updateGoods(Goods goods) {
        return goodsMapper.updateByPrimaryKey(goods)>0;
    }

    @Override
    public boolean deleteGoods(Long goodId) {
        return goodsMapper.deleteByPrimaryKey(goodId)>0;
    }
}
