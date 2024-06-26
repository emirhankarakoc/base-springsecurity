package com.karakoc.security_demosu.security;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;




    //generatetoken
    public String issue(long userId,String email, List<String> roles){
        return JWT.create()
        //CONTENT
        .withSubject(String.valueOf(userId))
        .withExpiresAt(Instant.now().plus(Duration.of(1,ChronoUnit.DAYS)))
        .withClaim("e", email)
        //buradaki "a" yi degistirirsen , jwtToPrincipalConveter classindaki key'i de degistirmelisin.
        .withClaim("a", roles)


        //SECRETKEY
        .sign(Algorithm.HMAC256(properties.getSecretKey()));


    }
}
