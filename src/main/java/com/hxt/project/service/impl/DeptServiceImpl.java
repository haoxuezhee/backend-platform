package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.Dept;
import com.hxt.project.bean.User;
import com.hxt.project.mapper.DeptMapper;
import com.hxt.project.service.DeptService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: DeptServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 10:25
 * @Version 1.0
 */
@Service
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;
    @Override
    public List<Dept> getAllDept() {
        return deptMapper.selectAll();
    }

    @Override
    public List<Dept> getAllByDeptNameAndNo(String deptName, String deptNo) {

        return deptMapper.getAllByDeptNameAndDeptNo(deptName,deptNo);
    }

    @Override
    public boolean saveDept(Dept dept) {
        return deptMapper.insert(dept)>0;
    }

    @Override
    public boolean updateDept(Dept dept) {
        return deptMapper.updateByPrimaryKey(dept)>0;
    }

    @Override
    public boolean deleteDept(Integer deptId) {
        return deptMapper.deleteByPrimaryKey(deptId)>0;
    }


    public String getToken(String token){
        DecodedJWT verify = JwtUtil.verify(token);
        if (token != null) {
            String username = verify.getClaim("username").asString();
            return username;
        }
        return null;
    }
}
