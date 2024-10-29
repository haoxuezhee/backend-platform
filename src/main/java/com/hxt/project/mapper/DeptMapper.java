package com.hxt.project.mapper;

import com.hxt.project.bean.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Dept row);

    Dept selectByPrimaryKey(Integer deptId);

    List<Dept> selectAll();

    int updateByPrimaryKey(Dept row);

    List<Dept> getAllByDeptNameAndDeptNo(@Param("deptName") String deptName, @Param("deptNo") String deptNo);
}