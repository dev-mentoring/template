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
    @field:NotNull(message = "이메일을 입력해주세요")
    val email: String?,

    /** 비밀번호 */
    @field:Schema(description = "비밀번호", example = "Password1234~!")
    @field:NotNull(message = "비밀번호를 입력해주세요")
    val password: String?
) {
}
