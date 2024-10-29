package com.hxt.project.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.hxt.project.bean.LoginLog;
import com.hxt.project.bean.User;
import com.hxt.project.common.Resp;
import com.hxt.project.common.RespCode;
import com.hxt.project.service.UserService;
import com.hxt.project.utils.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ItemController
 * Package: com.hxt.project.controller
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/30 10:58
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Resp login(String username, String password,HttpServletRequest request) {
        User loginUser = userService.findUserByName(username,request);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(loginUser.getId()));
        map.put("username", loginUser.getUsername());
        map.put("status",String.valueOf(loginUser.getStatus()));
        String token = JwtUtil.getToken(map);
        System.out.println(token);
        if (loginUser != null) {
            if (password.equals(loginUser.getPassword())) {
                loginUser.setPassword("");
                // 记录登录日志
                LoginLog loginLog = new LoginLog();
                loginLog.setUserId(loginUser.getId());
                loginLog.setLoginTime(new Date()); // 获取当前时间作为登录时间
                loginLog.setLoginIp(request.getRemoteAddr()); // 获取请求的IP地址
                userService.saveLoginLog(loginLog); // 调用UserService的方法保存登录日志
                redisTemplate.opsForValue().set(token,loginUser,3, TimeUnit.DAYS);
                return Resp.builder().data(token).msg("登陆成功").code(RespCode.LOGIN_SUCCESS_CODE).build();
            }
        }
        return Resp.builder().data(null).msg("登陆失败").code(RespCode.LOGIN_FAIL_CODE).build();

    }

    @GetMapping("/info")
    public Resp profile(HttpServletRequest request) {
        String token = request.getHeader("data");
        if (token != null) {
            HashMap<String, Object> map = new HashMap<>();
            DecodedJWT verify = JwtUtil.verify(token);
            String username = verify.getClaim("username").asString();
            User user = userService.findUserByName(username,request);
            user.setPassword("");
            map.put("user", user);
            log.info(MDC.get("userId"), MDC.get("userName"));
            return Resp.builder().data(map).msg("获取用户信息成功").code(RespCode.TOKEN_SUCCESS_CODE).build();
        }
        return Resp.builder().data(null).msg("未提供Token").code(RespCode.TOKEN_FAIL_CODE).build();
    }


    @GetMapping("/list")
    public Resp getUserByPhone(String phone, String username) {
        if (phone == null || phone.equals("") && username == null || username.equals("")) {
            List<User> allUser = userService.getAllUser();
            return Resp.builder().data(allUser).msg("获取全部用户成功").code(RespCode.QUERY_SUCCESS_CODE).build();
        }
        List<User> userList = userService.getUsersByPhone(phone, username);
        return Resp.builder().data(userList).msg("查询成功").code(RespCode.QUERY_SUCCESS_CODE).build();
    }

    @PostMapping("/save")
    public Resp saveUser(@RequestBody User user){
        user.setUpdateTime(new Date());
        boolean ret = userService.saveUser(user);
        return Resp.builder().data(ret).msg("保存成功").code(ret?RespCode.SAVE_SUCCESS_CODE:RespCode.SAVE_FAIL_CODE).build();
    }


    @PostMapping("/update")
    public Resp updateUser(@RequestBody User user){
        log.info("请求update方法的入参为:{}",user);
        user.setUpdateTime(new Date());
        boolean ret = userService.updateUser(user);
        return Resp.builder().
                code(ret?RespCode.UPDATE_SUCCESS_CODE:RespCode.UPDATE_FAIL_CODE)
                .data(ret)
                .build();
    }

    @PostMapping("/delete/{id}")
    public Resp deleteUser(@PathVariable Long id){
        log.info("请求delete方法的入参为:{}",id);
        boolean ret = userService.deleteUser(id);
        return Resp.builder().
                code(ret?RespCode.DELETE_SUCCESS_CODE:RespCode.DELETE_FAIL_CODE)
                .data(ret)
                .build();
    }

    @PostMapping("/resetPwd")
    public  Resp resetPassword(@RequestBody Map<String,Long> request){
        log.info("请求resetPwd方法的入参为:{}",request);
        Long id = request.get("id");
        User user = userService.selectById(id);
        user.setPassword("111111");
        userService.updateUser(user);
        return Resp.builder().
                code(user!=null?RespCode.RESETPWD_SUCCESS_CODE:RespCode.RESETPWD_FAIL_CODE)
                .data(user)
                .build();
    }


}
