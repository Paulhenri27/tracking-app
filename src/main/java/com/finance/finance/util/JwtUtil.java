package com.finance.finance.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil
{

    private String secretKey = "";

    public JwtUtil()
    {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGen.generateKey();

            secretKey =  Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username , String role)
    {
        Map<String, Object> claims = new HashMap<>();

        claims.put("role", role);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();
    }


    private SecretKey getKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUser(String jwtToken)
    {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public boolean validateToken(String jwtToken , UserDetails userDetails)
    {
        final String username = extractClaim(jwtToken, Claims::getSubject);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String jwtToken)
    {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken)
    {
        return extractClaim(jwtToken, Claims::getExpiration);
    }


    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken)
    {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }
}
