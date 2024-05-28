package com.hq.myfirtsapi.jwt;

import com.hq.myfirtsapi.apiusers.AuthError;
import com.hq.myfirtsapi.apiusers.UserApiModel;
import com.hq.myfirtsapi.helpers.ReaderProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class IJwtService {
    @Value("${key.private}")
    private String SECRET_KEY;

    public String getToken(UserDetails user){
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraclaims, UserDetails user){
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

        Date issueDate = new Date(System.currentTimeMillis()); //Fecha creacion de llave
        Date expirationDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)); //Fecha expiracion de llave +1 dia

        log.info(AuthError.TOKEN_CREATED.getMessage(user.getUsername(), df.format(issueDate), df.format(expirationDate)));

        return Jwts
                .builder()
                .setClaims(extraclaims)
                .setSubject(user.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
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
        boolean tokenState = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        if(isTokenExpired(token)){
            log.info(AuthError.TOKEN_EXPIRED.getMessage());
        }else if (!username.equals(userDetails.getUsername())){
            log.info(AuthError.INVALID_USER.getMessage());
        } else{
            return true;
        }
        return false;
    }

    //Obtenener los claims del token
    private Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey()) //especificar clabe
                .build()
                .parseClaimsJws(token) //cetear el claim
                .getBody(); //Obtener el cuerpo
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
