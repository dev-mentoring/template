package org.project.portfolio.config

import org.project.portfolio.auth.AuthService
import org.project.portfolio.auth.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/** 스프링 시큐리티 설정 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val authService: AuthService,
    private val jwtAuthFilter: JwtAuthFilter
) {

    /** 허용 URL 목록 */
    private val WHITE_LIST_URL = arrayOf<String>(
        "/api/v1/auth/**",
        "/h2-console/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/",
    )


    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .headers { it.disable() }
            .sessionManagement { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*WHITE_LIST_URL).permitAll()
                    .anyRequest().authenticated()
            }
            .userDetailsService(authService)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
}
