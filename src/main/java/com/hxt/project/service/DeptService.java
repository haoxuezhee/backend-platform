package com.hxt.project.service;

import com.hxt.project.bean.Dept;
import com.hxt.project.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: DeptService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/9/7 10:25
 * @Version 1.0
 */
public interface DeptService {

    List<Dept> getAllDept();

    List<Dept> getAllByDeptNameAndNo(String deptName, String deptNo);

    String getToken(String token);

    boolean saveDept(Dept dept);

    boolean updateDept(Dept dept);

    boolean deleteDept(Integer deptId);
}
