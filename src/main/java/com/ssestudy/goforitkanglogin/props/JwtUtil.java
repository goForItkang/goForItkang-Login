package com.ssestudy.goforitkanglogin.props;

import com.ssestudy.goforitkanglogin.dto.MemberDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    } //서명 키를 만듬

    public String createToken(String username) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //header 타입을 jwt
                .setSubject(username) // 사용자 이름 설정
                .claim("username",username)
                .claim("role", "USER")
                .setIssuedAt(new Date()) // 발급 시간
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24)) // 만료 시간
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // 서명
                .compact();
    }
    //유효한지 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey()) // 서명 키를 설정
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱
            return true;
        } catch (Exception e) {
            return false; // 유효하지 않은 경우
        }
    }
    public String getUser(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean isExpired(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build().
                parseClaimsJws(token).getBody();
        if(claims.getExpiration().before(new Date())){
            return true;
        }
        return false;
    }


    public String getRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token).getBody();
       return claims.get("role").toString();
    }
}
