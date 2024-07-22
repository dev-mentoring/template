package org.project.portfolio.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/** 비밀번호 암호화 설정 */
@Configuration
class PasswordEncoderConfig {
    /** 스프링 시큐리티용 비밀번호 암호화 빈 */
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
