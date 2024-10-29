package com.hxt.project.controller;


import com.hxt.project.bean.Dept;
import com.hxt.project.common.ErrorCode;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.exception.BusinessException;
import com.hxt.project.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
@RequestMapping("/dept")
@Slf4j
public class DeptController {


    @Autowired
    private DeptService deptService;

    @GetMapping("/list")
    public Resp getAllDept(String deptName,String deptNo){
        if (deptName == null || deptName.equals("") && deptNo == null || deptNo.equals("")) {
            List<Dept> deptList = deptService.getAllDept();
            return Resp.builder().data(deptList).msg("获取部门信息中")
                    .code(deptList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
        }

        List<Dept> deptList = deptService.getAllByDeptNameAndNo(deptName, deptNo);
        return Resp.builder().data(deptList).msg("获取部门信息中")
                .code(deptList!=null?RespCode.QUERY_SUCCESS_CODE:RespCode.QUERY_FAIL_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveDept(@RequestBody Dept dept,HttpServletRequest request){
        if(dept == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String token = request.getHeader("token");
        String username = deptService.getToken(token);
        dept.setEditTime(new Date());
        dept.setEditUser(username);
        boolean ret = deptService.saveDept(dept);
        if(!ret){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"保存失败");
        }
        return Resp.builder()
                .data(true)
                .msg("保存成功")
                .code(RespCode.SAVE_SUCCESS_CODE)
                .build();
    }


    @PostMapping("/update")
    public Resp updateDept(@RequestBody Dept dept,HttpServletRequest request){
        if(dept == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        log.info("请求update方法的入参为:{}",dept);
        String token = request.getHeader("token");
        String username = deptService.getToken(token);
        dept.setEditTime(new Date());
        dept.setEditUser(username);
        boolean ret = deptService.updateDept(dept);
        if(!ret){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return Resp.builder().
                code(RespCode.UPDATE_SUCCESS_CODE)
                .data(true)
                .msg("修改成功")
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteDept(@PathVariable Integer id){
        log.info("请求delete方法的入参为:{}",id);
        if(id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean ret = deptService.deleteDept(id);
        if(!ret){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"删除失败");
        }
        return Resp.builder().
                code(RespCode.DELETE_SUCCESS_CODE)
                .data(true)
                .msg("删除成功")
                .build();
    }



}
