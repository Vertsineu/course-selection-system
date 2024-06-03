package cn.ustc.courseselectionsystem.util;

import cn.ustc.courseselectionsystem.exp.TokenIllegalException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    @Value("${token.secretKey}")
    private String secretKey;

    public String createToken(String username, String password) {
        return JWT.create()
                .withClaim("username", username)
                .withClaim("password", password)
                .withClaim("timeStamp", System.currentTimeMillis())
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Map<String, String> parseToken(String token) {
        try {
            HashMap<String, String> map = new HashMap<>();
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey))
                    .build().verify(token);
            Claim userId = decodedJWT.getClaim("username");
            Claim userRole = decodedJWT.getClaim("password");
            Claim timeStamp = decodedJWT.getClaim("timeStamp");
            map.put("username", userId.asString());
            map.put("password", userRole.asString());
            map.put("timeStamp", timeStamp.asLong().toString());
            return map;
        } catch (Exception e) {
            throw new TokenIllegalException("Token解析失败", e);
        }
    }
}
