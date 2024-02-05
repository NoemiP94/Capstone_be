package noemipusceddu.Capstone_be.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import noemipusceddu.Capstone_be.entities.User;
import noemipusceddu.Capstone_be.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    public String createToken(User user){
        String role = String.valueOf(user.getRole());
        return Jwts.builder().subject(String.valueOf(user.getId()))
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token){
        try{
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        }catch(Exception ex){
            throw new UnauthorizedException("There are problems with token!");
        }
    }

    public String extractIdFromToken(String token){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parseSignedClaims(token).getPayload().getSubject();
    }
}