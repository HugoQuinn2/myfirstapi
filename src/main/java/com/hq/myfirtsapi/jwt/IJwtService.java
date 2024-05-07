package com.hq.myfirtsapi.jwt;

import com.hq.myfirtsapi.apiusers.UserApiModel;
import com.hq.myfirtsapi.helpers.ReaderProperties;
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
public class IJwtService {
    private static final String SECRET_KEY = "061101061101061101061101061101060611010611010611010611010611010";

    /*public IJwtService(){
        ReaderProperties properties = new ReaderProperties("../../../resource/aplication.properties");
        this.SECRET_KEY = properties.getSECRET_KEY();
        System.out.println(SECRET_KEY);
    }*/

    public String getToken(UserApiModel user){
        return getToken(new HashMap<>(), user);
    }
    private String getToken(Map<String, Object> extraclaims, UserDetails user){
        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))  //Fecha de inicio de vigencia de la llave
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //Fecha de fin de vigenca +1 dia
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
