package com.hxt.project.controller;


import com.hxt.project.bean.Dept;
import com.hxt.project.bean.Variable;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.DeptService;
import com.hxt.project.service.VariableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * ClassName: DeptController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 10:14
 * @Version 1.0
 */
@RestController
@RequestMapping("/var")
@Slf4j
public class VariableController {


    @Autowired
    private VariableService variableService;

    @GetMapping("/list")
    public Resp getAllVar(String varName,String varLabel){
        if (varName == null || varName.equals("") && varLabel == null || varLabel.equals("")) {
            List<Variable> variableList = variableService.getAllVar();
            return Resp.builder().data(variableList).msg("获取系统变量信息中")
                    .code(variableList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Variable> variableList = variableService.getAllByVarNameAndLabel(varName, varLabel);
        return Resp.builder().data(variableList).msg("获取系统变量信息中")
                .code(variableList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveVar(@RequestBody Variable variable, HttpServletRequest request){
        String token = request.getHeader("token");
        String username = variableService.getToken(token);
        variable.setEditTime(new Date());
        variable.setEditUser(username);
        boolean ret = variableService.saveVAr(variable);
        return Resp.builder()
                .data(ret)
                .msg(ret?"保存成功":"保存失败")
                .code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateVAr(@RequestBody Variable variable,HttpServletRequest request){
        log.info("请求update方法的入参为:{}",variable);
        String token = request.getHeader("token");
        String username = variableService.getToken(token);
        variable.setEditTime(new Date());
        variable.setEditUser(username);
        boolean ret = variableService.updateVar(variable);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .msg(ret?"修改成功":"修改失败")
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteVar(@PathVariable Integer id){
        log.info("请求delete方法的入参为:{}",id);
        boolean ret = variableService.deleteVar(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .msg(ret?"删除成功":"删除失败")
                .build();
    }



}
