package com.hxt.project.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.Variable;
import com.hxt.project.mapper.VariableMapper;
import com.hxt.project.service.VariableService;
import com.hxt.project.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: VariableServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 17:23
 * @Version 1.0
 */
@Service
@Transactional
public class VariableServiceImpl implements VariableService {

    @Autowired
    private VariableMapper variableMapper;
    @Override
    public List<Variable> getAllVar() {
        return variableMapper.selectAll();
    }

    @Override
    public List<Variable> getAllByVarNameAndLabel(String varName, String varLabel) {
        return variableMapper.getAllByVarNameAndLabel(varName,varLabel);
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
    public boolean saveVAr(Variable variable) {
        return variableMapper.insert(variable)>0;
    }

    @Override
    public boolean updateVar(Variable variable) {
        return variableMapper.updateByPrimaryKey(variable)>0;
    }

    @Override
    public boolean deleteVar(Integer varId) {
        return variableMapper.deleteByPrimaryKey(varId)>0;
    }
}
