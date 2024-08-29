package org.project.portfolio.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.users.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j(topic = "JwtProvider")
@Getter
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.token.access-expiration-time}")
    private Long accessTokenExpiration;

    @Value("${jwt.token.refresh-expiration-time}")
    private Long refreshTokenExpiration;

    private static final String ACCESS_TOKEN_HEADER = "Authorization";
    private static final String REFRESH_TOKEN_HEADER = "AuthorizationRefresh";
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";
    private static final String BEARER = "Bearer ";

    private final ObjectMapper objectMapper;
    private final UsersRepository usersRepository;

    // AccessToken 발급
    public String createAccessToken(String username, String role) {
        log.info("createAccessToken() 실행");
        Date now = new Date();

        return JWT.create()
                .withIssuedAt(now)
                .withSubject(username)
                .withClaim(ROLE_CLAIM, role)
                .withClaim("token_type", ACCESS_TOKEN_HEADER)
                .withExpiresAt(new Date(now.getTime() + accessTokenExpiration))
                .sign(Algorithm.HMAC512(secretKey));
    }

    // RefreshToken 발급
    public String createRefreshToken(String username){
        log.info("createRefreshToken() 실행");
        Date now = new Date();
        return JWT.create()
                .withIssuedAt(now)
                .withSubject(username)
                .withClaim("token_type", REFRESH_TOKEN_HEADER)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpiration))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public void sendAccessToken(
            HttpServletResponse response,
            String accessToken
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Map<String, String> token = new HashMap<>();
        token.put(ACCESS_TOKEN_HEADER, accessToken);

        String result = objectMapper.writeValueAsString(token);
        response.getWriter().write(result);
    }

    public void sendAccessAndRefreshToken(
            HttpServletResponse response,
            String accessToken,
            String refreshToken
    ) throws IOException {
        log.info("sendAccessAndRefreshToken() 실행");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        Map<String, String> token = new HashMap<>();
        token.put(ACCESS_TOKEN_HEADER, accessToken);
        token.put(REFRESH_TOKEN_HEADER, refreshToken);

        String result = objectMapper.writeValueAsString(token);

        response.getWriter().write(result);
    }

    public Optional<String> extractAccessToken(HttpServletRequest request){
        log.info("extractAccessToken() 실행");
        log.info("request.toString: {}", request.toString());
        log.info("access token: {}", request.getHeader(ACCESS_TOKEN_HEADER));
        return Optional.ofNullable(request.getHeader(ACCESS_TOKEN_HEADER))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request){
        log.info("extractRefreshToken() 실행");
        return Optional.ofNullable(request.getHeader(REFRESH_TOKEN_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public String extractUsername(String token) {
        log.info("extractUsername() 실행");
        DecodedJWT decodedJWT = validateToken(token);
        return decodedJWT.getSubject();
    }

    public DecodedJWT validateToken(String token){
        log.info("validateToken() 실행");
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
    }
}