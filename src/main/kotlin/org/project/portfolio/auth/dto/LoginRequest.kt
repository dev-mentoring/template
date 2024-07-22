package org.project.portfolio.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

/** 로그인 폼 */
@Schema(name = "로그인 폼")
data class LoginRequest(
    /** 이메일 */
    @field:Schema(description = "이메일", example = "test@test.com")
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}\$", message = "이메일 형식이 올바르지 않습니다")
    val email: String?,

    /** 비밀번호 */
    @field:Schema(description = "비밀번호", example = "Password1234~!")
    @field:NotNull
    val password: String?
) {
}
