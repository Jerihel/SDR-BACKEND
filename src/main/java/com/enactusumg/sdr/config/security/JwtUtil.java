package com.enactusumg.sdr.config.security;

import com.enactusumg.sdr.models.User;
import com.enactusumg.sdr.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JwtUtil {

    public static String generateToken(String text) {
        return generateAccessToken(text, (1000L * 60 * 10));
    }

    public static String generateAccessToken(String text, long expiration) {
        return "Bearer " + Jwts.builder()
                .setSubject(text)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, System.getenv("AWS_SIGNING_KEY"))
                .compact();
    }

    public static String generateRefreshToken(String text, long expiration) {
        return Jwts.builder()
                .setSubject(text)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, System.getenv("AWS_SIGNING_KEY"))
                .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response, UserService userService) throws IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                String username = parseToken(token);
                User user = userService.getUser(username);
                if (user != null && user.getToken() != null && user.getToken().equals(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            Collections.emptyList()
                    );
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    return authentication;
                }
            } catch (SignatureException | MalformedJwtException ex) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token.");
                return null;
            }
        }
        return null;
    }

    public static String parseToken(@Nullable String token) throws SignatureException {
        assert token != null;
        return Jwts.parser()
                .setSigningKey(System.getenv("AWS_SIGNING_KEY"))
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody()
                .getSubject();
    }
}
