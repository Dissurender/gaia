package dsd.cohort.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.security.jwt.secret-key}")
    private String secret;
    @Value("${spring.security.jwt.expiration}")
    private Long jwtExpiration;
    @Value("${spring.security.jwt.refresh-token.expiration}")
    private Long refreshExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(
            UserDetails userDetails
    ) {
        return buildToken(userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(userDetails, refreshExpiration);
    }

    private String buildToken(
            UserDetails userDetails,
            Long expiration
    ) {
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getVerifyKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts
                .parser()
                .verifyWith(getVerifyKey())
                .build()
                .parseSignedClaims(token);
    }

    private SecretKey getVerifyKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HS256");
    }
}
