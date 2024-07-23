package org.project.portfolio.auth

import org.project.portfolio.auth.dto.LoginRequest
import org.project.portfolio.auth.dto.RegisterRequest
import org.project.portfolio.auth.dto.TokenResponse
import org.project.portfolio.common.BusinessException
import org.project.portfolio.common.ErrorCode
import org.project.portfolio.user.User
import org.project.portfolio.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/**
 * 사용자 인증 서비스
 * @param userRepository 사용자 레포지토리
 * @param jwtProvider JWT 토큰 생성 및 검증
 * @param passwordEncoder 비밀번호 암호화 및 검증
 */
@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    /**
     * 스프링 시큐리티에서 사용되는 사용자명으로 정보 조회하는 메소드
     * @param username 사용자명
     * @return 사용자 정보
     */
    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findById(username).orElseThrow {
            throw BusinessException(ErrorCode.USER_NOT_FOUND)
        }
    }

    /**
     * 회원가입
     * @param registerRequest 회원가입 요청 폼
     * @return JWT 토큰
     */
    fun register(registerRequest: RegisterRequest): TokenResponse {
        // 중복 확인
        if (userRepository.existsById(registerRequest.email!!)) {
            throw BusinessException(ErrorCode.USER_ALREADY_EXISTS)
        }

        val user = User(
            email = registerRequest.email,
            name = registerRequest.name!!,
            phone = registerRequest.phone!!,
            password = passwordEncoder.encode(registerRequest.password!!)
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
            throw BusinessException(ErrorCode.USER_NOT_FOUND)
        }
        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw BusinessException(ErrorCode.USER_INVALID_PASSWORD)
        }
        return TokenResponse(token = jwtProvider.createToken(user.email))
    }
}
