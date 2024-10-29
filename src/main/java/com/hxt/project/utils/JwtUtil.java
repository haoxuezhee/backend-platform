package com.hxt.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.experimental.Tolerate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: JwtUtil
 * Package: com.hxt.project.utils
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/31 19:43
 * @Version 1.0
 */
public class JwtUtil {

    private static final String SIGN="!hgfrt#23wesserw3*";

    /**
     * 生成token
     * @param map
     * @return
     */
    public static String getToken(Map<String,String> map){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,1);

        JWTCreator.Builder builder = JWT.create();
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SIGN));

        return token;
    }

    /**
     * 验证token
     * @param token
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    /**
     * 获取token中信息
     * @param token
     * @return
     */
//    public static DecodedJWT getTokenInfo(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
//       return verify;
//    }

}
