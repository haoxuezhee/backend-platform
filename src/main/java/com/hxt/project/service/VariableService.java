package com.hxt.project.service;

import com.hxt.project.bean.Dept;
import com.hxt.project.bean.Variable;

import java.util.List;

/**
 * ClassName: VariableService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/15 17:22
 * @Version 1.0
 */
public interface VariableService {


    List<Variable> getAllVar();

    List<Variable> getAllByVarNameAndLabel(String deptName, String deptNo);

    String getToken(String token);

    boolean saveVAr(Variable variable);

    boolean updateVar(Variable variable);

    boolean deleteVar(Integer varId);
}
