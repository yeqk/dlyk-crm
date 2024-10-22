package io.github.yeqk97.dlykserver.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.yeqk97.dlykserver.model.TUser;

import java.util.HashMap;
import java.util.Map;

public class JWTUtils {

    public static final String SECRET = "w3osER)wTxc0>pTr03ryP";

    /**
     * 生成jwt
     *
     * @return
     */
    public static String createJWT(String userJSON) {
        // 组装头数据
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        // 链式编程
        return JWT.create()
                .withHeader(header)
                .withClaim("user", userJSON)
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证jwt
     *
     */
    public static Boolean verifyJWT(String jwt) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            jwtVerifier.verify(jwt);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析jwt
     *
     */
    public static String parseJWT(String jwt) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();

            DecodedJWT decodedJWT = jwtVerifier.verify(jwt);

            Claim userJSON = decodedJWT.getClaim("user");

            return userJSON.asString();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static TUser parseUserFromJWT(String jwt) {
        String userJSON = parseJWT(jwt);
        return JSONUtils.toObject(userJSON, TUser.class);
    }
}