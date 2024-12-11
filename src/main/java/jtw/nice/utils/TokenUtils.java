package jtw.nice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Encrypt token.
     */
    public String getToken(int userId, String userRole, int groupId) {
        long now = System.currentTimeMillis();
        // 设置过期时间
        Date expiryDate = new Date(now + expiration * 1000);

        // 生成 token
        return JWT.create()
                .withClaim("user_id", userId)
                .withClaim("userRole", userRole)
                .withClaim("group_id", groupId)
                .withClaim("timeStamp", now)
                .withExpiresAt(expiryDate)
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * Parse token.
     */
    public Map<String, String> parseToken(String token) {
        Map<String, String> map = new HashMap<>();
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                .build().verify(token);

        map.put("user_id", String.valueOf(decodedJWT.getClaim("user_id").asInt()));
        map.put("userRole", decodedJWT.getClaim("userRole").asString());
        map.put("group_id", String.valueOf(decodedJWT.getClaim("group_id").asInt()));
        map.put("timeStamp", String.valueOf(decodedJWT.getClaim("timeStamp").asLong()));
        return map;
    }

    /**
     * Decode token
     */
    public DecodedJWT decodeToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
    }
}

