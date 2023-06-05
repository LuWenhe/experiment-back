package edu.nuist.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.nuist.vo.UserAndRoleVo;

import java.util.Date;
import java.util.Map;

public class JWTUtils {

    private static final String SIGN = "!Q@EeKE^EN%KN";
    private static final long TIME = 1000*60*60*24;

    /**
     * 生成token
     */
    public static String getToken(Map<String, String> map) {
        // 创建JWT的Builder
        JWTCreator.Builder builder = JWT.create();

        // 生成payload, 存入用户id和用户名
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }

        // 生成token字符串
        return builder
                .withExpiresAt(new Date(System.currentTimeMillis() + TIME))
                .sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * 解析token
     */
    public static  Map<String, Claim> parseToken(String token) {
        DecodedJWT decodedJWT = verify(token);
        return decodedJWT.getClaims();
    }

    /**
     * 解析Token获取用户对象
     */
    public static UserAndRoleVo getUserAndRoleFromToken(String token) {
        DecodedJWT jwt = verify(token);
        Map<String, Claim> claimMap = jwt.getClaims();

        Integer userId = Integer.valueOf(claimMap.get("userId").asString());
        String username = claimMap.get("username").asString();
        Integer roleId = Integer.valueOf(claimMap.get("roleId").asString());
        String roleName = claimMap.get("roleName").asString();

        return new UserAndRoleVo(userId, username, roleId, roleName);
    }

    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

}
