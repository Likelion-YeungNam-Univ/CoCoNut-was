package CoCoNut_was.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final long accessValid = 1000L * 60 * 60; // 1시간
    private final long refreshValid = 1000L * 60 * 60 * 24 * 7; // 7일

    // 1. Access 토큰 발급
    public String createAccessToken(Long id, String email) {
        return generateToken(id, email, accessValid);
    }
    // 2. Refresh 토큰 발급
    public String createRefreshToken(Long id, String email) {
        return generateToken(id, email, refreshValid);
    }

//    private String generateToken(String email, long validTime) {
//        return Jwts.builder()
//                .subject(email)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + validTime))
//                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }

    private String generateToken(Long id, String email, long validTime) {
        return Jwts.builder()
                .subject(id.toString())
                .claim("email", email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + validTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }

    // 제대로 작동되는지는 테스트 필요
//    public String extractRefreshTokenFromCookie(HttpServletRequest request) {
//        if (request.getCookies() != null) {
//            for (Cookie cookie : request.getCookies()) {
//                if ("refreshToken".equals(cookie.getName())) {
//                    return cookie.getValue();
//                }
//            }
//        }
//        return null;
//    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())   // 서명 검증 키 설정
                .build()                       // JwtParser 생성
                .parseClaimsJws(token);        // JWS 파싱 & 검증

        return jws.getBody().get("email", String.class);
    }

    public Long getIdFromToken(String token) {
        Jws<Claims> jws = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())   // 서명 검증 키 설정
                .build()                       // JwtParser 생성
                .parseClaimsJws(token);        // JWS 파싱 & 검증

        return Long.parseLong(jws.getBody().getSubject());
    }
}

