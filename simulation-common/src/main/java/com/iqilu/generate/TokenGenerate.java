package com.iqilu.generate;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.iqilu.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

/**
 * token测试工具
 *
 * @author zhangyicheng
 * @date 2020/08/17
 */
@Slf4j
public class TokenGenerate {

    /**
     * 解析传入的token-JSON串
     *
     * @param token token字符串
     * @return 解析的token字符串信息
     * @author zhangyicheng
     * @date 2020/06/25
     */
    private static JSONObject decode(String token) {
        String body = token.split("\\.")[1];
        return JSONObject.parseObject(new String(Base64.getUrlDecoder().decode(body)));
    }

    /**
     * 测试令牌
     *
     * @author zhangyicheng
     * @date 2020/07/01
     */
    public static void main(String[] args) {
        /*
         * token加密测试
         */
        String encryptSecret = "cb5a5965ccb9412cae8717612d0e251b";
        String encryptToken = "";
        String adminToken = TokenUtils.getAdminToken(encryptToken, encryptSecret, null);
        System.out.println("生成的token: " + adminToken);

        System.out.println("-----------------------------------------------");

        /*
         * token 解密测试
         */
        String decodeSecret = "cb5a5965ccb9412cae8717612d0e251b";
        String decodeToken = "";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(decodeSecret)).build();
        try {
            jwtVerifier.verify(decodeToken);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token验证不通过, 请检查是否错误或已过期.");
        }
        System.out.println("解密成功");

    }

}
