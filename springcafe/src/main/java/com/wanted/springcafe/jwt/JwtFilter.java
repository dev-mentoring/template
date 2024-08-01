package com.wanted.springcafe.jwt;

import com.wanted.springcafe.domain.user.UserEntity;
import com.wanted.springcafe.web.user.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization ==null || !authorization.startsWith("Bearer ")){
            System.out.println("Token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization.split(" ")[1];
        if(jwtUtil.isExpired(token)){
            System.out.println("Token expired");
            filterChain.doFilter(request, response);
        }

        String userName = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);

        UserEntity user = new UserEntity(userName, role);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);

    }
}
