package br.com.solivos.appOficinaVeiculos.config;


import br.com.solivos.appOficinaVeiculos.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret:minha-chave-secreta-muito-longa-e-segura-123456}")
    private String secret;

    private final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    public String gerarToken(Usuario usuario) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", usuario.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}