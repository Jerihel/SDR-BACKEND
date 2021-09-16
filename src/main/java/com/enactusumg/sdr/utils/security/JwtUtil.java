package com.enactusumg.sdr.utils.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class JwtUtil {

    public static String generateToken(String username) {

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 2)))
                .signWith(SignatureAlgorithm.HS512, "3N4cTu5P45s")
                .compact();
        return "Bearer " + token;
    }

    public static Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user;
            try {
                user = Jwts.parser()
                        .setSigningKey("3N4cTu5P45s")
                        .parseClaimsJws(token.replace("Bearer", ""))
                        .getBody()
                        .getSubject();
            } catch (SignatureException ex) {
                System.err.println(ex);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token.");
                return null;
            }
            return user != null
                    ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
                    : null;
        }
        return null;
    }
}
