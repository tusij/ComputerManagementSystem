package com.computerManagementSystem.util;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 赖志宇
 * @date 2020/6/16 23:42
 */
public class JWTUtil {
    private static final String SECRET = "JIWEI123456";
    private static final long expireTime = 7*24 * 60 * 60 * 1000;
    private static final String ISSUER = "www.jiwei.com";
    private static Algorithm algorithm = Algorithm.HMAC256(SECRET);

    /**
     * 获取一个登陆凭证
     * @param
     * @return
     */
    public static  String getToken(String name,String value) {
        long currentTimeMillis = System.currentTimeMillis();

        Date expireDate = new Date(currentTimeMillis + expireTime);
        Map<String, Object> headerClaims = new HashMap<String, Object>(2);
        headerClaims.put("alg", "HS256");
        headerClaims.put("typ", "JWT");
        try {
            return JWT.create().withHeader(headerClaims)
                    .withIssuer(ISSUER)
                    .withIssuedAt(new Date(currentTimeMillis))
                    .withExpiresAt(expireDate)
                    .withClaim(name, value)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            return null;
        } catch (Exception e) {
            return null;
        }


    }



    /**
     * 解析token 获得一个decodedJWT对象，目前没什么用，职责分离是为了给以后的开发修改时提供便利
     * @param token
     * @return
     */
    public static DecodedJWT parseToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = jwtVerifier.verify(token);
            return jwt;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }


    }

    /**
     * 验证token是否正确
     * @param token
     * @return
     */
    public static Boolean verifyToken(String token){
        if(token==null)
            return false;
        if(parseToken(token)!=null)
            return true;
        else
            return false;
    }

    /**
     * 通过名字获取value
     * @param token
     * @param name
     * @return
     */
    public static String getClaim(String token,String name){
       //还可以这样写，但我发现了一个自带的方法
       /* DecodedJWT jwt = parseToken(token);
        if(jwt!=null)
        return jwt.getClaim(name).asString();
        else
            return null;*/
       if(token==null)
           return null;
        return JWT.decode(token).getClaim(name).asString();
    }

    /**
     * 取得名为account的值，即账号
     * @param token
     * @return
     */
    public static String getClaimNamedAccount(String token){
        return getClaim(token,"account");
    }

    public static String getClaimNamedRole(String token){
        return getClaim(token,"role");
    }
    /**
     * 获取一个新的登录凭证
     * @param token
     * @return
     */
    public static String getNewToken(String token,String name){
        String value = getClaim(token,name);
        return getToken(name,value);
    }
}
