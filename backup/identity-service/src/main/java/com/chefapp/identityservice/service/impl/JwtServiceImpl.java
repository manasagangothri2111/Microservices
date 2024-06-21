package com.chefapp.identityservice.service.impl;

import com.chefapp.identityservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Override
    public String extractUserName(String token) {

        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(String userName) {

        return generateToken(new HashMap<>(), userName);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()))&& !isTokenExpired(token);
    }
    public void validateToken(final String token) {
        Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
    }

    private<T> T extractClaim(String token, Function<Claims,T> claimResolvers){
        final Claims claims = extractAllClaims(token);
        return claimResolvers.apply(claims);
    }

    private String generateToken(Map<String,Object> extraClaims, String userName){
        return Jwts.builder().setClaims(extraClaims).setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*2))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    private Key getSigningKey() {
        byte[] key = Decoders.BASE64.decode("8523698521478569874563214587532569854769321458756985647315987582");
        return Keys.hmacShaKeyFor(key);
       // Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
}
