package com.hxt.project.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.HashMap;

/**
 * ClassName: Jwt
 * Package: com.hxt.project.test
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/31 17:38
 * @Version 1.0
 */
public class Jwt {

    public static void main(String[] args) {


    }


    @Test
    public void test01(){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("asdcfgetrycggsgd")).build();
        DecodedJWT verify = jwtVerifier.verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRvbSIsInVzZXJJZCI6IjAwMSIsImV4cCI6MTcyNTA5ODA3Nn0.piT_f-0lQfEIeIFai5MuASaiT-SwPpqFtHKPaQNGd1Q");
        System.out.println(verify.getClaim("username"));
        System.out.println(verify.getClaim("userId"));

    }
}
