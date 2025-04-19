package com.wtc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    // 密钥原始字符串（建议通过配置中心管理）
    private static final String SECRET_ORIGINAL = "wtc";
    // 有效期（12小时）
    private static final long EXPIRATION = 12 * 3600 * 1000;

    /**
     * 生成 JWT 令牌
     * 
     * @param claims 自定义声明内容
     * @return JWT令牌字符串
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String generateToken(Map<String, Object> claims)
            throws InvalidKeyException, NoSuchAlgorithmException {
        return Jwts.builder()
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 解析 JWT 令牌
     * 
     * @param token JWT令牌字符串
     * @return 声明内容
     * @throws NoSuchAlgorithmException
     * @throws IllegalArgumentException
     * @throws JwtException
     */
    public static Claims parseToken(String token)
            throws JwtException, IllegalArgumentException, NoSuchAlgorithmException {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 统一密钥生成方法
     * 
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey getSecretKey() throws NoSuchAlgorithmException {
        // 使用SHA-256扩展原始密钥
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(SECRET_ORIGINAL.getBytes(StandardCharsets.UTF_8));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}