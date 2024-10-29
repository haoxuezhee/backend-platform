package com.hxt.project.test;


import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.UUID;

/**
 * ClassName: JwtTest
 * Package: com.hxt.project.test
 * Description:
 *
 * @Author hxt
 * @Create 2024/8/31 16:51
 * @Version 1.0
 */
public class JwtTest {



    public static void main(String[] args) {
//        long time = 1000*60*60*24;
//        String signature = "11111111112222222222333333333344444444445555555555";
//        String jwtToken = jwtToken(time, signature);
//        System.out.println(jwtToken);
//
//        Claims claims = jwtPare(jwtToken, signature);
//        System.out.println(claims);
//    }
//
//
//    public static String jwtToken(long time,String signature){
//        JwtBuilder jwtBuilder= Jwts.builder();
//        String jwtToken = jwtBuilder
//                //header
//                .setHeaderParam("type", "JWT")
//                .setHeaderParam("alg", "HS256")
//                //payload
//                .claim("username", "tom")
//                .claim("role", "admin")
//                .setSubject("admin-test")
//                .setExpiration(new Date(System.currentTimeMillis() + time))
//                .setId(UUID.randomUUID().toString())
//                //signature
//                .signWith(SignatureAlgorithm.HS256, signature)
//                .compact();
//        return jwtToken;
//    }
//
//    public static Claims jwtPare(String token,String signature){
//        JwtParser jwtParser=Jwts.parser();
//        Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
//        Claims claims = claimsJws.getBody();
//        return claims;
//    }

    }
}
