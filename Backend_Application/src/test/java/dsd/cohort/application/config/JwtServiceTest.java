package dsd.cohort.application.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @TestConfiguration
    static class JwtServiceTestConfiguration {

        @Bean
        public JwtService jwtService() {
            return new JwtService("TotallyTestSecret");
        }
    }

    @MockBean
    @Autowired
    private JwtService jwtService;

    private UserDetails userDetails;
    private String token1;
    private SecretKey secretKey;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtService, "secret", "TotallyTestSecret");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000);
        ReflectionTestUtils.setField(jwtService, "refreshToken", 604800000);

        secretKey = ReflectionTestUtils.invokeMethod(jwtService, "getVerifyKey");

        userDetails = createUserDetails();
        token1 = jwtService.generateToken(userDetails);
    }

    private UserDetails createUserDetails() {
        return User.withUsername("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();
    }

    private Claims createClaims() {
        return (Claims) Jwts.parser().verifyWith(secretKey).build();
    }

    @Test
    public void ExtractUsername_ValidToken_ReturnsUsername() {
        String token = token1;

        // Act
        String username = jwtService.extractUsername(token);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    public void GenerateToken_ValidUserDetails_ReturnsValidToken() {
        // Act
        String token = token1;

        // Assert
        Claims claims = (Claims) Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        assertEquals("testuser", claims.getSubject());
    }

    @Test
    public void IsTokenValid_ValidTokenAndUserDetails_ReturnsTrue() {
        String token = token1;

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }
}