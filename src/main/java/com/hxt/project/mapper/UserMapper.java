package com.hxt.project.mapper;

import com.hxt.project.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User row);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User row);

    User queryByUsername(@Param("username") String username);

    List<User> queryByPhoneUsersAndUsername(@Param("phone") String phone, @Param("username") String username);

}