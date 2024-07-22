package org.project.portfolio.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.project.portfolio.auth.dto.LoginRequest
import org.project.portfolio.auth.dto.RegisterRequest
import org.project.portfolio.auth.dto.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** Auth API 컨트롤러 */
@Tag(name = "1. Auth", description = "The authentication API")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    /**
     * 로그인 API
     * @param loginRequest 로그인 요청 정보
     * @return JWT 토큰
     */
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "Authenticate the user and return the JWT token")
    fun login(
        @Valid @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.login(loginRequest))
    }

    /**
     * 회원가입 API
     * @param registerRequest 회원가입 요청 정보
     * @return JWT 토큰
     */
    @PostMapping("/register")
    @Operation(summary = "회원가입 API", description = "Create a new user account")
    fun register(
        @Valid @RequestBody registerRequest: RegisterRequest
    ): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.register(registerRequest))
    }
}
