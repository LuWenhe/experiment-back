package edu.nuist.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class JWTTest {

    @Test
    void testJwt() {
        String SIGN = "!Q@EeKE^EN%KN";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWQiOiIyIiwicm9sZU5hbWUiOiLogIHluIgiLCJle" +
                "HAiOjE2ODYwNjAzMTMsInVzZXJJZCI6IjIiLCJ1c2VybmFtZSI6InRlYWNoZXIxIn0." +
                "GT7hGBM94hdQkPQKYUvckKAhUnoY_SDbbAFD52wGGgc";
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Map<String, Claim> claimMap = decodedJWT.getClaims();

        System.out.println(claimMap);
        Integer userId = Integer.valueOf(claimMap.get("userId").asString());
        String username = claimMap.get("username").asString();
        Integer roleId = Integer.valueOf(claimMap.get("roleId").asString());
        String roleName = claimMap.get("roleName").asString();

        System.out.println(userId);
        System.out.println(username);
        System.out.println(roleId);
        System.out.println(roleName);
    }

}
