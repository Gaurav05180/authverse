//package com.nic.auth_server.util;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.Set;
//
//@Component
//public class JwtUtil {
//
////    private final String secret = "mysecretkey"; // In production, keep it secure & in env
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//    private final long expirationMs = 3600000; // 1 hour
//
//    public String generateToken(String username, Set<String> roles) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("roles", roles)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parser().setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}


package com.nic.auth_server.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private final long expirationMs = 3600000; // 1 hour

    private Key getSigningKey() {
        // Convert the string to a 256-bit secure key
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}


