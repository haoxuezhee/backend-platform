package com.hxt.project.mapper;

import com.hxt.project.bean.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface LoginLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginLog row);

    LoginLog selectByPrimaryKey(Integer id);

    List<LoginLog> selectAll();

    int updateByPrimaryKey(LoginLog row);
}