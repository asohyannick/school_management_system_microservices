package com.ecol.apiGateway.jwt;
import com.ecol.apiGateway.exception.badRequestException.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtValidator {

    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    private SecretKey getSigningKey() throws  Exception {
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new BadRequestException("JWT secret key must be at least 32 characters");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims validate(String token) throws  Exception {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}