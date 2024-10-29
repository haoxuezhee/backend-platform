package com.hxt.project.controller;

import com.hxt.project.bean.Goods;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * ClassName: GoodsController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 16:32
 * @Version 1.0
 */

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public Resp getAllGoods(String goodName){
        if (goodName == null || goodName.equals("")) {
            List<Goods> goodsList = goodsService.getAllGoods();
            return Resp.builder().data(goodsList).msg("获取商品信息中")
                    .code(goodsList!=null? RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Goods> goodsList = goodsService.getAllByGoodsName(goodName);
        log.info("goodList:{}",goodsList);
        return Resp.builder().data(goodsList).msg("获取商品信息中")
                .code(goodsList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveGoods(@RequestBody Goods goods, HttpServletRequest request){
        String token = request.getHeader("token");
        String username = goodsService.getToken(token);
        goods.setEditTime(new Date());
        goods.setEditUser(username);
        boolean ret = goodsService.saveGoods(goods);
        return Resp.builder()
                .data(ret)
                .msg(ret?"保存成功":"保存失败")
                .code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateGood(@RequestBody Goods goods,HttpServletRequest request){
        log.info("请求update方法的入参为:{}",goods);
        String token = request.getHeader("token");
        String username = goodsService.getToken(token);
        goods.setEditTime(new Date());
        goods.setEditUser(username);
        boolean ret = goodsService.updateGoods(goods);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .msg(ret?"修改成功":"修改失败")
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteGood(@PathVariable Long id){
        log.info("请求delete方法的入参为:{}",id);
        boolean ret = goodsService.deleteGoods(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .msg(ret?"删除成功":"删除失败")
                .build();
    }



}

