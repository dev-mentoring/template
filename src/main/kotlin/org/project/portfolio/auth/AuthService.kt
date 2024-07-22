package org.project.portfolio.auth

import org.project.portfolio.auth.dto.LoginRequest
import org.project.portfolio.auth.dto.RegisterRequest
import org.project.portfolio.auth.dto.TokenResponse
import org.project.portfolio.user.User
import org.project.portfolio.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(username).orElseThrow {
            throw UsernameNotFoundException("User not found")
        }
    }

    /**
     * 회원가입
     * @param registerRequest 회원가입 요청 폼
     * @return JWT 토큰
     */
    fun register(registerRequest: RegisterRequest): TokenResponse {
        // 중복 확인
        if (userRepository.existsById(registerRequest.email)) {
            throw IllegalArgumentException("User already exists")
        }

        val user = User(
            email = registerRequest.email,
            name = registerRequest.name,
            phone = registerRequest.phone,
            password = passwordEncoder.encode(registerRequest.password)
        )
        userRepository.save(user)
        return TokenResponse(token = jwtProvider.createToken(user.email))
    }

    /**
     * 로그인
     * @param loginRequest 로그인 요청 폼
     * @return JWT 토큰
     */
    fun login(loginRequest: LoginRequest): TokenResponse {
        val user = userRepository.findById(loginRequest.email!!).orElseThrow {
            throw UsernameNotFoundException("User not found")
        }
        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }
        return TokenResponse(token = jwtProvider.createToken(user.email))
    }
}
