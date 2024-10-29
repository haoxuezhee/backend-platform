package com.hxt.project.service;

import com.hxt.project.bean.LoginLog;
import com.hxt.project.bean.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName: UserService
 * Package: com.hxt.project.service
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/30 15:12
 * @Version 1.0
 */
public interface UserService {


    // 通过用户名查找用户
     User findUserByName(String username, HttpServletRequest request);

     //获取全部用户
    List<User> getAllUser();

    List<User> getUsersByPhone(String phone,String username);

    boolean saveUser(User user);

    boolean updateUser(User user);
    boolean deleteUser(Long id);

    User selectById(Long id);


    boolean saveLoginLog(LoginLog loginLog);
}
