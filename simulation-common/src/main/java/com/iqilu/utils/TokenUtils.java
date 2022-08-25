package com.iqilu.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.iqilu.config.ClientAesDecrypt;
import com.iqilu.enums.token.TokenEnum;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Token 工具类
 *
 * @author zhangyicheng
 * @date 2020/05/21
 */
@Slf4j
public class TokenUtils {

    /**
     * 后台token过期时间
     */
    private static final int EXPIRE_TIME = 604800000;

    /**
     * 上传: 图片视频上传Token过期时间(半天)
     */
    private static final int UPLOAD_EXPIRE_TIME = 43200000;

    /**
     * 评论系统: Token过期时间(半天)
     */
    private static final int COMMENT_EXPIRE_TIME = 43200000;

    /**
     * 解析Token
     *
     * @param token token字符串
     * @return 解析的token字符串信息
     * @author zhangyicheng
     * @date 2020/06/25
     */
    public static JSONObject decode(String token) {
        String body = token.split("\\.")[1];
        return JSONObject.parseObject(new String(Base64.getUrlDecoder().decode(body)));
    }

    /**
     * 验证后台跳转token
     *
     * @author zhangyc
     * @date 2021/12/13
     */
    public static Boolean verifyAdminWeb(String jwt, String secret) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(jwt);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    /**
     * 生成新token - 兼容客户端openid
     *
     * @author zhangyicheng
     * @date 2020/09/10
     */
    public static String setAppToken(String token, String secret, Integer type) throws Exception {
        JSONObject tokenBody = TokenUtils.decode(token);
        String jwt = token;
        // 目前仅前端APP转换
        if (type != null && type == 1 && tokenBody.get(TokenEnum.OPEN_ID.getType()) != null &&
                tokenBody.get(TokenEnum.OPEN_ID.getType()) != null) {
            String openid = tokenBody.getString("openid");
            String oldId = tokenBody.getString("id");
            String id = null == openid ? oldId : ClientAesDecrypt.aesDecode(openid);
            Map<String, Object> map = new HashMap<>(16);
            map.put("typ", "JWT");
            jwt = JWT.create().withHeader(map)
                    .withClaim("id", Integer.valueOf(id))
                    .withClaim("openid", tokenBody.getString("openid"))
                    .withClaim("orgid", tokenBody.getString("orgid"))
                    .withClaim("nickname", tokenBody.getString("nickname"))
                    .withClaim("avatar", tokenBody.getString("avatar"))
                    .withClaim("platform", tokenBody.getString("platform"))
                    .sign(Algorithm.HMAC256(secret));
            log.info("生成新APP - TOKEN: {}", jwt);
        }
        return jwt;
    }

    /**
     * 生成本系统访问token
     *
     * @param secret 密钥
     * @param menu   菜单权限
     * @return Token字符串
     * @author zhangyicheng
     * @date 2020/06/24
     */
    public static String getAdminToken(String jwt, String secret, String menu) {
        JSONObject tokenBody = TokenUtils.decode(jwt);
        String token = JWT.create()
                .withClaim("id", tokenBody.getString("id"))
                .withClaim("nickname", tokenBody.getString("nickname"))
                .withClaim("avatar", tokenBody.getString("avatar"))
                .withClaim("orgid", tokenBody.getString("orgid"))
                .withClaim("orgname", tokenBody.getString("orgname"))
                .withClaim("priv", tokenBody.getString(TokenEnum.PRIV.getType()))
                .withClaim("platform", tokenBody.getString("platform"))
                .withClaim("departmentid", tokenBody.getString("departmentid"))
                // 该用户的权限
                .withClaim("menu", menu)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 生成上传附件token
     *
     * @return token
     * @author zhangyicheng
     * @date 2020/06/23
     */
    public static String getUploadToken(Integer userId, String secret, String orgId) {
        Date expiresDate = new Date(System.currentTimeMillis() + UPLOAD_EXPIRE_TIME);
        Map<String, Object> map = new HashMap<>(16);
        map.put("typ", "JWT");
        String token = "";
        token = JWT.create().withHeader(map)
                .withClaim("userid", userId)
                .withClaim("orgid", orgId)
                .withClaim("app", "gov")
                .withClaim("time", DateTimeUtils.getSecondTimestampTwo(new Date()))
                .withIssuedAt(new Date())
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(secret));
        return token;
    }

    /**
     * 生成评论系统token
     *
     * @return token串
     * @author zhangyicheng
     * @date 2020/06/24
     */
    public static String getCommentToken(String secret, JSONObject tokenBody) {
        Date expiresDate = new Date(System.currentTimeMillis() + COMMENT_EXPIRE_TIME);
        byte[] encodedKey = secret.getBytes();
        /// SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HS256");
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.claim("exp", expiresDate);
        jwtBuilder.claim("time", new Date());
        jwtBuilder.claim("id", tokenBody.getString("id"));
        jwtBuilder.claim("orgid", tokenBody.getString("orgid"));
        jwtBuilder.claim("nickname", tokenBody.getString("nickname"));
        jwtBuilder.claim("avatar", tokenBody.getString("avatar"));
        jwtBuilder.claim("platform", "chuangqi");
        /// jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        return jwtBuilder.compact();
    }

    /**
     * 获取参数中的机构id
     *
     * @author zhangyc
     * @date 2021/11/23
     */
    public static Integer getOrgId(HttpServletRequest request) {
        String orgId = request.getHeader("orgId");
        if (orgId == null) {
            orgId = request.getHeader(TokenEnum.ORG_ID.getType());
        }
        if (orgId == null) {
            String token = request.getHeader("CQ-TOKEN");
            if (StringUtils.isNotEmpty(token)) {
                JSONObject tokenBody = decode(token);
                orgId = tokenBody.getString(TokenEnum.ORG_ID.getType());
            }
        }
        if (orgId == null) {
            request.getParameter(TokenEnum.ORG_ID.getType());
        }

        return orgId != null ? Integer.valueOf(orgId) : null;
    }

    /**
     * 获取token
     *
     * @author zhangyc
     * @date 2021/11/28
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("CQ-TOKEN");
        if (token == null) {
            token = request.getHeader("simulationToken");
        }
        if (token == null) {
            token = request.getParameter("jwt");
        }
        return token;
    }

}
