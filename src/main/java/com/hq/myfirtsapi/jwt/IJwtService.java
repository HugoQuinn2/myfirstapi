package com.hq.myfirtsapi.jwt;

import com.hq.myfirtsapi.apiusers.UserApiModel;
import com.hq.myfirtsapi.helpers.ReaderProperties;
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

@Service
public class IJwtService {
    private String SECRET_KEY = null;
    public IJwtService(){
        ReaderProperties properties = new ReaderProperties("../../../resource/aplication.properties");
        this.SECRET_KEY = properties.getSECRET_KEY();
    }

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

}
