package com.karakoc.security_demosu.security;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

public class JwtDecoder {
    
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token){
        return JWT
        //burayi unutursan , cok fena olur.
        .require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
        //buraya birden fazla sey istersen onlari da getirebilirsin, mesela rolu sadece "USER olanlar"
        // MESELA .withClaim("a", "USER")
        //MESELA .withClaim("uydurmabirfield","DEGERI")
        .build()
        .verify(token);
    }
}
