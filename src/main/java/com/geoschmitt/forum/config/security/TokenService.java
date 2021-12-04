package com.geoschmitt.forum.config.security;

import com.geoschmitt.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String tokenGenerate(Authentication authentication){
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("REST Forum")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.expiration)))
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public Boolean isValidToken(String token){
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public Long getIdUsuario(String token){
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
