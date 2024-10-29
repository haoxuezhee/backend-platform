package com.hxt.project.service.impl;

import com.hxt.project.bean.LoginLog;
import com.hxt.project.bean.User;
import com.hxt.project.mapper.LoginLogMapper;
import com.hxt.project.mapper.UserMapper;
import com.hxt.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: UserServiceImpl
 * Package: com.hxt.project.service.impl
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/30 15:13
 * @Version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;


    @Override
    public User findUserByName(String username, HttpServletRequest request) {
        return userMapper.queryByUsername(username);
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    @Override
    public List<User> getUsersByPhone(String phone,String username) {
        return userMapper.queryByPhoneUsersAndUsername(phone,username);
    }

    @Override
    public boolean saveUser(User user) {
        return userMapper.insert(user)>0;
    }

    @Override
    public boolean updateUser(User user) {
        return userMapper.updateByPrimaryKey(user)>0;
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean saveLoginLog(LoginLog loginLog) {
        return loginLogMapper.insert(loginLog)>0;
    }


}
