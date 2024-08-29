package org.project.portfolio.global.security.jwt.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.portfolio.domain.users.domain.Users;
import org.project.portfolio.domain.users.repository.UsersRepository;
import org.project.portfolio.global.exception.ErrorCodeDetail;
import org.project.portfolio.global.exception.domainErrorCode.LoginErrorCode;
import org.project.portfolio.global.redis.RedisDao;
import org.project.portfolio.global.security.jwt.JwtProvider;
import org.project.portfolio.global.security.jwt.exception.JwtExpiredException;
import org.project.portfolio.global.security.jwt.exception.RefreshTokenNotFoundException;
import org.project.portfolio.global.security.jwt.exception.UserNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {
    private static final String[] NO_CHECK_URLS = {
            "/login",
            "/api/users/register"
    };

    private final JwtProvider jwtProvider;
    private final RedisDao redisDao;
    private final UsersRepository usersRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, JwtExpiredException {
        log.info("doFilterInternal() 실행");
        for (String url : NO_CHECK_URLS){
            if (request.getRequestURI().equals(url)){
                filterChain.doFilter(request, response);
                return;
            }
        }

        try {
            String refreshTokenFromHeader = jwtProvider.extractRefreshToken(request).orElse(null);
            if (refreshTokenFromHeader != null){
                String username = jwtProvider.extractUsername(refreshTokenFromHeader);
                if (!redisDao.getValues(username).equals(refreshTokenFromHeader))
                    throw new RefreshTokenNotFoundException();

                jwtProvider.validateToken(refreshTokenFromHeader);

                reissueAccessToken(response, username);

                return; // RT를 보낸 경우, AT를 재발급하고 인증처리는 하지 않게 하기 위해 return으로 필터 진행 막음
            }

            checkAccessTokenAndAuthentication(request, response, filterChain);
            log.info("checkAccessTokenAndAuthentication 실행 종료");
        } catch (TokenExpiredException e){
            log.error("유효기간이 만료된 토큰입니다. : {}", e.getMessage());
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(LoginErrorCode.JWT_EXPIRED.getStatus().value());
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(ErrorCodeDetail.from(LoginErrorCode.JWT_EXPIRED).getMessage());
        }
    }

    public void reissueAccessToken(HttpServletResponse response, String username) throws IOException {
        log.info("Access Token 재발급");
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException());

        String reissuedAccessToken = jwtProvider.createAccessToken(username, users.getRole().name());
        jwtProvider.sendAccessToken(response, reissuedAccessToken);
    }

    public void checkAccessTokenAndAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, JwtExpiredException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        String accessTokenFromHeader = jwtProvider.extractAccessToken(request).orElse(null);
        jwtProvider.validateToken(accessTokenFromHeader);
        String username = jwtProvider.extractUsername(accessTokenFromHeader);
        log.info("extractUsername 실행 종료");
        Users users = usersRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        saveAuthentication(users);
        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Users currentUser) {
        log.info("saveAuthentication() 실행");
        UserDetails userDetails = User.builder()
                .username(currentUser.getUsername())
                .password(currentUser.getPassword())
                .roles(currentUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("saveAuthentication() 실행 종료");
    }

    private String reIssueRefreshToken(String username){
        String reIssuedRefreshToken = jwtProvider.createRefreshToken(username);
        redisDao.setValues(username, reIssuedRefreshToken);

        return reIssuedRefreshToken;
    }
}
