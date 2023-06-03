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
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODU4NjE0MDQsInVzZXJuYW1lI" +
                "joidGVhY2hlcjEifQ.c-2jZsuFJmD4UuChL5D6S2VnUmKtM8_ag3mhRJZkzzc";
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        for (Map.Entry<String, Claim> entry : claims.entrySet()) {
            String key = entry.getKey();
            Claim value = entry.getValue();
        }
    }

}
