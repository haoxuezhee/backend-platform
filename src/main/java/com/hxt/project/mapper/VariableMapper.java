package com.hxt.project.mapper;

import com.hxt.project.bean.Dept;
import com.hxt.project.bean.Variable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface VariableMapper {
    int deleteByPrimaryKey(Integer varid);

    int insert(Variable row);

    Variable selectByPrimaryKey(Integer varid);

    List<Variable> selectAll();

    int updateByPrimaryKey(Variable row);
    List<Variable> getAllByVarNameAndLabel(@Param("varName") String varName, @Param("varLabel") String varLabel);
}